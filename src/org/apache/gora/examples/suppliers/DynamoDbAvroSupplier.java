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
package org.apache.gora.examples.suppliers;

import java.util.Map;


//import org.apache.gora.dynamodb.query.DynamoDBKey;
import org.apache.gora.examples.GoraDataStoreRunnerI;
import org.apache.gora.examples.dynamodb.generated.person;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.query.Result;

/**
 * Class for supplying data to be persisted using Avro serialization in DynamoDB.
 */
public class DynamoDbAvroSupplier implements GoraDataStoreRunnerI{

  @Override
  public person getObject() {
    // TODO Auto-generated method stub
    return null;
  }

//  @Override
//  public DynamoDBKey<String, String> getKey() {
//    // TODO Auto-generated method stub
//    return null;
//  }

  @Override
  public Map getElements() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object getMaxKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object getMinKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void handleResult(Result res) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void handleResult(Persistent res) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Object getKey() {
    // TODO Auto-generated method stub
    return null;
  }

}
