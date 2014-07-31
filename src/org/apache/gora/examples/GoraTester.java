package org.apache.gora.examples;

import java.nio.ByteBuffer;

import static org.apache.gora.utils.GoraUtils.Type.CASSANDRA;

import org.apache.avro.util.Utf8;
import org.apache.gora.examples.generated.WebPage;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.store.DataStore;
import org.apache.gora.util.ByteUtils;
import org.apache.gora.util.GoraException;
import org.apache.gora.utils.GoraUtils;

/**
 * @author renatomarroquin
 * 
 */
public class GoraTester<K, T extends PersistentBase> {

  DataStore<String, WebPage> dataStore;

  String[] urls = { "http://a.com/a" };// , "http://b.com/b", "http://c.com/c",
  // "http://d.com/d", "http://e.com/e", "http://f.com/f", "http://g.com/g"};
  String[] outLinks = { "http://a.com/a", "http://b.com/b", "http://c.com/c",
      "http://d.com/d", "http://e.com/e", "http://f.com/f", "http://g.com/g" };// };
  String content = "content";
  String parsedContent = "parsedContent";
  String anchor = "anchor";
  int parsedContentCount = 0;

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    GoraTester<String, WebPage> gt = new GoraTester<String, WebPage>();
    try {
      gt.dataStore = GoraUtils.createSpecificDataStore("webpage", CASSANDRA,
          String.class, WebPage.class);
      gt.updateWebpage();
    } catch (GoraException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void printWebPages() {
    System.out.println("==================");
    System.out.println("Printing web pages");
    System.out.println("==================");
    for (int i = 0; i < urls.length; i++) {
      WebPage webPage = dataStore.get(urls[i]);
      webPage.setUrl(new Utf8(urls[i]));
      System.out.println(webPage.toString());
    }
    System.out.println("==================");
  }

  public void initialize() {
    parsedContentCount = 0;
    for (int i = 0; i < urls.length; i++) {
      WebPage webPage = dataStore.newPersistent();
      webPage.setUrl(new Utf8(urls[i]));
      for (parsedContentCount = 0; parsedContentCount < 5; parsedContentCount++) {
        webPage.getParsedContent().add(
            new Utf8(parsedContent + i + "," + parsedContentCount));
      }
      for (int j = 0; j < outLinks.length; j += 2) {
        webPage.getOutlinks().put(new Utf8(anchor + j), new Utf8(outLinks[j]));
      }
      System.out.println("Putting " + webPage.getUrl().toString() + " = "
          + webPage.toString());
      dataStore.put(webPage.getUrl().toString(), webPage);
    }
    dataStore.flush();
  }

  public void verifyWebPage() {
    for (int i = 0; i < urls.length; i++) {
      WebPage webPage = dataStore.get(urls[i]);
      // Assert.assertEquals(content + i, ByteUtils.toString(
      // toByteArray(webPage.getContent()) ));
      if ((content + i).equals(ByteUtils.toString(toByteArray(webPage
          .getContent()))))
        System.out.println("They were equal " + (content + i) + " - "
            + ByteUtils.toString(toByteArray(webPage.getContent())));
      else
        System.out.println("They were not equal " + (content + i) + " - "
            + ByteUtils.toString(toByteArray(webPage.getContent())));

      // Assert.assertEquals(10, webPage.getParsedContent().size());
      if (10 == webPage.getParsedContent().size())
        System.out.println("WebPage parsed content was right");
      else
        System.out.println("WebPage parsed content was not right"
            + String.valueOf(webPage.getParsedContent().size()));

      int j = 0;
      for (CharSequence pc : webPage.getParsedContent()) {
        // Assert.assertEquals(parsedContent + i + "," + j, pc.toString());
        if ((parsedContent + i + "," + j).toString().equals(pc.toString()))
          System.out.println("Parsed content was the same "
              + (parsedContent + i + "," + j).toString() + " - "
              + pc.toString());

        j++;
      }
      int count = 0;
      for (j = 1; j < urls.length; j += 2) {
        CharSequence link = webPage.getOutlinks().get(new Utf8(anchor + j));
        // Assert.assertNotNull(link);
        if (link == null) {
          System.out.println("Link was null." + anchor + j);

        } else {
          // Assert.assertEquals(urls[j], link.toString());
          if (link.toString().equals(urls[j]))
            System.out.println("links were equal " + link.toString() + " - "
                + urls[j]);
          else
            System.out.println("links were not equal " + link.toString()
                + " - " + urls[j]);
        }
        count++;
      }
      // Assert.assertEquals(count, webPage.getOutlinks().size());
      if (count == webPage.getOutlinks().size())
        System.out.println("WebPage outlinks size was right "
            + String.valueOf(webPage.getOutlinks().size()));
      else
        System.out.println("111 WebPage outlinks size was not right: Expected "
            + String.valueOf(count) + " - Obtained:"
            + String.valueOf(webPage.getOutlinks().size()));
    }
  }

  public void modifyParseContentOutlinksWebPage() {
    System.out.println("==================");
    System.out.println("Modifying urls");
    System.out.println("==================");
    for (int i = 0; i < urls.length; i++) {
      WebPage webPage = dataStore.get(urls[i]);
      webPage.setContent(ByteBuffer.wrap(ByteUtils.toBytes(content + i)));
      for (parsedContentCount = 5; parsedContentCount < 10; parsedContentCount++) {
        webPage.getParsedContent().add(
            new Utf8(parsedContent + i + "," + parsedContentCount));
      }
      webPage.getOutlinks().clear();
      for (int j = 1; j < outLinks.length; j += 2) {
        System.out.println("putting " + anchor + j);
        webPage.getOutlinks().put(new Utf8(anchor + j), new Utf8(outLinks[j]));
      }
      System.out.println("Modifying " + webPage.getUrl().toString() + " = "
          + webPage.toString());
      dataStore.put(webPage.getUrl().toString(), webPage);
    }
    dataStore.flush();
    System.out.println("==================");
  }

  public void modifyOutLinks() {
    for (int i = 0; i < urls.length; i++) {
      WebPage webPage = dataStore.get(urls[i]);
      for (int j = 0; j < urls.length; j += 2) {
        webPage.getOutlinks().put(new Utf8(anchor + j), new Utf8(urls[j]));
      }
      dataStore.put(webPage.getUrl().toString(), webPage);
    }

    dataStore.flush();
  }

  public void verifyOutLinks() {
    for (int i = 0; i < urls.length; i++) {
      WebPage webPage = dataStore.get(urls[i]);
      int count = 0;
      for (int j = 0; j < urls.length; j++) {
        CharSequence link = webPage.getOutlinks().get(new Utf8(anchor + j));
        // Assert.assertNotNull(link);
        if (link == null) {
          System.out.println("Link was null." + anchor + j);
          continue;
        }
        // Assert.assertEquals(urls[j], link.toString());
        if (link.toString().equals(urls[j]))
          System.out.println("links were equal " + link.toString() + " - "
              + urls[j]);
        else
          System.out.println("links were not equal " + link.toString() + " - "
              + urls[j]);
        count++;
      }
    }
  }

  public void updateWebpage() {
    initialize();
    printWebPages();
    modifyParseContentOutlinksWebPage();
    printWebPages();
    verifyWebPage();
    modifyOutLinks();
    verifyOutLinks();
  }

  private static byte[] toByteArray(ByteBuffer buffer) {
    int p = buffer.position();
    int n = buffer.limit() - p;
    byte[] bytes = new byte[n];
    for (int i = 0; i < n; i++) {
      bytes[i] = buffer.get(p++);
    }
    return bytes;
  }
}
