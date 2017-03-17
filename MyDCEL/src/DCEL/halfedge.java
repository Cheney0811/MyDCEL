package DCEL;

public class halfedge implements Comparable<halfedge>{
	
	vertex vertexOrin;
	
	halfedge next;
	halfedge prev;
	halfedge twin;
	
	face incidentFace;
	
	//Constructor
	public halfedge(){
	}
	
	public halfedge(vertex orin){
		this.vertexOrin = orin;
	}
	
	//Setter
	public void setIncidentFace(face f){
		this.incidentFace = f;
	}
	
	public void setPrevHalfEdge(halfedge e){
		this.prev = e;
	}
	public void setNextHalfEdge(halfedge e){
		this.next = e;
	}
	public void setTwinEdge(halfedge e){
		this.twin = e;
	}
	//Getter
	public face getIncidentFace(){
		return this.incidentFace;
	}
	
	public halfedge getPrevHalfEdge(){
		return this.prev;
	}
	public halfedge getNextHalfEdge(){
		return this.next;
	}
	public halfedge getTwinEdge(){
		return this.twin;
	}
	
	public vertex getOrinVertex(){
		return this.vertexOrin;
	}
	
	public int compareTo(halfedge he){
		int toReturn = this.getOrinVertex().compareTo(he.getOrinVertex());
		return toReturn;
	}
}
