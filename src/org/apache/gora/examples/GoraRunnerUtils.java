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

import org.apache.gora.dynamodb.query.DynamoDBKey;
import org.apache.gora.examples.dynamodb.generated.person;
import org.apache.gora.examples.runners.DynamoDBNativeRunner;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.query.Result;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.gora.util.GoraException;
import org.apache.gora.utils.GoraUtils;
import org.apache.gora.utils.GoraUtils.Type;
import org.apache.hadoop.conf.Configuration;
//import org.apache.gora.examples.generated.old.Simpson;
//import org.apache.gora.examples.generated.old.Vertex;
//import org.apache.gora.dynamodb.query.DynamoDBKey;
//import org.apache.gora.dynamodb.store.DynamoDBStore;

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
public class GoraRunnerUtils<K, T extends Persistent> {

  /** Data store to handle user storage */
  protected HashMap<String, DataStore<K, T>> dataStores;

  /**
   * @param args
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void main(String[] args) {

    String dsName = "dynamoDBpeople";
    Type dsType = Type.DYNAMODB;
    GoraRunnerUtils gr;
    GoraDataStoreRunnerI runnerUtils;

    switch(dsType) {
      case DYNAMODB:
        gr = new GoraRunnerUtils<DynamoDBKey, person>();
        // Creating data stores
        gr.addDataStore(dsName, Type.DYNAMODB, DynamoDBKey.class, person.class);
        runnerUtils = new DynamoDBNativeRunner();
        break;
      case ACCUMULO:
      case CASSANDRA: 
      case HBASE:
      default: System.out.println("Data stores not supported yet."); 
        return;
      
    }
    //gr.putRequest(dsName, runnerUtils.getKey(), runnerUtils.getObject());
    Persistent obj = gr.getRequest(dsName, runnerUtils.getKey());
    System.out.println(obj.toString());

    /**
     * [0,0,[[1,1],[3,3]]] [1,0,[[0,1],[2,2],[3,1]]] [2,0,[[1,2],[4,4]]]
     * [3,0,[[0,3],[1,1],[4,4]]] [4,0,[[3,4],[2,4]]]
     */
    // Performing requests vertices's requests
    // gr.putRequests(dsName, GeneratedUtils.getVertices());
    // gr.putRequest(dsName, gr.createKey("bart.simpsone"),
    // GeneratedUtils.createSimpson("bart.simpsone"));
    // gr.deleteRequests("simpsonStore", gr.createKey("bart.simpsone"));
    // gr.verify(gr.goraRead(dsName, null, null));
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
    System.out.println("Performing get requests for <" + pDataStoreName + "> using key <" + pStartKey.toString() + ">");
    return GoraUtils.queryRequests(dataStores.get(pDataStoreName), pStartKey,
        pEndKey);
  }

  /**
   * Put elements into a Gora data store.
   * 
   * @param pDataStoreName
   * @param pGraph
   * 
   *          public void putRequests(String pDataStoreName, Map<K, T> pGraph){
   *          System.out.println("Performing put requests for " +
   *          pDataStoreName); DataStore<K, T> dataStore =
   *          dataStores.get(pDataStoreName); for(K vrtxId : pGraph.keySet())
   *          dataStore.put(vrtxId, pGraph.get(vrtxId)); dataStore.flush(); }
   */

  public T getRequest(String pDataStoreName, K pKey) {
    System.out.println("Performing get requests for <" + pDataStoreName + "> using key <" + pKey + ">");
    DataStore<K, T> dataStore = dataStores.get(pDataStoreName);
    return dataStore.get(pKey);
  }

  public void putRequest(String pDataStoreName, K pKey, T pValue) {
    System.out.println("Performing put requests for <" + pDataStoreName + ">");
    DataStore<K, T> dataStore = dataStores.get(pDataStoreName);
    dataStore.put(pKey, pValue);
    dataStore.flush();
  }

  /**
   * Verifies if a result obtained has our vertices.
   * 
   * @param pResults
   * 
   *          public void verify(Result<K, T> pResults){ if (pResults != null){
   *          try { while (pResults.next()){ Vertex vrtx =
   *          (Vertex)pResults.get(); System.out.println(vrtx); } } catch
   *          (IOException e) {
   *          System.out.println("Error verifying data input.");
   *          e.printStackTrace(); } catch (Exception e) {
   *          System.out.println("Error verifying data input.");
   *          e.printStackTrace(); } } else
   *          System.out.println("Objetos hasn't been found."); }
   */

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
