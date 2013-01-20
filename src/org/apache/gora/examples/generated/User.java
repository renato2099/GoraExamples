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
public class User extends PersistentBase {
  public static final Schema _SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"User\",\"namespace\":\"org.apache.gora.examples.generated\",\"fields\":[{\"name\":\"username\",\"type\":\"string\"},{\"name\":\"firstname\",\"type\":\"string\"},{\"name\":\"lastname\",\"type\":\"string\"},{\"name\":\"password\",\"type\":\"string\"}]}");
  public static enum Field {
    USERNAME(0,"username"),
    FIRSTNAME(1,"firstname"),
    LASTNAME(2,"lastname"),
    PASSWORD(3,"password"),
    ;
    private int index;
    private String name;
    Field(int index, String name) {this.index=index;this.name=name;}
    public int getIndex() {return index;}
    public String getName() {return name;}
    public String toString() {return name;}
  };
  public static final String[] _ALL_FIELDS = {"username","firstname","lastname","password",};
  static {
    PersistentBase.registerFields(User.class, _ALL_FIELDS);
  }
  private Utf8 username;
  private Utf8 firstname;
  private Utf8 lastname;
  private Utf8 password;
  public User() {
    this(new StateManagerImpl());
  }
  public User(StateManager stateManager) {
    super(stateManager);
  }
  public User newInstance(StateManager stateManager) {
    return new User(stateManager);
  }
  public Schema getSchema() { return _SCHEMA; }
  public Object get(int _field) {
    switch (_field) {
    case 0: return username;
    case 1: return firstname;
    case 2: return lastname;
    case 3: return password;
    default: throw new AvroRuntimeException("Bad index");
    }
  }
  @SuppressWarnings(value="unchecked")
  public void put(int _field, Object _value) {
    if(isFieldEqual(_field, _value)) return;
    getStateManager().setDirty(this, _field);
    switch (_field) {
    case 0:username = (Utf8)_value; break;
    case 1:firstname = (Utf8)_value; break;
    case 2:lastname = (Utf8)_value; break;
    case 3:password = (Utf8)_value; break;
    default: throw new AvroRuntimeException("Bad index");
    }
  }
  public Utf8 getUsername() {
    return (Utf8) get(0);
  }
  public void setUsername(Utf8 value) {
    put(0, value);
  }
  public Utf8 getFirstname() {
    return (Utf8) get(1);
  }
  public void setFirstname(Utf8 value) {
    put(1, value);
  }
  public Utf8 getLastname() {
    return (Utf8) get(2);
  }
  public void setLastname(Utf8 value) {
    put(2, value);
  }
  public Utf8 getPassword() {
    return (Utf8) get(3);
  }
  public void setPassword(Utf8 value) {
    put(3, value);
  }
}
