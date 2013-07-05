package org.apache.accumulo.core.client;

import org.apache.accumulo.core.data.ByteSequence;

public interface EntryConverter<T, F, Q> {
  
  public ByteSequence getFamilyBytes(F family);
  
  public ByteSequence getQualifierBytes(Q qualifier);

  public T getEntry(ByteSequence rowData, ByteSequence columnFamilyData,
      ByteSequence columnQualifierData, ByteSequence columnVisibilityData,
      long timestamp, ByteSequence byteSequence);
  

}
