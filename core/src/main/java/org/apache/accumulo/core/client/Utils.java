package org.apache.accumulo.core.client;

import org.apache.accumulo.core.data.ByteSequence;

public final class Utils {

  private Utils() {
  }

  public static byte[] bytesForSequence(ByteSequence bs) {
    byte[] backingArray = bs.getBackingArray();
    if (backingArray.length == bs.length()) {
      return backingArray;
    }
    byte[] bytes = new byte[bs.length()];
    System.arraycopy(bs.getBackingArray(), bs.offset(), bytes, 0, bs.length());
    return bytes;
  }

}
