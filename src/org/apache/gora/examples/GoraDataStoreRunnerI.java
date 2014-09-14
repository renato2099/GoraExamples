package org.apache.gora.examples;

import java.util.Map;

import org.apache.gora.persistency.Persistent;
import org.apache.gora.query.Result;

/**
 * @author renatomarroquin
 *
 * @param <K>
 * @param <V>
 */
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

  /**
   * Gets a group of elements associated with their keys.
   * 
   * @return
   */
  public abstract Map<K, V> getElements();

  /**
   * Gets the max key used.
   * 
   * @return
   */
  public abstract K getMaxKey();

  /**
   * Gets the ming key used.
   * 
   * @return
   */
  public abstract K getMinKey();

  /**
   * Handles a result set from a query.
   * @param res
   */
  public abstract void handleResult(Result<K, V> res);

  /**
   * Handles a single result from a request.
   * @param res
   */
  public abstract void handleResult(V res);
}