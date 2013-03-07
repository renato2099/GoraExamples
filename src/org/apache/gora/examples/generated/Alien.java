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

package org.apache.gora.examples.generated;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;
import org.apache.avro.Protocol;
import org.apache.avro.Schema;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.Protocol;
import org.apache.avro.util.Utf8;
import org.apache.avro.ipc.AvroRemoteException;
import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.FixedSize;
import org.apache.avro.specific.SpecificExceptionBase;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificFixed;
import org.apache.gora.persistency.StateManager;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.persistency.impl.StateManagerImpl;
import org.apache.gora.persistency.StatefulHashMap;
import org.apache.gora.persistency.ListGenericArray;

/**
 * Alien describes an entity who has a first and last name, a password, 
 * telephone number and an index associated with the telephone number.
 * To display how schema mapping is handled in Gora, the telephone number is 
 * stored as a Union.
 * @author rmarroquin
 */
@SuppressWarnings("all")
public class Alien extends PersistentBase {
  public static final Schema _SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"Alien\",\"namespace\":\"org.apache.gora.examples.generated\",\"fields\":[{\"name\":\"firstname\",\"type\":\"string\"},{\"name\":\"lastname\",\"type\":\"string\"},{\"name\":\"password\",\"type\":\"string\"},{\"name\":\"telephone\",\"type\":[\"string\",\"bytes\"]},{\"name\":\"telephoneUnionIndex\",\"type\":\"int\"}]}");
  public static enum Field {
    FIRSTNAME(0,"firstname"),
    LASTNAME(1,"lastname"),
    PASSWORD(2,"password"),
    TELEPHONE(3,"telephone"),
    TELEPHONEUNIONINDEX(4,"telephoneUnionIndex"),
    ;
    private int index;
    private String name;
    Field(int index, String name) {this.index=index;this.name=name;}
    public int getIndex() {return index;}
    public String getName() {return name;}
    public String toString() {return name;}
  };
  
  /**
   * Simply states the static fields provided by the Alien Avro Schema.
   */
  public static final String[] _ALL_FIELDS = {"firstname","lastname","password","telephone","telephoneUnionIndex",};
  static {
    PersistentBase.registerFields(Alien.class, _ALL_FIELDS);
  }
  private Utf8 firstname;
  private Utf8 lastname;
  private Utf8 password;
  private Object telephone;
  private Integer telephoneUnionIndex;
  public Alien() {
    this(new StateManagerImpl());
  }
  
  /**
   * Default constructor.
   * @param stateManager the {@link StateManager} used to manage state of fields.
   */
  public Alien(StateManager stateManager) {
    super(stateManager);
  }
  
  /**
   * Create a new instance of the {@link Alien} class.
   * @param stateManager will be used to track state within the instance.
   */
  public Alien newInstance(StateManager stateManager) {
    return new Alien(stateManager);
  }
  
  /**
   * Get the Schema associated with the {@link Alien}.
   * @return the schema
   */
  public Schema getSchema() { return _SCHEMA; }
  
  /**
   * Get an {@link Object} field.
   * @param _field the field we wish to obtain a value for
   * @return the _field {@link Object}.
   * @throws {@link AvroRuntimeException}
   */
  public Object get(int _field) {
    switch (_field) {
    case 0: return firstname;
    case 1: return lastname;
    case 2: return password;
    case 3: return telephone;
    case 4: return telephoneUnionIndex;
    default: throw new AvroRuntimeException("Bad index");
    }
  }
  
  /**
   * Put an {@link Object} into some field within {@link Alien}.
   * @param _field the field we wish to persist as value to.
   * @param _value the value we wish to associate with the _field.
   * @throws {@link AvroRuntimeException}.
   *
   */
  @SuppressWarnings(value="unchecked")
  public void put(int _field, Object _value) {
    if(isFieldEqual(_field, _value)) return;
    getStateManager().setDirty(this, _field);
    switch (_field) {
    case 0:firstname = (Utf8)_value; break;
    case 1:lastname = (Utf8)_value; break;
    case 2:password = (Utf8)_value; break;
    case 3:telephone = (Object)_value; break;
    case 4:telephoneUnionIndex = (Integer)_value; break;
    default: throw new AvroRuntimeException("Bad index");
    }
  }
  
  /**
   * Get the first name associated with an Alien
   * @return first name
   */
  public Utf8 getFirstname() {
    return (Utf8) get(0);
  }
  
  /**
   * Set the first name for an Alien
   * @param value the {@link Utf8} value to set.
   */
  public void setFirstname(Utf8 value) {
    put(0, value);
  }
  
  /**
   * Get the last name associated with an Alien
   * @return last name
   */
  public Utf8 getLastname() {
    return (Utf8) get(1);
  }
  
  /**
   * Set the last name for an Alien
   * @param value the {@link Utf8} value to set.
   */
  public void setLastname(Utf8 value) {
    put(1, value);
  }
  
  /**
   * Get the password associated with an Alien
   * @return password
   */
  public Utf8 getPassword() {
    return (Utf8) get(2);
  }
  
  /**
   * Set the password for an Alien
   * @param value the {@link Utf8} value to set.
   */
  public void setPassword(Utf8 value) {
    put(2, value);
  }
  
  /**
   * Get the telephone number associated with an Alien
   * @return telephone number
   */
  public Object getTelephone() {
    return (Object) get(3);
  }
  
  /**
   * Set the telephone number for an Alien
   * @param value the {@link Utf8} value to set.
   */
  public void setTelephone(Utf8 value) {
    put(3, value);
  }
  
  /**
   * Set the telephone number for an Alien
   * @param value the {@link ByteBuffer} value to set.
   */
  public void setTelephone(ByteBuffer value) {
    put(3, value);
  }
  
  /**
   * Get the Union index associated with the telephone number for an Alien
   * @return index number
   */
  public Integer getTelephoneUnionIndex() {
    return (Integer) get(4);
  }
  
  /**
   * Set the index telephone number for an Alien
   * @param value the {@link Integer} value to set.
   */
  public void setTelephoneUnionIndex(Integer value) {
    put(4, value);
  }
}
