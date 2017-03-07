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
	public void addHEdge(vertex v1, vertex v2){
		halfedge he = new halfedge(v1,v2);
		heList.add(he);
	}
	
	//Create the topology by adding a simple polygon
	public void addFace(int[] vertices){
		
		face newFace = new face();
		
		halfedge innerComponent = new halfedge(this.vList.get(vertices[0]),this.vList.get(vertices[1]));
		halfedge outerComponent = new halfedge(this.vList.get(vertices[1]),this.vList.get(vertices[0]));
		
		newFace.setinnerComponent(innerComponent);
		newFace.setouterComponent(outerComponent);
		
		halfedge tempPrevHe = null;
		halfedge tempNextHe = null;
		for(int vIndex = 0; vIndex <vertices.length; vIndex++){
			halfedge he = new halfedge(this.vList.get(vertices[vIndex]), this.vList.get(vertices[(vIndex+1)%(vertices.length)]));
			he.setIncidentFace(newFace);
			
			he.setNextHalfEdge(tempNextHe);
			he.setPrevHalfEdge(tempPrevHe);
			
			tempPrevHe = he;
		}
		
	}
	
	
}
