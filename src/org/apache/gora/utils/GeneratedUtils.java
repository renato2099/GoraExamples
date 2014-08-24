package org.apache.gora.utils;

//import org.apache.gora.examples.generated.old.Edge;
//import org.apache.gora.examples.generated.old.Simpson;
//import org.apache.gora.examples.generated.old.Vertex;

/**
 * Class used to create necessary objects to be tested.
 * @author renatomarroquin
 *
 */
public class GeneratedUtils {

  /**
   * Generates a couple of vertices attached one to another.
   * @return Map containing vertices.
   
  public static Map<String, Vertex> getVertices() {
    // Creating vertices
    Vertex vrtx1, vrtx2;
    vrtx1 = new Vertex();
    vrtx2 = new Vertex();
    Map<String, Vertex> graph = new HashMap<String, Vertex>();
    
    // Setting ids.
    vrtx1.setVertexId(new Utf8("A"));
    vrtx1.setValue(1f);
    Edge edg1 = new Edge();
    edg1.setEdgeVertexId(new Utf8("B"));
    vrtx1.addToEdges(edg1);
    
    vrtx2.setVertexId(new Utf8("B"));
    vrtx2.setValue(1f);
    Edge edg2 = new Edge();
    edg2.setEdgeVertexId(new Utf8("A"));
    vrtx2.addToEdges(edg2);
    
    graph.put("A", vrtx1);
    graph.put("B", vrtx2);
    return graph;
  }*/

  /*public static Simpson createSimpson(String pKey) {
    Simpson familyMmbr = new Simpson();
    familyMmbr.setFirstname(new Utf8(pKey));
    familyMmbr.setLastname(new Utf8("Simpson"));
    return familyMmbr;
  }*/
}
