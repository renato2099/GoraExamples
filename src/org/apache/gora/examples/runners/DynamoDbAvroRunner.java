package org.apache.gora.examples.runners;

import org.apache.gora.dynamodb.query.DynamoDBKey;
import org.apache.gora.examples.GoraDataStoreRunnerI;
import org.apache.gora.examples.dynamodb.generated.person;

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

}
