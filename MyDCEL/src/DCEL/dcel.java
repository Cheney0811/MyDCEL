package DCEL;

import java.util.ArrayList;
import java.util.Collections;
import AVLTree.*;

public class dcel {
	ArrayList<vertex> vList = new ArrayList<vertex>();
	ArrayList<halfedge> heList = new ArrayList<halfedge>();
	ArrayList<face> fList = new ArrayList<face>();
	
	//Initialize the vertex list
	public void setvList(vertex[] vertices){
		for(int vIndex = 0; vIndex < vertices.length; vIndex++){
			vertex v = new vertex(vertices[vIndex]);
			v.setIndex(vIndex);
			vList.add(vertices[vIndex]);
		}
	}
	//Add a vertex
	public void addVertex(vertex v){
		vertex vToAdd = new vertex(v);
		vToAdd.setIndex(this.vList.size());
		vList.add(vToAdd);
	}
	
	//Add a half edge
	public void addHEdge(vertex v1){
		halfedge he = new halfedge(v1);
		heList.add(he);
	}
	
	public void addHEdge(halfedge he1, halfedge he2){
		halfedge newHe1 = new halfedge(he1.vertexOrin);
		halfedge newHe2 = new halfedge(he2.vertexOrin);
		heList.add(newHe1);
		heList.add(newHe2);
		
		newHe1.setTwinEdge(he2);
		newHe2.setTwinEdge(he1);
		
		//Recreate the topology
		newHe1.setNextHalfEdge(he1);
		newHe1.setPrevHalfEdge(he2.getPrevHalfEdge());
		
		newHe2.setNextHalfEdge(he2);
		newHe2.setPrevHalfEdge(he1.getPrevHalfEdge());
		
		he1.setPrevHalfEdge(newHe1);
		he2.setPrevHalfEdge(newHe2);
		
		//Remove old face and add new face
		this.fList.remove(he1.incidentFace);
		
		face newFace1 = new face();
		fList.add(newFace1);
		
		newFace1.setinnerComponent(newHe1);
		newFace1.setouterComponent(newHe2);
		
		face newFace2 = new face();
		fList.add(newFace2);
		
		newFace2.setinnerComponent(newHe2);
		newFace2.setouterComponent(newHe1);
		
		//Set all the halfedges to its new face
		for(halfedge edge = newHe1; ; edge = edge.getNextHalfEdge()){			
			edge.setIncidentFace(newFace1);			
			if(edge == newHe1.getPrevHalfEdge())
				break;
		}
		
		for(halfedge edge = newHe2; ; edge = edge.getNextHalfEdge()){			
			edge.setIncidentFace(newFace2);			
			if(edge == newHe1.getPrevHalfEdge())
				break;
		}
		
	}
	
