package org.apache.gora.examples;

import org.apache.gora.persistency.Persistent;

public interface GoraDataStoreRunnerI<K, V extends Persistent> {

  /**
   * Gets a native object for the data store.
   * 
   * @return
   */
  public abstract V getObject();

  /**
   * Gets a native key for the data store.
   * 
   * @return
   */
  public abstract K getKey();

}