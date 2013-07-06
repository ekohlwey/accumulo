package org.apache.accumulo.core.client;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.accumulo.core.data.Range;
/**
 * Implementations of BatchScanner support efficient lookups of many ranges in accumulo.
 * 
 * Use this when looking up lots of ranges and you expect each range to contain a small amount of data. Also only use this when you do not care about the
 * returned data being in sorted order.
 * 
 * If you want to lookup a few ranges and expect those ranges to contain a lot of data, then use the Scanner instead. Also, the Scanner will return data in
 * sorted order, this will not.
 */
public interface GenericBatchScanner<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE>
    extends GenericScannerBase<TYPE, ROW, FAMILY, QUALIFIER, VISIBILITY, TIMESTAMP, VALUE> {

  /**
   * Allows scanning over multiple ranges efficiently.
   * 
   * @param ranges
   *          specifies the non-overlapping ranges to query
   */
  void setRanges(Collection<Range> ranges);
  
  /**
   * Cleans up and finalizes the scanner
   */
  void close();
  
  /**
   * Sets a timeout threshold for a server to respond. The batch scanner will accomplish as much work as possible before throwing an exception. BatchScanner
   * iterators will throw a {@link TimedOutException} when all needed servers timeout. Setting the timeout to zero or Long.MAX_VALUE and TimeUnit.MILLISECONDS
   * means no timeout.
   * 
   * <p>
   * If not set, there is not timeout. The BatchScanner will retry forever.
   * 
   * @param timeout
   * @param timeUnit
   *          determines how timeout is interpreted
   * @since 1.5.0
   */
  @Override
  void setTimeout(long timeout, TimeUnit timeUnit);
}
