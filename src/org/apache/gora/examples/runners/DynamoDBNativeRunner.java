package org.apache.gora.examples.runners;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.gora.dynamodb.query.DynamoDBKey;
import org.apache.gora.examples.GoraDataStoreRunnerI;
import org.apache.gora.examples.dynamodb.generated.person;

public class DynamoDBNativeRunner implements GoraDataStoreRunnerI<DynamoDBKey<String, String>, person> {

  @Override
  public DynamoDBKey<String, String> getKey() {
    DynamoDBKey<String, String> dKey = new DynamoDBKey<String, String>();
    dKey.setHashKey("43024255");
    dKey.setRangeKey("11/06/85");
    return dKey;
  }

  @Override
  public person getObject() {
    person p = new person();
    p.setHashKey("43024255");
    p.setRangeKey("11/06/85");
    p.setFirstName("qwerty");
    p.setVisitedplaces(new HashSet<String>(Arrays.asList("Lima", "Caracas", "Bogota", "Rio de Janeiro")));
    return p;
  }
}
