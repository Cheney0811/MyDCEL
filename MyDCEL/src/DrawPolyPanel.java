//Modified based on Ref: http://www.java2s.com/Code/Java/2D-Graphics-GUI/DrawaPolygon.htm

import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

import DCEL.*;
import java.util.ArrayList;

public class DrawPolyPanel  extends JPanel {
	
	 protected ArrayList<Polygon> p;
	 
	 public void setPolygon(dcel polygons){
		 p = new ArrayList<Polygon>();
		 for(int i = 0; i < polygons.getFaces().size(); i++){
			 Polygon tempP = new Polygon();
			 halfedge incidentEdge = polygons.getFaces().get(i).getinnerComponent();
			 for( halfedge edge = incidentEdge; ; edge = edge.getPrevHalfEdge()){
				 
				 tempP.addPoint(100+5*(int) edge.getOrinVertex().getX(),750-5*(int) edge.getOrinVertex().getY());
				 
				 if(edge == incidentEdge.getNextHalfEdge())
						break;
			 }
			 p.add(tempP);
		 }
	 }
	 public void paintComponent(Graphics g) {
		    super.paintComponent(g);

		    for(int i = 0; i < p.size(); i++){
		    	 g.drawPolygon(p.get(i));
		    }	   
		 
	 }
}
