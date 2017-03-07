package DCEL;

public class halfedge {
	
	vertex vertexOrin;
	vertex vertexTo;
	
	halfedge next;
	halfedge prev;
	halfedge twin;
	
	face incidentFace;
	
	//Constructor
	public halfedge(){
	}
	
	public halfedge(vertex orin, vertex to){
		this.vertexOrin = orin;
		this.vertexTo = to;
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
}
