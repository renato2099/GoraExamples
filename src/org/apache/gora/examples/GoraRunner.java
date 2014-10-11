/**
 *Licensed to the Apache Software Foundation (ASF) under one
 *or more contributor license agreements.  See the NOTICE file
 *distributed with this work for additional information
 *regarding copyright ownership.  The ASF licenses this file
 *to you under the Apache License, Version 2.0 (the"
 *License"); you may not use this file except in compliance
 *with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package org.apache.gora.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.avro.util.Utf8;
//import org.apache.gora.dynamodb.query.DynamoDBKey;
import org.apache.gora.examples.dynamodb.generated.person;
import org.apache.gora.examples.generated.WebPage;
import org.apache.gora.examples.suppliers.DynamoDBNativeSupplier;
import org.apache.gora.examples.suppliers.WebPageSupplier;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.query.Result;
import org.apache.gora.store.DataStore;
import org.apache.gora.util.GoraException;
import org.apache.gora.utils.GoraUtils;
import org.apache.gora.utils.GoraUtils.Type;

/**
 * GoraRunner enables us to create a {@link DataStore} via
 * {@link DataStoreFactory#createDataStore(Class, Class, Class, Configuration)},
 * then executes puts (writes first and last name, passwsord and telephone
 * number) and gets (retrieves) the data we previously persisted. We then close
 * the data store using {@link DataStore#close()}.
 * 
 * @author renatomarroquin
 * 
 */
public class GoraRunner<K, T extends Persistent> {

  /** Data store to handle user storage */
  protected HashMap<String, DataStore<K, T>> dataStores;

  /**
   * @param args
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void main(String[] args) {

    String dsName = "webPages";
    Type dsType = Type.MONGO;
    GoraRunner gr;
    GoraDataStoreRunnerI runnerUtils;

    switch (dsType) {
      // Creating data stores.
      case DYNAMODB:
        //gr = new GoraRunner<DynamoDBKey, person>();
        //gr.addDataStore(dsName, Type.DYNAMODB, DynamoDBKey.class, person.class);
        //runnerUtils = new DynamoDBNativeSupplier();
        //break;
      // They all support the same type of serialization.
      case ACCUMULO:
      case CASSANDRA:
      case HBASE:
      case MONGO:
        gr = new GoraRunner<String, WebPage>();
        gr.addDataStore(dsName, dsType, String.class, WebPage.class);
        runnerUtils = new WebPageSupplier();
        break;
      default:
        System.out.println("Data store not supported yet.");
        return;
    }
    // Put requests
    //gr.putRequest(dsName, runnerUtils.getElements());
    // Get request
    runnerUtils.handleResult(gr.getRequest(dsName, runnerUtils.getKey()));
    // Query request
    Result res = gr.queryRequest(dsName, runnerUtils.getMinKey(),
        runnerUtils.getMaxKey());
    runnerUtils.handleResult(res);
  }

  /**
   * Read elements from a Gora data store using a range query.
   * 
   * @param pDataStoreName
   * @param pStartKey
   * @param pEndKey
   * @return
   */
  public Result<K, T> queryRequest(String pDataStoreName, K pStartKey, K pEndKey) {
    System.out.println("Performing get requests for <" + pDataStoreName
        + "> using key <" + pStartKey + ", " + pEndKey + ">");
    return GoraUtils.queryRequests(dataStores.get(pDataStoreName), pStartKey,
        pEndKey);
  }

  /**
   * Gets a specific element from a data store.
   * 
   * @param pDataStoreName
   * @param pKey
   * @return
   */
  public T getRequest(String pDataStoreName, K pKey) {
    System.out.println("Performing get requests for <" + pDataStoreName
        + "> using key <" + pKey + ">");
    DataStore<K, T> dataStore = dataStores.get(pDataStoreName);
    return dataStore.get(pKey);
  }

  /**
   * Puts more than one element into a specific data store.
   * 
   * @param pDataStoreName
   * @param values
   */
  public void putRequest(String pDataStoreName, Map<K, T> values) {
    for (Entry<K, T> en : values.entrySet()) {
      putRequest(pDataStoreName, en.getKey(), en.getValue());
    }
  }

  /**
   * Puts a single element into a specific data store.
   * 
   * @param pDataStoreName
   * @param pKey
   * @param pValue
   */
  public void putRequest(String pDataStoreName, K pKey, T pValue) {
    System.out.println("Performing put requests for <" + pDataStoreName + ">");
    DataStore<K, T> dataStore = dataStores.get(pDataStoreName);
    dataStore.put(pKey, pValue);
    dataStore.flush();
  }

  /**
   * Adds a data store to the ones being used.
   * 
   * @param dsName
   * @param dsType
   * @param pKeyClass
   * @param pValueClass
   */
  private void addDataStore(String dsName, Type dsType, Class<K> pKeyClass,
      Class<T> pValueClass) {
    // Setting the recently created data store into a centralized structure
    DataStore<K, T> dataStore;
    this.dataStores = new HashMap<String, DataStore<K, T>>();
    try {
      dataStore = GoraUtils.createSpecificDataStore(dsName, dsType, pKeyClass,
          pValueClass);
      if (dataStore != null)
        dataStores.put(dsName, dataStore);
    } catch (GoraException e) {
      System.out.println("Error adding new data store");
      e.printStackTrace();
    }
  }
}
