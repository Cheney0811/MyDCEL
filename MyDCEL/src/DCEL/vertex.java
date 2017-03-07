package DCEL;

public class vertex {
	protected int index;
	//Geometry info
	protected double x;
	protected double y;
	
	//Half edge info
	halfedge halfEdgeOut;
	halfedge halfEdgeIn;
	
	//Constructor for vertex
	public vertex(double X, double Y){
		this.x = X;
		this.y = Y;
	}
	public vertex(double X, double Y, int Index){
		this.x = X;
		this.y = Y;
		this.index = Index;
	}
	public vertex(vertex v){
		this.x = v.x;
		this.y = v.y;
		this.index = v.index;
	}
	
	public void setIndex(int Index){
		this.index = Index;
	}
	
	//Set halfedge
	public void setHalfEdge(halfedge inE, halfedge outE){
		this.halfEdgeIn = inE;
		this.halfEdgeOut = outE;
	}
	public void setHalfEdgeOut(halfedge e){
		this.halfEdgeOut = e;
	}
	public void setHalfEdgeIn(halfedge e){
		this.halfEdgeIn = e;
	}
	
	//Get halfedge	
	public halfedge getHalfEdgeOut(){
		return halfEdgeOut;
	}
	public halfedge getHalfEdgeIn(){
		return halfEdgeIn;
	}
	
	//vertex operation
	public boolean equalTo(vertex v){
		if(this.x == v.x && this.y == v.y)
			return true;
		else
			return false;
	}
	
	public int above(vertex v){
		if(this.y > v.y)
			return 1;
		else if (this.y < v.y)
			return -1;
		else 
			return 0;
	}
}
