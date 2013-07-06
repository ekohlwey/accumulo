package org.apache.accumulo.core.client.impl;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.EntryConverter;
import org.apache.accumulo.core.client.GenericScanner;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.security.Authorizations;
import org.apache.accumulo.core.security.thrift.TCredentials;
import org.apache.accumulo.core.util.ArgumentChecker;
import org.apache.hadoop.io.Text;

public class GenericScannerImpl<T, R, F, Q, VI, TS, V> extends ScannerOptions<T, R, F, Q, VI, TS, V> implements GenericScanner<T, R, F, Q, VI, TS, V> {

  // keep a list of columns over which to scan
  // keep track of the last thing read
  // hopefully, we can track all the state in the scanner on the client
  // and just query for the next highest row from the tablet server
  
  private Instance instance;
  private TCredentials credentials;
  private Authorizations authorizations;
  private Text table;
  
  private int size;
  
  private Range range;
  private boolean isolated = false;
  private EntryConverter<T, R, F, Q, VI, TS, V> converter;
  
  public GenericScannerImpl(Instance instance, TCredentials credentials,
      String table, Authorizations authorizations,
      EntryConverter<T, R, F, Q, VI, TS, V> entryConverter) {
    super(entryConverter);
    ArgumentChecker.notNull(instance, credentials, table, authorizations);
    this.instance = instance;
    this.credentials = credentials;
    this.table = new Text(table);
    this.range = new Range((Key) null, (Key) null);
    this.authorizations = authorizations;
    this.converter = entryConverter;
    this.size = Constants.SCAN_BATCH_SIZE;
  }
  
  @Override
  public synchronized void setRange(Range range) {
    ArgumentChecker.notNull(range);
    this.range = range;
  }
  
  @Override
  public synchronized Range getRange() {
    return range;
  }
  
  @Override
  public synchronized void setBatchSize(int size) {
    if (size > 0)
      this.size = size;
    else
      throw new IllegalArgumentException("size must be greater than zero");
  }
  
  @Override
  public synchronized int getBatchSize() {
    return size;
  }
  
  /**
   * Returns an iterator over an accumulo table. This iterator uses the options that are currently set on the scanner for its lifetime. So setting options on a
   * Scanner object will have no effect on existing iterators.
   */
  @Override
  public synchronized Iterator<T> iterator() {
    return new ScannerIterator<T>(instance, credentials, table, authorizations, range, size, getTimeout(TimeUnit.MILLISECONDS), this, isolated, converter);
  }
  
  @Override
  public synchronized void enableIsolation() {
    this.isolated = true;
  }
  
  @Override
  public synchronized void disableIsolation() {
    this.isolated = false;
  }
  
  protected EntryConverter<T, R, F, Q, VI, TS, V> getConverter() {
    return converter;
  }

}
