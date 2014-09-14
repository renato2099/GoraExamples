package org.apache.gora.examples.runners;

import java.io.IOException;
import java.util.Map;

import org.apache.gora.examples.GoraDataStoreRunnerI;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.query.Result;

public class GiraphRunner<K, V extends Persistent> implements
    GoraDataStoreRunnerI<K, V> {

  /**
   * [0,0,[[1,1],[3,3]]] [1,0,[[0,1],[2,2],[3,1]]] [2,0,[[1,2],[4,4]]]
   * [3,0,[[0,3],[1,1],[4,4]]] [4,0,[[3,4],[2,4]]]
   */

  @Override
  public V getObject() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public K getKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<K, V> getElements() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public K getMaxKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public K getMinKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void handleResult(Result<K, V> res) {
    if (res != null) {
      try {
        while (res.next()) {
          // Vertex vrtx = (Vertex)pResults.get(); System.out.println(vrtx);
          System.out.println("Nothing to see here yet");
        }
      } catch (IOException e) {
        System.out.println("Error verifying data input.");
        e.printStackTrace();
      } catch (Exception e) {
        System.out.println("Error verifying data input.");
        e.printStackTrace();
      }
    } else {
      System.out.println("Objets hasn't been found.");
    }
  }

  @Override
  public void handleResult(V res) {
    // TODO Auto-generated method stub
    
  }

}
