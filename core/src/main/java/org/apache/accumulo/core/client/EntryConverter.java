package org.apache.accumulo.core.client;

import org.apache.accumulo.core.data.ByteSequence;

public interface EntryConverter<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> {

  public ByteSequence getFamilyBytes(FAMILY family);

  public ByteSequence getQualifierBytes(QUALIFIER qualifier);
  
  public ByteSequence getRowBytes(ROW row);
  
  public ByteSequence getVisibilityBytes(VISIBILITY visibility);
  
  public long getTimestampLong(TIMESTAMP timestamp);
  
  public ByteSequence getValueBytes(VALUE value);

  public TYPE getEntry(ByteSequence rowData, ByteSequence columnFamilyData,
      ByteSequence columnQualifierData, ByteSequence columnVisibilityData,
      long timestamp, ByteSequence byteSequence);
  
  public ROW getRow(TYPE entry);
  
  public FAMILY getFamily(TYPE entry);
  
  public QUALIFIER getQualifier(TYPE entry);
  
  public VISIBILITY getVisibility(TYPE entry);
  
  public TIMESTAMP getTimestamp(TYPE entry);
  
  public VALUE getValue(TYPE entry);

}
