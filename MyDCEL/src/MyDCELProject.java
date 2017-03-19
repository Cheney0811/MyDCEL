import DCEL.*;
import AVLTree.*;
import java.util.ArrayList;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Polygon;


public class MyDCELProject {

		   public static void main(String []args) {
		      vertex v1 = new vertex(100.0,100.0);
		      vertex v2 = new vertex(70.0,90.0);
		      vertex v3 = new vertex(60.0,130.0);
		      vertex v4 = new vertex(45.0,125.0);
		      vertex v5 = new vertex(40.0,130.0);
		      vertex v6 = new vertex(30.0,120.0);
		      vertex v7 = new vertex(35.0,105.0);	      
		      vertex v8 = new vertex(32.0,80.0);
		      vertex v9 = new vertex(20.0,90.0);
		      vertex v10 = new vertex(10.0,50.0);
		      vertex v11 = new vertex(30.0,25.0);
		      vertex v12 = new vertex(40.0,40.0);
		      vertex v13 = new vertex(60.0,5.0);
		      vertex v14 = new vertex(50.0,60.0);
		      vertex v15 = new vertex(80.0,50.0);
		      
		      
		      dcel polygon = new dcel();
		      
		      polygon.addVertex(v1);
		      polygon.addVertex(v2);
		      polygon.addVertex(v3);
		      polygon.addVertex(v4);
		      polygon.addVertex(v5);
		      polygon.addVertex(v6);
		      polygon.addVertex(v7);
		      polygon.addVertex(v8);
		      polygon.addVertex(v9);
		      polygon.addVertex(v10);
		      polygon.addVertex(v11);
		      polygon.addVertex(v12);
		      polygon.addVertex(v13);
		      polygon.addVertex(v14);
		      polygon.addVertex(v15);


		      
		      int[] arrayTomakePolygon = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		      int[] arrayMonotonePolygon1 = {1,2,3,5,6,7};
		      int[] arrayMonotonePolygon2 = {0,1,7,13,14};
		      int[] arrayMonotonePolygon3 = {13,7,8,9,11,12};
		      
		      polygon.addFace(arrayTomakePolygon);

		      Triangulation myTriangle = new Triangulation();
		      
		      dcel newPolygon = myTriangle.makeTriangulation(polygon);
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
		      
		      
		      //Display polygon
		      JFrame frame = new JFrame();
		      frame.setTitle("DrawPoly");
		      frame.setSize(1000, 1000);
		      frame.addWindowListener(new WindowAdapter() {
		        public void windowClosing(WindowEvent e) {
		          System.exit(0);
		        }
		      });
		      Container contentPane = frame.getContentPane();
		      
		      DrawPolyPanel newPolyPanel = new DrawPolyPanel();
		      newPolyPanel.setPolygon(newPolygon);
		      contentPane.add(newPolyPanel);

		      frame.show();
		      
		   } 
}


