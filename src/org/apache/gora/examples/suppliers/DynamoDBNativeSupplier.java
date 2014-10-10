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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.gora.dynamodb.query.DynamoDBKey;
import org.apache.gora.examples.GoraDataStoreRunnerI;
import org.apache.gora.examples.dynamodb.generated.person;
import org.apache.gora.query.Result;

/**
 * Class to supply data to be persisted inside DynamoDB using native serialization.
 */
public class DynamoDBNativeSupplier implements
    GoraDataStoreRunnerI<DynamoDBKey<Long, String>, person> {

  private static final String maxRangeKey = "11/06/86";
  private static final String minRangeKey = "03/03/83";
  private static final Long maxHashKey = 10L;
  private static final Long minHashKey = 1L;

  @Override
  public DynamoDBKey<Long, String> getKey() {
    return buildKey(maxHashKey, maxRangeKey);
  }

  @Override
  public person getObject() {
    return buildPerson(maxHashKey, maxRangeKey, "Pedro", "",
        Arrays.asList("Lima", "Caracas", "Bogota", "Rio de Janeiro"));
  }

  @Override
  public Map<DynamoDBKey<Long, String>, person> getElements() {
    Map<DynamoDBKey<Long, String>, person> elems = new HashMap<>();
    elems.put(
        buildKey(maxHashKey, maxRangeKey),
        buildPerson(maxHashKey, maxRangeKey, "Pedro", "",
            Arrays.asList("Lima", "Caracas", "Bogota", "Rio de Janeiro")));
    elems.put(
        buildKey(minHashKey, minRangeKey),
        buildPerson(minHashKey, minRangeKey, "Paco", "Perez",
            Arrays.asList("Zurich", "Madric", "London", "Paris")));
    return elems;
  }

  private DynamoDBKey<Long, String> buildKey(Long hashKey, String rangeKey) {
    DynamoDBKey<Long, String> dKey = new DynamoDBKey<>();
    dKey.setHashKey(hashKey);
    dKey.setRangeKey(rangeKey);
    return dKey;
  }

  private person buildPerson(Long ssn, String bd, String fn, String ln,
      List<String> vp) {
    person p = new person();
    p.setHashKey(ssn);
    if (bd != null & !bd.isEmpty())
      p.setRangeKey(bd);
    if (fn != null & !fn.isEmpty())
      p.setFirstName(fn);
    if (ln != null & !ln.isEmpty())
      p.setLastName(ln);
    if (vp != null & !vp.isEmpty())
      p.setVisitedplaces(new HashSet<String>(vp));
    return p;
  }

  @Override
  public DynamoDBKey<Long, String> getMaxKey() {
    return buildKey(maxHashKey, maxRangeKey);
  }

  @Override
  public DynamoDBKey<Long, String> getMinKey() {
    return buildKey(minHashKey, minRangeKey);
  }

  @Override
  public void handleResult(Result<DynamoDBKey<Long, String>, person> res) {
    boolean any = false;
    try {
      while(res.next()) {
        System.out.println(res.get().getFirstName());
        any = true;
      }
      if (!any)
        System.out.println("There were no elements fetched.");
    } catch (Exception e) {
      System.out.println("Error while handling result from DynamoDB.");
      e.printStackTrace();
    }
  }

  @Override
  public void handleResult(person res) {
    StringBuilder sb = new StringBuilder();
    sb.append(res.getFirstName()).append(' ').append(res.getLastName());
    System.out.println(sb.toString());
    
  }
}
