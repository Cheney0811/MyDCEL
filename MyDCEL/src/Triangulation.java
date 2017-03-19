import DCEL.*;
import DCEL.vertex.VertexType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import AVLTree.*;

import java.util.Stack;

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
		
		//Create an map to store the helper edge for each vertex
		Map<halfedge, halfedge> helper = new HashMap<>();	
		
		//Handle all the event vertices
		for(int i = Event.size() - 1; i >-1; i--){
			//If the vertex is a Start type
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.Start){
				
				statusTree.printTree();				//Test code
				
				statusTree.insert(getVertexEdgeRelation(Event.get(i), Event.get(i).getOrinVertex()), Event.get(i));
				
				//Update Status Tree
				updateKeyValue(Event.get(i).getOrinVertex(), statusTree);
				
				helper.put(Event.get(i), Event.get(i));

			}		
			
			//If the vertex is a end type
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.End){

				//Update Status Tree
				updateKeyValue(Event.get(i).getOrinVertex(), statusTree);
				
				statusTree.printTree();				//Test code
				
				halfedge temp = helper.get(Event.get(i).getPrevHalfEdge()); //test code
				

				if(helper.get(Event.get(i).getPrevHalfEdge())!=null && 
						helper.get(Event.get(i).getPrevHalfEdge()).getOrinVertex().getVertexType() == VertexType.Merge){
					toReturn.addHEdge(Event.get(i), helper.get(Event.get(i).getPrevHalfEdge()));
				}
				
				statusTree.delete(getVertexEdgeRelation(Event.get(i).getPrevHalfEdge(), Event.get(i).getOrinVertex()));
			}					
			//If the vertex is a split type
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.Split){
				//Update Status Tree
				updateKeyValue(Event.get(i).getOrinVertex(), statusTree);
				statusTree.printTree();				//Test code
				halfedge tempEdge = statusTree.lookupLeftClosest(Event.get(i).getOrinVertex().getX()).getValue();
				if(helper.get(tempEdge)!=null){
					toReturn.addHEdge(Event.get(i), helper.get(tempEdge));
				}				
				
				helper.put(tempEdge, Event.get(i));
				
				statusTree.insert(getVertexEdgeRelation(Event.get(i), Event.get(i).getOrinVertex()), Event.get(i));
				
				helper.put(helper.get(tempEdge), Event.get(i));

			}
			//If the vertex is a merge type
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.Merge){
				//Update Status Tree
				updateKeyValue(Event.get(i).getOrinVertex(), statusTree);

				statusTree.printTree();				//Test code
				
				//Temp half edge marks the previous halfedge of the current halfedge
				double keyToDelete = getVertexEdgeRelation(Event.get(i).getPrevHalfEdge(), Event.get(i).getOrinVertex());
				if(helper.get(Event.get(i).getPrevHalfEdge())!=null){
					vertex check = helper.get(Event.get(i).getPrevHalfEdge()).getOrinVertex();//Test code
					if(helper.get(Event.get(i).getPrevHalfEdge()).getOrinVertex().getVertexType() == VertexType.Merge){
						toReturn.addHEdge(Event.get(i), helper.get(Event.get(i).getPrevHalfEdge()));
					}
				}				
				statusTree.delete(keyToDelete);
				
				halfedge tempEdge = statusTree.lookupLeftClosest(Event.get(i).getOrinVertex().getX()).getValue();
				if(tempEdge.getOrinVertex().getVertexType() == VertexType.Merge)
					toReturn.addHEdge(Event.get(i), helper.get(tempEdge));
				
				helper.put(tempEdge,Event.get(i));
			}
			//If the vertex is a regular vertex
			if(Event.get(i).getOrinVertex().getVertexType() == VertexType.Regular){
				//Update Status Tree
				updateKeyValue(Event.get(i).getOrinVertex(), statusTree);
				
				statusTree.printTree();				//Test code
				
				if(Event.get(i).getOrinVertex().getY() >= Event.get(i).getNextHalfEdge().getOrinVertex().getY()){
					double keyToDelete = getVertexEdgeRelation(Event.get(i).getPrevHalfEdge(), Event.get(i).getOrinVertex());
					if(helper.get(Event.get(i).getPrevHalfEdge())!=null && 
							helper.get(Event.get(i).getPrevHalfEdge()).getOrinVertex().getVertexType() == VertexType.Merge){
						toReturn.addHEdge(Event.get(i), helper.get(Event.get(i).getPrevHalfEdge()));	
					}
					statusTree.delete(keyToDelete);
					statusTree.insert(getVertexEdgeRelation(Event.get(i), Event.get(i).getOrinVertex()),Event.get(i));
							
					helper.put( Event.get(i), Event.get(i));		
										
				}	
				else{
					halfedge tempEdge = statusTree.lookupLeftClosest(Event.get(i).getOrinVertex().getX()).getValue();
					if(tempEdge.getOrinVertex().getVertexType() == VertexType.Merge){
						toReturn.addHEdge(Event.get(i), helper.get(tempEdge));
						
					}
					helper.put(tempEdge,Event.get(i));				
				}
			}
		}
		return toReturn;
	}
	
	
	public dcel triangulateMonotonePolygon(dcel ymonotonePolygon){
		dcel toReturn = new dcel();
		
		toReturn = ymonotonePolygon;
		
		ArrayList<face> faces = toReturn.getFaces();
		
		int realFindex = 0;
		for(int i = 0; i < faces.size();){
			
			//Set a mark for the current i value
			int mark = i;
			
			//Create vertex event queue with y coordinate as priority
			ArrayList<halfedge> Event = new ArrayList<halfedge>();
			
			halfedge incidentEdge = faces.get(realFindex).getinnerComponent();
					
			for(halfedge edge = incidentEdge; ; edge = edge.getNextHalfEdge()){			
				Event.add(edge);	
				
				if(edge == incidentEdge.getPrevHalfEdge())
					break;
			}			
			Collections.sort(Event);
			//Check which chain each event belongs to -1 means left , 1 means right, 0 means top or bottom
			Map<vertex,Integer> chainSide = new HashMap<>();
			halfedge edge = Event.get(0);
			
			chainSide.put(edge.getOrinVertex(),0);
			while(edge.getTwinEdge().getOrinVertex() != Event.get(Event.size()-1).getOrinVertex()){
				edge = edge.getNextHalfEdge();
				chainSide.put(edge.getOrinVertex(),1);
			}
			edge = edge.getNextHalfEdge();
			chainSide.put(edge.getOrinVertex(),0);
			while(edge.getOrinVertex() != Event.get(0).getOrinVertex()){
				edge = edge.getNextHalfEdge();
				chainSide.put(edge.getOrinVertex(),-1);
			}
			
			ArrayList<halfedge> S = new ArrayList<halfedge>();
			S.add(Event.get(Event.size()-1));
			S.add(Event.get(Event.size()-2));
			for(int j = 3; j < Event.size(); j++){
				if(chainSide.get(Event.get(Event.size()- j).getOrinVertex()) * chainSide.get(S.get(S.size()-1).getOrinVertex()) < 0){
					while(S.size() > 1){
						halfedge tempHe = S.remove(0);
						tempHe = S.remove(0);
						
						if(S.size() >= 0){
							halfedge tempHe_New = toReturn.addHEdge(Event.get(Event.size()-j), tempHe).getTwinEdge();
							Event.set(Event.size() -j + 1, tempHe_New);
							i++;
						}										
						
					}		
					S.add(Event.get(Event.size() -j + 1));
					S.add(Event.get(Event.size() - j));
				}
				else{
					halfedge tempHe = new halfedge();
					tempHe = S.remove(S.size()-1);
					while(!S.isEmpty() && toReturn.checkDiagonal(Event.get(Event.size() - j), S.get(S.size()-1))){
						tempHe = S.remove(S.size()-1);
						if(chainSide.get(Event.get(Event.size() - j).getOrinVertex()) == -1){
							halfedge tempHe_New = toReturn.addHEdge(Event.get(Event.size() - j), tempHe).getTwinEdge();							
							tempHe = tempHe_New;
						}						
						else{
							halfedge tempHe_New = toReturn.addHEdge(Event.get(Event.size() - j), tempHe);
							Event.set(Event.size() - j, tempHe_New);
						}
							
						i++;
					}
					if(tempHe.getOrinVertex()!=null)
						S.add(tempHe);
					
					S.add(Event.get(Event.size() - j));
				}				
					
			}
			
			if(S.size() > 2){
				for( int sIndex = 1; sIndex < S.size()-1; sIndex++){
					toReturn.addHEdge(Event.get(0), S.get(sIndex));
					i++;
				}
			}
			if(mark == i){
				realFindex++;

			}
			i++;
		}	 	

	
		return toReturn;			
	}
	
	public dcel makeTriangulation(dcel simplePolygon){
		dcel toReturn = new dcel();
		toReturn = simplePolygon;
		toReturn = makeMonotone(simplePolygon);
		toReturn = triangulateMonotonePolygon(toReturn);
		
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
			
			if(v.getY() > vPrev.getY() && v.getY() > vNext.getY()){
				if((v.getX()-vPrev.getX())*(vNext.getY()-v.getY()) - (v.getY()-vPrev.getY())*(vNext.getX()-v.getX()) > 0)
					v.setVertexType(VertexType.Start);
				else
					v.setVertexType(VertexType.Split);
			}
			
			else if(v.getY() < vPrev.getY() && v.getY() < vNext.getY()){
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

	//Update the key value when ever a new event is handled
	public void updateKeyValue(vertex v, AVLTree<Double,halfedge> statusTree){
		
		ArrayList<AVLNode<Double,halfedge>> nodesInStatusTree = new ArrayList<AVLNode<Double,halfedge>>();
		//Ref:https://gist.github.com/antonio081014/5939018
		if(statusTree.getRoot() != null){
			Queue<AVLNode<Double,halfedge>> queue = new LinkedList<AVLNode<Double,halfedge>>();
			queue.add(statusTree.getRoot());
			while (!queue.isEmpty()) {
				AVLNode<Double,halfedge> node = queue.poll();			
				nodesInStatusTree.add(node);
				
				AVLNode<Double,halfedge> left = node.getLeft();
				AVLNode<Double,halfedge> right = node.getRight();
				if (left != null) {
					queue.add(left);
				}
				if (right != null) {
					queue.add(right);
				}
			}
			
			//Set new key for the node
			for(int i = 0; i < nodesInStatusTree.size(); i++){
				halfedge tempNewEdge= statusTree.lookup(nodesInStatusTree.get(i).getKey());
				double newKey = getVertexEdgeRelation(tempNewEdge, v);			
				statusTree.delete(nodesInStatusTree.get(i).getKey());
				statusTree.insert(newKey, tempNewEdge);
			}
			
		}
		
	}
}
