package org.apache.accumulo.core.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.accumulo.core.client.EntryConverter;
import org.apache.accumulo.core.data.thrift.TMutation;
import org.apache.accumulo.core.util.ByteBufferUtil;
import org.apache.accumulo.core.util.UnsynchronizedBuffer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class GenericMutation<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE>
    implements Writable {
  static final int VALUE_SIZE_COPY_CUTOFF = 1 << 15;

  public static enum SERIALIZED_FORMAT {
    VERSION1, VERSION2
  };

  private boolean useOldDeserialize = false;
  private byte[] row;
  private byte[] data;
  private int entries;
  private List<byte[]> values;

  private UnsynchronizedBuffer.Writer buffer;

  private List<ColumnUpdate> updates;
  private final EntryConverter<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> converter;

  private static final byte[] EMPTY_BYTES = new byte[0];

  private void serialize() {
    if (buffer != null) {
      data = buffer.toArray();
      buffer = null;
    }
  }

  protected GenericMutation(
      byte[] row,
      int start,
      int length,
      EntryConverter<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> converter) {
    this.row = new byte[length];
    System.arraycopy(row, start, this.row, 0, length);
    buffer = new UnsynchronizedBuffer.Writer();
    this.converter = converter;
  }

  protected GenericMutation(
      EntryConverter<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> converter) {
    this.converter = converter;
  }

  public GenericMutation(
      TMutation tmutation,
      EntryConverter<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> converter) {
    this.row = ByteBufferUtil.toBytes(tmutation.row);
    this.data = ByteBufferUtil.toBytes(tmutation.data);
    this.entries = tmutation.entries;
    this.values = ByteBufferUtil.toBytesList(tmutation.values);
    this.converter = converter;
  }

  public GenericMutation(
      GenericMutation<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> m) {
    m.serialize();
    this.converter = m.converter;
    this.row = m.row;
    this.data = m.data;
    this.entries = m.entries;
    this.values = m.values;
  }

  public GenericMutation(
      ROW row,
      EntryConverter<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> converter) {
    this.converter = converter;
  }

  public void put(FAMILY family, QUALIFIER qualifier, VISIBILITY visibility,
      TIMESTAMP timestamp, VALUE value) {
    put(converter.getFamilyBytes(family),
        converter.getQualifierBytes(qualifier),
        converter.getVisibilityBytes(visibility), true,
        converter.getTimestampLong(timestamp), converter.getValueBytes(value));
  }

  private void put(ByteSequence bs) {
    buffer.add(bs.getBackingArray(), bs.offset(), bs.length());
  }

  private void put(boolean val) {
    buffer.add(val);
  }

  private void put(long val) {
    buffer.writeVLong(val);
  }

  protected void put(ByteSequence cf, ByteSequence cq, ByteSequence cv,
      boolean hasts, long ts, boolean deleted, ByteSequence val) {
    if (buffer == null) {
      throw new IllegalStateException(
          "Can not add to mutation after serializing it");
    }
    put(cf);
    put(cq);
    put(cv);
    put(hasts);
    if (hasts) {
      put(ts);
    }
    put(deleted);

    int valLength = val.length();
    if (valLength < VALUE_SIZE_COPY_CUTOFF) {
      put(val);
    } else {
      if (values == null) {
        values = new ArrayList<byte[]>();
      }
      byte copy[] = new byte[valLength];
      System.arraycopy(val, 0, copy, 0, valLength);
      values.add(copy);
      put(-1 * values.size());
    }

    entries++;
  }

}
