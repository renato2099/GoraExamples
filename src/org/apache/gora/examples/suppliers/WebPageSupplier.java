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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.util.Utf8;
import org.apache.gora.examples.GoraDataStoreRunnerI;
import org.apache.gora.examples.generated.Metadata;
import org.apache.gora.examples.generated.WebPage;
import org.apache.gora.query.Result;

/**
 * Class for supplying Avro WebPage to be persisted in data stores that use Avro
 * serialization.
 */
public class WebPageSupplier implements GoraDataStoreRunnerI<String, WebPage> {

  public static final String[] URLS = { "http://foo.com/",
      "http://foo.com/1.html", "http://foo.com/2.html", "http://bar.com/3.jsp",
      "http://bar.com/1.html", "http://bar.com/",
      "http://baz.com/1.jsp&q=barbaz", "http://baz.com/1.jsp&q=barbaz&p=foo",
      "http://baz.com/1.jsp&q=foo", "http://bazbar.com" };

  public static HashMap<String, Integer> URL_INDEXES = new HashMap<String, Integer>();

  static {
    for (int i = 0; i < URLS.length; i++) {
      URL_INDEXES.put(URLS[i], i);
    }
  }

  public static final String[] CONTENTS = { null, "foo", "foo1 bar1 baz1",
      "a b c d e", "aa bb cc dd ee", "1", "2 3", "a b b b b b a", "a a a",
      "foo bar baz" };

  public static final int[][] LINKS = { { 1, 2, 3, 9 }, { 3, 9 }, {}, { 9 },
      { 5 }, { 1, 2, 3, 4, 6, 7, 8, 9 }, { 1 }, { 2 }, { 3 }, { 8, 1 } };

  public static final String[][] ANCHORS = { { "foo", "foo", "foo", "foo" },
      { "a1", "a2" }, {}, { "anchor1" }, { "bar" },
      { "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9" }, { "foo" },
      { "baz" }, { "bazbar" }, { "baz", "bar" } };

  public static final String[] SORTED_URLS = new String[URLS.length];
  static {
    for (int i = 0; i < URLS.length; i++) {
      SORTED_URLS[i] = URLS[i];
    }
    Arrays.sort(SORTED_URLS);
  }

  /** First URL */
  private static final int ROOT_ELEM = 0;

  /** Last URL */
  private static final int BOT_ELEM = URLS.length - 1;

  @Override
  public WebPage getObject() {
    return buildWebPage(ROOT_ELEM);
  }

  @Override
  public String getKey() {
    return URLS[ROOT_ELEM];
  }

  @Override
  public Map<String, WebPage> getElements() {
    Map<String, WebPage> webs = new HashMap<String, WebPage>();
    WebPage page;
    for (int i = 0; i < URLS.length; i++) {
      page = buildWebPage(i);
      webs.put(URLS[i], page);
    }
    return webs;
  }

  /**
   * Builds a webPage from data arrays.
   * 
   * @param pos
   *          from where data will be taken.
   * @return WebPage constructe.
   */
  private WebPage buildWebPage(int pos) {
    WebPage page = WebPage.newBuilder().build();
    page.setUrl(new Utf8(URLS[pos]));
    page.setParsedContent(new ArrayList<CharSequence>());
    if (CONTENTS[pos] != null) {
      page.setContent(ByteBuffer.wrap(CONTENTS[pos].getBytes()));
      for (String token : CONTENTS[pos].split(" ")) {
        page.getParsedContent().add(new Utf8(token));
      }
    }
    for (int j = 0; j < LINKS[pos].length; j++) {
      page.getOutlinks().put(new Utf8(URLS[LINKS[pos][j]]),
          new Utf8(ANCHORS[pos][j]));
    }
    Metadata metadata = Metadata.newBuilder().build();
    metadata.setVersion(1);
    metadata.getData().put(new Utf8("metakey"), new Utf8("metavalue"));
    page.setMetadata(metadata);
    return page;
  }

  @Override
  public String getMaxKey() {
    return URLS[BOT_ELEM];
  }

  @Override
  public String getMinKey() {
    return URLS[ROOT_ELEM];
  }

  @Override
  public void handleResult(Result<String, WebPage> res) {
    boolean any = false;
    try {
      while (res.next()) {
        System.out.println("Key: " + res.getKey() + " * Value:" + res);
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
  public void handleResult(WebPage res) {
    if (res != null) {
      System.out.println(res.toString());
    }
  }

}