	//Get face list
	public ArrayList<face> getFaces(){
		return fList;
	}
	//Get halfe
	//Create the topology by adding a simple polygon
	public void addFace(int[] vertices){		
		//Check if the list of vertices makes a simple polygon
		ArrayList<vertex> polygonVList = new ArrayList<vertex>();
		for(int i = 0; i < vertices.length; i++){
			polygonVList.add(vList.get(i));
		}
		if(!checkSimplePolygon(polygonVList)){
			System.err.println("Exiting, non simple polygon input");
			System.exit(1);
		}
		
		face newFace = new face();
		fList.add(newFace);
		
		ArrayList<halfedge> tempheList1 = new ArrayList<halfedge>();
		ArrayList<halfedge> tempheList2 = new ArrayList<halfedge>();
		
		for(int vIndex = 0; vIndex <vertices.length; vIndex++){	
			//Add the halfedges for the innerloop
			halfedge he1 = new halfedge(this.vList.get(vertices[vIndex]));
			he1.setIncidentFace(newFace);			
			tempheList1.add(he1);	
			
			//Add to halfedge list			
			heList.add(he1);
			
			//Add the halfedges for the outerloop
			halfedge he2 = new halfedge(this.vList.get(vertices[vIndex]));
			he2.setIncidentFace(null);			
			tempheList2.add(he2);
			
			//Add to halfedge list
			heList.add(he2);
		}

		for(int i = 0; i < tempheList1.size(); i++){
			tempheList1.get(i).setNextHalfEdge(tempheList1.get((i+1)%tempheList1.size()));
			tempheList1.get((i+1)%tempheList1.size()).setPrevHalfEdge(tempheList1.get(i));
			//Set twin edge.
			tempheList1.get(i).setTwinEdge(tempheList2.get((i+1)%tempheList1.size()));
			
		}
		
		for(int i = 0; i < tempheList2.size(); i++){
			tempheList2.get(i).setNextHalfEdge(tempheList2.get((i+1)%tempheList2.size()));
			tempheList2.get((i+1)%tempheList2.size()).setPrevHalfEdge(tempheList2.get(i));
			
			//Set twin edge.
			tempheList2.get(i).setTwinEdge(tempheList2.get((i+tempheList1.size()-1)%tempheList1.size()));
		}
		
		newFace.setinnerComponent(tempheList1.get(0));
		newFace.setouterComponent(tempheList1.get(0));
		

		
	}
	//Check if a polygon is a simple or complex polygon
	//Return true if it is a simple polygon
	public boolean checkSimplePolygon(ArrayList<vertex> vList){
		boolean toReturn = true;
		ArrayList<Segment> SegList = new ArrayList<Segment>();
		
		for (int i = 0; i < vList.size(); i++) {
			Segment tempSegment = new Segment(vList.get(i),vList.get((i+1)%vList.size()));
			SegList.add(tempSegment);
		}
		//Brutal Force checking intersection
		
		for(int i = 0; i < SegList.size();i++)
		{
			for(int j = 0; j < SegList.size(); j++){
				if(j != (i-1+SegList.size())%SegList.size() && j!= (i+1)%SegList.size() && j != i){
					toReturn = !checkSegmentIntersection(SegList.get(i), SegList.get(j));
					if(!toReturn)
						return toReturn;

				}
			}
					
		}
		
//		AVLTree<vertex, Segment> EventTree = new AVLTree<vertex, Segment>();
//		for (int i = 0; i < SegList.size(); i++) {
//			EventTree.insert(SegList.get(i).v1, SegList.get(i));
//			EventTree.insert(SegList.get(i).v2, SegList.get(i));
//		}
//		
//		
//		//Sweep line checking if there is intersection.
//		//If there is intersection, then return false, otherwise return true.		
//		ArrayList<vertex> tempvList = vList;		
//		Collections.sort(tempvList);
//		//Test code: Output vertex list.
//	      for (int i = 0; i < tempvList.size(); i++) {
//	    	  StatusTree.lookup(key)
//	          StatusTree.insert(tempvList.get(i).getX(), tempvList.get(i));
//	       }
	      
	      
		return toReturn;
	}
	//Return true if intersecing, return false if not intersecting.
	public boolean checkSegmentIntersection(Segment s1, Segment s2){ 
		double check1 = (s1.v1.getX()-s2.v2.getX())*(s2.v1.getY()-s2.v2.getY()) - 
				(s1.v1.getY()-s2.v2.getY())*(s2.v1.getX()-s2.v2.getX());
		double check2 = (s1.v2.getX()-s2.v2.getX())*(s2.v1.getY()-s2.v2.getY()) - 
				(s1.v2.getY()-s2.v2.getY())*(s2.v1.getX()-s2.v2.getX());
		
		double check3 = (s2.v1.getX()-s1.v2.getX())*(s1.v1.getY()-s1.v2.getY()) - 
				(s2.v1.getY()-s1.v2.getY())*(s1.v1.getX()-s1.v2.getX());
		double check4 = (s2.v2.getX()-s1.v2.getX())*(s1.v1.getY()-s1.v2.getY()) - 
				(s2.v2.getY()-s1.v2.getY())*(s1.v1.getX()-s1.v2.getX());
		if(check1*check2 <= 0 && check3*check4 <= 0)
			return true;
		else
			return false;
	}
}
