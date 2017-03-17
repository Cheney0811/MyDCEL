import DCEL.*;
import AVLTree.*;
import java.util.ArrayList;

public class MyDCELProject {

		   public static void main(String []args) {
		      vertex v1 = new vertex(-1.0,1.0);
		      vertex v2 = new vertex(0.0,-1.0);
		      vertex v3 = new vertex(1.0,0.5);
		      vertex v4 = new vertex(0.0,0.0);
		      
		      
		      
		      dcel polygon = new dcel();
		      
		      polygon.addVertex(v1);
		      polygon.addVertex(v2);
		      polygon.addVertex(v3);
		      polygon.addVertex(v4);
		      
		      int[] arrayTomakePolygon = {0,1,2,3};
		      
		      polygon.addFace(arrayTomakePolygon);

		      Triangulation myTriangle = new Triangulation();
		      
		      myTriangle.makeMonotone(polygon);
//		      AVLTree<vertex, vertex> tree = new AVLTree<vertex, vertex>();
//		      
//		      tree.insert(v1,v1);
//		      tree.insert(v2,v2);
//		      tree.insert(v3,v3);
//		      tree.insert(v4,v4);
			   AVLTree<Double, String> tree = new AVLTree<Double, String>();
			   tree.insert(9.0, "Element");
			   tree.insert(1.0, "Element");
			   tree.insert(2.0, "Element");
			   tree.insert(3.0, "Element");
			   tree.insert(4.0, "Element");
			   tree.insert(5.0, "Element");
			   tree.insert(6.0, "Element");
			   tree.insert(7.0, "Element");
			   tree.insert(8.0, "Element");

		      tree.printTree();
		      System.out.println(tree.lookupLeftClosest(4.2).getKey());
		      System.out.println(tree.lookupRightClosest(3.2).getKey());
		      
		   } 
}


