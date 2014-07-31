/**
 * 
 */
package org.apache.gora.utils;

import org.apache.gora.cassandra.store.CassandraStore;
import org.apache.gora.dynamodb.store.DynamoDBStore;
import org.apache.gora.hbase.store.HBaseStore;
//import org.apache.gora.hbase.store.HBaseStore;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.query.Query;
import org.apache.gora.query.Result;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.gora.util.GoraException;
import org.apache.hadoop.conf.Configuration;

/**
 * @author renatomarroquin
 * 
 */
public class GoraUtils {

  public static enum Type {
    CASSANDRA, HBASE, DYNAMODB
  }

  protected static Class<? extends DataStore> dataStoreClass;
  private static Configuration conf;

  /**
   * Creates a generic data store using the data store class set using the class
   * property
   * 
   * @param keyClass
   * @param persistentClass
   * @return
   * @throws GoraException
   */
  @SuppressWarnings("unchecked")
  public static <K, T extends Persistent> DataStore<K, T> createDataStore(
      Class<K> keyClass, Class<T> persistentClass) throws GoraException {
    // DataStoreFactory.createProps();
    DataStore<K, T> dataStore = DataStoreFactory.createDataStore(
        (Class<? extends DataStore<K, T>>) dataStoreClass, keyClass,
        persistentClass, conf);

    return dataStore;
  }

  /**
   * Returns the specific type of class for the requested data store
   * 
   * @param pDataStoreName
   * @return
   */
  private static Class<? extends DataStore> getSpecificDataStore(
      Type datastoreType) {
    switch (datastoreType) {
    case CASSANDRA:
      return CassandraStore.class;
    case DYNAMODB:
      return DynamoDBStore.class;
    case HBASE:
      return HBaseStore.class;
    default:
      throw new IllegalStateException("DataStore not supported yet.");
    }
  }

  public static <K, T extends Persistent> DataStore<K, T> createSpecificDataStore(
      String pDataStoreName, Type dsType, Class<K> pKeyClass,
      Class<T> pValueClass) throws GoraException {
    // Getting the specific data store
    dataStoreClass = getSpecificDataStore(dsType);
    return createDataStore(pKeyClass, pValueClass);
  }

  public static <K, T extends Persistent> Result<K, T> getRequests(
      DataStore<K, T> pDataStore, K pStartKey, K pEndKey) {
    Query<K, T> query = pDataStore.newQuery();
    query.setStartKey(pStartKey);
    query.setEndKey(pEndKey);
    return pDataStore.execute(query);
  }
}
