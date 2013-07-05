/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.accumulo.core.client;

import java.util.Map.Entry;

import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Value;
import org.apache.hadoop.io.Text;

/**
 * Scans a table over a given range.
 * 
 * "Clients can iterate over multiple column families, and there are several mechanisms for limiting the rows, columns, and timestamps traversed by a scan. For
 * example, we could restrict [a] scan ... to only produce anchors whose columns match [a] regular expression ..., or to only produce anchors whose timestamps
 * fall within ten days of the current time."
 */
public interface Scanner extends ScannerBase, GenericScanner<Entry<Key,Value>, Text, Text> {
  
  /**
   * This setting determines how long a scanner will automatically retry when a failure occurs. By default a scanner will retry forever.
   * 
   * @param timeOut
   *          in seconds
   * @deprecated Since 1.5. See {@link ScannerBase#setTimeout(long, java.util.concurrent.TimeUnit)}
   */
  @Deprecated
  public void setTimeOut(int timeOut);
  
  /**
   * Returns the setting for how long a scanner will automatically retry when a failure occurs.
   * 
   * @return the timeout configured for this scanner
   * @deprecated Since 1.5. See {@link ScannerBase#getTimeout(java.util.concurrent.TimeUnit)}
   */
  @Deprecated
  public int getTimeOut();
  
  
}
