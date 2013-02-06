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
import java.util.HashSet;

import org.apache.avro.util.Utf8;
import org.apache.gora.cassandra.store.CassandraStore;
import org.apache.gora.dynamodb.store.DynamoDBStore;
import org.apache.gora.examples.generated.Alien;
import org.apache.gora.examples.generated.Simpson;
import org.apache.gora.examples.generated.User;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.gora.util.GoraException;
import org.apache.hadoop.conf.Configuration;

/**
 * @author renatomarroquin
 *
 */
public class GoraRunner {

  /**
   * Data store to handle user storage
   */
  protected static DataStore<String,User> userStore;
  
  protected static DataStore<String,Alien> alienStore;

  protected static DataStore<String,Simpson> simpsonStore;

  private static Configuration conf;

  protected static HashSet<DataStore> dataStores = new HashSet();

  protected static Class<? extends DataStore> dataStoreClass;

  /**
   * @param args
   */
  public static void main(String[] args) {

    String dataStoreName = "Cassandra";
    dataStoreClass = getSpecificDataStore(dataStoreName);

    try {

      // Creating data stores
      //userStore = createDataStore(String.class, User.class);
      //alienStore = createDataStore(String.class, Alien.class);
      simpsonStore = createDataStore(String.class, Simpson.class);

      // Performing requests
      putRequests();
      getRequests();

      // Closing Alien data store
      //alienStore.close();
      simpsonStore.close();

      // Closing User data store
      //userStore.flush();
      //userStore.close();

    } catch (GoraException e) {
      e.printStackTrace();
    }

  }

  private static void getRequests(){
    System.out.println("Performing get requests");
    //userGetOperations();
    //alienGetOperations();
    simpsonGetOperations();
  }
  
  private static void putRequests(){
    System.out.println("Performing put requests");
    //userPutRequests();
    //alienPutRequests();
    simpsonPutRequests();
  }
  
  private static void userPutRequests(){
    // Adding a new user
    User usr = new User();
    usr.setFirstname(new Utf8("Renato"));
    userStore.put("renatoj.marroquin", usr);
  }

  private static void simpsonPutRequests(){
    // Adding a new simpson
    Simpson bSimpson = new Simpson();
    bSimpson.setFirstname(new Utf8("bart"));
    bSimpson.setLastname(new Utf8("simpson"));
    bSimpson.setPassword(new Utf8("bart123"));
    //alen.setTelephone(new Utf8("247548"));
    Integer phone = new Integer("247548");
    bSimpson.setTelephone(phone);
    simpsonStore.put("bart.simpson", bSimpson);
    simpsonStore.flush();
  }

  private static void alienPutRequests(){
    // Adding a new alien
    Alien alen = new Alien();
    alen.setFirstname(new Utf8("chewbacca"));
    alen.setLastname(new Utf8("sobaka"));
    alen.setPassword(new Utf8("chewie"));
    //alen.setTelephone(new Utf8("247548"));
    alen.setTelephone( ByteBuffer.wrap("247548".getBytes()) );
    alienStore.put("chewbacca.sobaka", alen);

    // Flushing down operations
    alienStore.flush();
  }

  private static void simpsonGetOperations(){
    // Retrieving first alien
    Simpson bSimpson = simpsonStore.get("bart.simpson");
    if (bSimpson.getTelephone() != null)
      System.out.println(bSimpson.getTelephone());
    else
      System.out.println("No telephone registered.");
    
    if (bSimpson != null)
      System.out.println(bSimpson.getFirstname());
    else
      System.out.println("Simpson hasn't been found.");
  }

  private static void alienGetOperations(){
    // Retrieving first alien
    Alien alen = alienStore.get("chewbacca.sobaka");
    if (alen.getTelephone() instanceof Utf8)
      System.out.println(alen.getTelephone());
    if (alen.getTelephone() instanceof ByteBuffer){
      byte[] bytearr = new byte[((ByteBuffer)alen.getTelephone()).remaining()];
      ((ByteBuffer)alen.getTelephone()).get(bytearr);
      System.out.println(new String(bytearr));
    }
    if (alen != null)
      System.out.println(alen.getFirstname());
    else
      System.out.println("Alien hasn't been found.");
  }
  
  private static void userGetOperations(){
    // Retrieving first user
    User usr = userStore.get("pmcfadin");
    if(usr != null)
      System.out.println(usr.getFirstname());
    else
      System.out.println("User hasn't been object.");
    
    // Retrieving second user
    usr = userStore.get("renatoj.marroquin");
    System.out.println(usr.getFirstname());
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
    if (dataStore != null)
      dataStores.add(dataStore);

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
