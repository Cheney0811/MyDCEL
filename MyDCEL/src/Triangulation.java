import DCEL.*;
import DCEL.vertex.VertexType;

import java.util.ArrayList;
import java.util.Collections;

import AVLTree.*;

public class Triangulation {

	public dcel makeMonotone(dcel simplePolygon){
		dcel toReturn = new dcel();
		
		toReturn = simplePolygon;
		//Check vertex type
		checkVeretxType(toReturn);
		//Get the fList from the dcel, it should only have one face
		ArrayList<face> faces = toReturn.getFaces();		
		halfedge incidentEdge = faces.get(0).getinnerComponent();
		
		//Create vertex event queue with y coordinate as priority
		ArrayList<halfedge> Event = new ArrayList<halfedge>();
		//Create BST storing edges for status update
		AVLTree<Double, halfedge> statusTree = new AVLTree<Double, halfedge>();		
		
		for(halfedge edge = incidentEdge; ; edge = edge.getNextHalfEdge()){			
			Event.add(edge);			
			if(edge == incidentEdge.getPrevHalfEdge())
				break;
		}
		Collections.sort(Event); 
		
		//Create an arraylist to store the helper edge for each vertex
		ArrayList<halfedge> helper = new ArrayList<halfedge>();
		for(int i = 0; i < Event.size(); i++){
			helper.add(null);
		}
			
		//Handle all the event vertices
		for(int i = Event.size() - 1; i >-1; i--){
			//If the vertex is a Start type
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.Start){
				statusTree.insert(getVertexEdgeRelation(Event.get(i), Event.get(i).getOrinVertex()), Event.get(i));
				helper.set(i, Event.get(i));
			}		
			
			//If the vertex is a end type
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.End){
				if(helper.get(i-1).getOrinVertex().getVertexType() == VertexType.End){
					toReturn.addHEdge(Event.get(i), helper.get(i-1));
				}
				statusTree.delete(getVertexEdgeRelation(Event.get(i-1), Event.get(i-1).getOrinVertex()));
			}					
			//If the vertex is a split type
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.Split){
				
			}
			//If the vertex is a merge type
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.Merge){
				
			}
			//If the vertex is a regular vertex
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.Regular){
				
			}
		}
		
//		//test
//		while(vertexEvent.size() != 0){
//			System.out.println(vertexEvent.get(vertexEvent.size() - 1).getX() + " " + vertexEvent.get(vertexEvent.size() - 1).getY() + " " + vertexEvent.size());
//			vertexEvent.remove(vertexEvent.size() - 1);
//		}
	
		return toReturn;
	}
	
	
	public dcel triangulateMonotonePolygon(dcel ymonotoPolygon){
		dcel toReturn = new dcel();
		
		
		
		
		return toReturn;			
	}
	
	public void checkVeretxType(dcel simplePolygon){
		//Get the fList from the dcel, it should only have one face
		ArrayList<face> faces = simplePolygon.getFaces();				
		halfedge incidentEdge = faces.get(0).getinnerComponent();
		
		for(halfedge edge = incidentEdge; ; edge = edge.getNextHalfEdge()){
			vertex v = edge.getOrinVertex();			
			vertex vPrev = edge.getPrevHalfEdge().getOrinVertex();
			vertex vNext = edge.getNextHalfEdge().getOrinVertex();
			
			if(v.compareTo(vPrev) == -1 && v.compareTo(vNext) == -1){
				if((v.getX()-vPrev.getX())*(vNext.getY()-v.getY()) - (v.getY()-vPrev.getY())*(vNext.getX()-v.getX()) > 0)
					v.setVertexType(VertexType.Start);
				else
					v.setVertexType(VertexType.Split);
			}
			
			else if(v.compareTo(vPrev) == 1 && v.compareTo(vNext) == 1){
				if((v.getX()-vPrev.getX())*(vNext.getY()-v.getY()) - (v.getY()-vPrev.getY())*(vNext.getX()-v.getX()) > 0)
					v.setVertexType(VertexType.End);
				else
					v.setVertexType(VertexType.Merge);
			}			
			else
				v.setVertexType(VertexType.Regular);
			
			if(edge == incidentEdge.getPrevHalfEdge())
				break;
		}
	}
	
	//Get x coordinate of the intersection of an halfedge and a vertical line
	//Precondition, the edge does intersect with the line.
	public double getVertexEdgeRelation(halfedge he,vertex v){		
		double toReturn;
		
		vertex v1 = he.getOrinVertex();
		vertex v2 = he.getTwinEdge().getOrinVertex();
		
		toReturn = v2.getX() + (v1.getX()-v2.getX())*(v.getY()-v2.getY())/(v1.getY()-v2.getY());
		return toReturn;		
	}

}
