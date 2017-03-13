package DCEL;
import java.util.ArrayList;

public class vertex {
	protected int index;
	//Geometry info
	protected double x;
	protected double y;
	
	//Half edge info:Incident halfedge
	ArrayList<halfedge> halfEdgeOut;
	
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
	public void setHalfEdge(halfedge outE){
		this.halfEdgeOut.add(outE);
	}
	
	//Get halfedge	
	public ArrayList<halfedge> getHalfEdgeOut(){
		return halfEdgeOut;
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
