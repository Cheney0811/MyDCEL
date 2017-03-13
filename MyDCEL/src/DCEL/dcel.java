package DCEL;

import java.util.ArrayList;

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
	
	//Create the topology by adding a simple polygon
	public void addFace(int[] vertices){
		
		
		
		
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
	public boolean checkSimplePolygon(int[] vertices){
		
		
		return true;
	}
}
