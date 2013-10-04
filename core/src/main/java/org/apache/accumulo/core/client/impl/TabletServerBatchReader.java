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
package org.apache.accumulo.core.client.impl;

import java.util.Map.Entry;

import org.apache.accumulo.core.client.BatchScanner;
import org.apache.accumulo.core.client.ClassicAccumuloEntryConverter;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizations;
<<<<<<< HEAD
import org.apache.accumulo.core.security.Credentials;
import org.apache.accumulo.core.util.ArgumentChecker;
import org.apache.accumulo.core.util.SimpleThreadPool;
=======
import org.apache.accumulo.core.security.thrift.TCredentials;
>>>>>>> More work on generic supertypes.
import org.apache.hadoop.io.Text;

<<<<<<< HEAD
public class TabletServerBatchReader extends GenericTabletServerBatchScannerImpl<Entry<Key,Value>, Text, Text, Text, Text, Long, Value> {
  public static final Logger log = Logger.getLogger(TabletServerBatchReader.class);
  
  private String table;
  private int numThreads;
  private ExecutorService queryThreadPool;
  
  private Instance instance;
  private ArrayList<Range> ranges;
  
  private Credentials credentials;
  private Authorizations authorizations = Authorizations.EMPTY;
  private Throwable ex = null;
  
  private static int nextBatchReaderInstance = 1;
  
  private static synchronized int getNextBatchReaderInstance() {
    return nextBatchReaderInstance++;
  }
  
  private final int batchReaderInstance = getNextBatchReaderInstance();
=======
public class TabletServerBatchReader extends GenericTabletServerBatchScannerImpl<Entry<Key,Value>, Text, Text, Text, Text, Long, Value> implements BatchScanner {
>>>>>>> More work on generic supertypes.
  
  public TabletServerBatchReader(Instance instance, Credentials credentials, String table, Authorizations authorizations, int numQueryThreads) {
    super(instance, credentials, table, authorizations, numQueryThreads, new ClassicAccumuloEntryConverter());
  }
  
}
