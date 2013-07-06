package org.apache.accumulo.core.client;

import org.apache.accumulo.core.data.Range;

public interface GenericScanner<T, R, F, Q, VI, TS, V> extends GenericScannerBase<T, R, F, Q, VI, TS, V> {

  /**
   * Sets the range of keys to scan over.
   * 
   * @param range
   *          key range to begin and end scan
   */
  public void setRange(Range range);
  
  /**
   * Returns the range of keys to scan over.
   * 
   * @return the range configured for this scanner
   */
  public Range getRange();
  
  /**
   * Sets the number of Key/Value pairs that will be fetched at a time from a tablet server.
   * 
   * @param size
   *          the number of Key/Value pairs to fetch per call to Accumulo
   */
  public void setBatchSize(int size);
  
  /**
   * Returns the batch size (number of Key/Value pairs) that will be fetched at a time from a tablet server.
   * 
   * @return the batch size configured for this scanner
   */
  public int getBatchSize();
  
  /**
   * Enables row isolation. Writes that occur to a row after a scan of that row has begun will not be seen if this option is enabled.
   */
  public void enableIsolation();
  
  /**
   * Disables row isolation. Writes that occur to a row after a scan of that row has begun may be seen if this option is enabled.
   */
  void disableIsolation();
  
}
