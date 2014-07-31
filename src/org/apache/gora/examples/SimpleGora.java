/**
 * 
 */
package org.apache.gora.examples;

import org.apache.avro.util.Utf8;
import org.apache.gora.cassandra.store.CassandraStore;
import org.apache.gora.examples.generated.Employee;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.gora.util.GoraException;
import org.apache.hadoop.conf.Configuration;

/**
 * Simple GORA example
 */
public class SimpleGora<K, T extends PersistentBase> {

  /**
   * @param args
   * @throws GoraException 
   */
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws GoraException {
    DataStore<CharSequence, Employee> dataStore = 
        DataStoreFactory.createDataStore(CassandraStore.class, 
                                          String.class, Employee.class,
                                          new Configuration());
    Employee emp1 = new Employee();
    emp1.setSsn("43024255");
    emp1.setName("Renato");
    dataStore.put(emp1.getSsn(), emp1);
    dataStore.flush();
    Employee sameEmp = dataStore.get(emp1.getSsn());
    System.out.println(sameEmp.toString());
  }
}
