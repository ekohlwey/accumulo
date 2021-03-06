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
package org.apache.accumulo.test.functional;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

public class ZooCacheIT extends SimpleMacIT {
  
  @Test(timeout = 2 * 60 *1000)
  public void test() throws Exception {
    assertEquals(0, exec(CacheTestClean.class, "/zcTest-42", "/tmp/zcTest-42").waitFor());
    final AtomicReference<Exception> ref = new AtomicReference<Exception>();
    List<Thread> threads = new ArrayList<Thread>();
    for (int i = 0; i < 3; i++) {
      Thread reader = new Thread() {
        public void run() {
          try {
            CacheTestReader.main(new String[]{"/zcTest-42", "/tmp/zcTest-42", getConnector().getInstance().getZooKeepers()});
          } catch(Exception ex) {
            ref.set(ex);
          }
        }
      };
      reader.start();
      threads.add(reader);
    }
    assertEquals(0, exec(CacheTestWriter.class, "/zcTest-42", "/tmp/zcTest-42", "3","50").waitFor());
    for (Thread t: threads) {
      t.join();
      if (ref.get() != null)
        throw ref.get();
    }
  }
  
}
