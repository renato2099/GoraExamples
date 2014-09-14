package org.apache.gora.examples.runners;

import java.util.Map;

import org.apache.gora.dynamodb.query.DynamoDBKey;
import org.apache.gora.examples.GoraDataStoreRunnerI;
import org.apache.gora.examples.dynamodb.generated.person;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.query.Result;

public class DynamoDbAvroRunner implements GoraDataStoreRunnerI{

  @Override
  public person getObject() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DynamoDBKey<String, String> getKey() {
    // TODO Auto-generated method stub
    return null;
  }

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

}
