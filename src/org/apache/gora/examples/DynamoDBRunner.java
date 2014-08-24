package org.apache.gora.examples;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.gora.dynamodb.query.DynamoDBKey;
import org.apache.gora.examples.dynamodb.generated.person;

public class DynamoDBRunner {

  public static DynamoDBKey<String, String> getNativeKey() {
    DynamoDBKey<String, String> dKey = new DynamoDBKey<String, String>();
    dKey.setHashKey("Brazil");
    dKey.setRangeKey("10/10/1985");
    return dKey;
  }

  public static person getNativeObject() {
    person p = new person();
    p.setHashKey("43024255");
    p.setRangeKey("11/06/85");
    p.setFirstName("qwerty");
    p.setVisitedplaces(new HashSet<String>(Arrays.asList("Lima", "Caracas", "Bogota", "Rio de Janeiro")));
    return p;
  }
}
