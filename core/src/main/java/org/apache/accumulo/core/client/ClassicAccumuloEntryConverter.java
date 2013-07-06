package org.apache.accumulo.core.client;

import static org.apache.accumulo.core.client.Utils.bytesForSequence;

import java.util.Map.Entry;

import org.apache.accumulo.core.data.ArrayByteSequence;
import org.apache.accumulo.core.data.ByteSequence;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.KeyValue;
import org.apache.accumulo.core.data.Value;
import org.apache.hadoop.io.Text;

public class ClassicAccumuloEntryConverter implements
    EntryConverter<Entry<Key, Value>, Text, Text, Text, Text, Long, Value> {

  @Override
  public ByteSequence getFamilyBytes(Text family) {
    return new ArrayByteSequence(family.getBytes());
  }

  @Override
  public ByteSequence getQualifierBytes(Text qualifier) {
    return new ArrayByteSequence(qualifier.getBytes());
  }

  @Override
  public Entry<Key, Value> getEntry(ByteSequence rowData,
      ByteSequence columnFamilyData, ByteSequence columnQualifierData,
      ByteSequence columnVisibilityData, long timestamp, ByteSequence value) {
    return new KeyValue(new Key(bytesForSequence(rowData),
        bytesForSequence(columnFamilyData), bytesForSequence(columnQualifierData),
        bytesForSequence(columnVisibilityData), timestamp),
        bytesForSequence(value));
  }

  @Override
  public ByteSequence getRowBytes(Text row) {
    return new ArrayByteSequence(row.getBytes());
  }

  @Override
  public ByteSequence getVisibilityBytes(Text visibility) {
    return new ArrayByteSequence(visibility.getBytes());
  }

  @Override
  public long getTimestampLong(Long timestamp) {
    return timestamp;
  }

  @Override
  public ByteSequence getValueBytes(Value value) {
    return new ArrayByteSequence(value.get());
  }

  @Override
  public Text getRow(Entry<Key, Value> entry) {
    return entry.getKey().getRow();
  }

  @Override
  public Text getFamily(Entry<Key, Value> entry) {
    return entry.getKey().getColumnFamily();
  }

  @Override
  public Text getQualifier(Entry<Key, Value> entry) {
    return entry.getKey().getColumnQualifier();
  }

  @Override
  public Text getVisibility(Entry<Key, Value> entry) {
    return entry.getKey().getColumnVisibility();
  }

  @Override
  public Long getTimestamp(Entry<Key, Value> entry) {
    return entry.getKey().getTimestamp();
  }

  @Override
  public Value getValue(Entry<Key, Value> entry) {
    return entry.getValue();
  }

}
