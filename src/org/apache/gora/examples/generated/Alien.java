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
  public Alien(StateManager stateManager) {
    super(stateManager);
  }
  public Alien newInstance(StateManager stateManager) {
    return new Alien(stateManager);
  }
  public Schema getSchema() { return _SCHEMA; }
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
  public Utf8 getFirstname() {
    return (Utf8) get(0);
  }
  public void setFirstname(Utf8 value) {
    put(0, value);
  }
  public Utf8 getLastname() {
    return (Utf8) get(1);
  }
  public void setLastname(Utf8 value) {
    put(1, value);
  }
  public Utf8 getPassword() {
    return (Utf8) get(2);
  }
  public void setPassword(Utf8 value) {
    put(2, value);
  }
  public Object getTelephone() {
    return (Object) get(3);
  }
  public void setTelephone(Utf8 value) {
    put(3, value);
  }
  public void setTelephone(ByteBuffer value) {
    put(3, value);
  }
  public void setTelephoneUnionIndex(Integer value){
    put(4,value);
  }
  public Integer getTelephoneUnionIndex(){
    return (Integer) get(4);
  }
}
