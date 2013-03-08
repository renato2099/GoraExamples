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

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.apache.avro.util.Utf8;
import org.apache.gora.cassandra.store.CassandraStore;
import org.apache.gora.dynamodb.store.DynamoDBStore;
import org.apache.gora.examples.generated.Alien;
import org.apache.gora.examples.generated.Simpson;
import org.apache.gora.examples.generated.User;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.gora.util.GoraException;
import org.apache.hadoop.conf.Configuration;

/**
 * GoraRunner enables us to create a {@link DataStore} via 
 * {@link DataStoreFactory#createDataStore(Class, Class, Class, Configuration)}, then 
 * executes puts (writes first and last name, passwsord and telephone number) and gets 
 * (retrieves) the data we previously persisted.
 * We then close the data store using {@link DataStore#close()}.
 * @author renatomarroquin
 *
 */
public class GoraRunner<K, T extends PersistentBase> {

  /**
   * Data store to handle user storage
   */
  protected static HashMap<String, DataStore> dataStores = new HashMap<String, DataStore>();

  private static Configuration conf;

  protected static Class<? extends DataStore> dataStoreClass;

  protected Class<K> keyClass;
  protected Class<T> persistentClass;
  
  /**
   * @param args
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void main(String[] args) {

    String dataStoreType = "Cassandra";
    GoraRunner gr = new GoraRunner();
    
    try {

      // Creating data stores
      //createSpecificDataStore("userStore", dataStoreType, String.class, User.class);
      //createSpecificDataStore("alienStore", dataStoreType, String.class, Alien.class);
      createSpecificDataStore("simpsonStore", dataStoreType, String.class, Simpson.class);

      
      // Performing requests Simpson's requests
      gr.putRequests("simpsonStore", gr.createKey("bart.simpson"), gr.createSimpson());
      verifySimpson((Simpson)gr.getRequests("simpsonStore", gr.createKey("bart.simpson")));

      // Performing requests Alien's requests
      //gr.putRequests("simpsonStore", gr.createKey("chewbacca.sobaka"), gr.createAlien());
      
      // Performing requests User's requests
      //usr = userStore.get("renatoj.marroquin");
      //usr = userStore.get("pmcfadin");
      //gr.putRequests("userStore", gr.createKey("renatoj.marroquin"), gr.createUser());

    } catch (GoraException e) {
      e.printStackTrace();
    }

  }
  
  private K createKey(String pKey){
    return (K) pKey;
  }
  
  private static <K, T extends Persistent> DataStore<K,T>
   createSpecificDataStore(String pDataStoreName, String pDataStoreType, Class<K> keyClass, Class<T> persistentClass) throws GoraException {
    // Getting the specific data store
    dataStoreClass = getSpecificDataStore(pDataStoreType);
    DataStore<K,T> dataStore = createDataStore(keyClass, persistentClass);
    // Setting the recently created data store into a centralized structure
    if (dataStore != null)
      dataStores.put(pDataStoreName, dataStore);
    // Returning the data store created
    return dataStore;
  }

  private Object getRequests(String pDataStoreName, K pKey){
    System.out.println("Performing get requests for " + pDataStoreName);
    DataStore<K, T> dataStore = dataStores.get(pDataStoreName);
    Object obj = dataStore.get(pKey);
    return obj;
  }

  private void putRequests(String pDataStoreName, K pKey, T pValue){
    System.out.println("Performing put requests for " + pDataStoreName);
    DataStore<K, T> dataStore = dataStores.get(pDataStoreName);
    dataStore.put(pKey, pValue);
    dataStore.flush();
  }

  private User createUser(){
    // Adding a new user
    User usr = new User();
    usr.setFirstname(new Utf8("Renato"));
    return usr;
  }

  private Simpson createSimpson(){
    // Adding a new simpson
    Simpson bSimpson = new Simpson();
    bSimpson.setFirstname(new Utf8("bart"));
    bSimpson.setLastname(new Utf8("simpson"));
    bSimpson.setPassword(new Utf8("bart123"));
    bSimpson.setTelephone(new Utf8("247548"));
    //bSimpson.setTelephone(null);
    
    return bSimpson;
  }

  private Alien createAlien(){
    // Adding a new alien
    Alien alen = new Alien();
    alen.setFirstname(new Utf8("chewbacca"));
    alen.setLastname(new Utf8("sobaka"));
    alen.setPassword(new Utf8("chewie"));
    //alen.setTelephone(new Utf8("247548"));
    alen.setTelephone( ByteBuffer.wrap("247548".getBytes()) );

    return alen;
  }

  private static void verifySimpson(Simpson pSimpson){
    if (pSimpson != null){
      System.out.println(pSimpson.toString());
      if (pSimpson.getTelephone() != null)
        System.out.println("There was a telephone registered.");
      else
        System.out.println("No telephone registered.");
    }
    else
      System.out.println("Simpson hasn't been found.");
  }

  private static void verifyAlien(Alien alen){
    // Retrieving first alien
    if (alen != null){
      System.out.println(alen.getFirstname());
      if (alen.getTelephone() instanceof Utf8)
        System.out.println(alen.getTelephone());
      if (alen.getTelephone() instanceof ByteBuffer){
        byte[] bytearr = new byte[((ByteBuffer)alen.getTelephone()).remaining()];
        ((ByteBuffer)alen.getTelephone()).get(bytearr);
        System.out.println(new String(bytearr));
      }
    }
    else
      System.out.println("Alien hasn't been found.");
  }
  
  private static void verifyUser(User pUsr){
    // Retrieving first user
    if(pUsr != null)
      System.out.println(pUsr.getFirstname());
    else
      System.out.println("User hasn't been object.");
  }

  /**
   * Creates a generic data store using the data store class set using the class property
   * @param keyClass
   * @param persistentClass
   * @return
   * @throws GoraException
   */
  @SuppressWarnings("unchecked")
  public static <K, T extends Persistent> DataStore<K,T>
    createDataStore(Class<K> keyClass, Class<T> persistentClass) throws GoraException {
    DataStoreFactory.createProps();
    DataStore<K,T> dataStore = 
        DataStoreFactory.createDataStore((Class<? extends DataStore<K,T>>)dataStoreClass, 
                                          keyClass, persistentClass,
                                          conf);

    return dataStore;
  }

  /**
   * Returns the specific type of class for the requested data store
   * @param pDataStoreName
   * @return
   */
  private static Class<? extends DataStore> getSpecificDataStore(String pDataStoreName){
    if (pDataStoreName == "Cassandra")
        return CassandraStore.class;
    if (pDataStoreName == "DynamoDB")
        return DynamoDBStore.class;
    return null;
  }
}
