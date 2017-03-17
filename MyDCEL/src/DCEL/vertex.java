package DCEL;
import java.util.ArrayList;

public class vertex implements Comparable<vertex>{
	protected int index;
	//Geometry info
	protected double x;
	protected double y;
	protected VertexType type;
	
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
	//Set vertex type
	public void setVertexType(VertexType type){
		this.type = type;
	}
	//Get vertex type
	public VertexType getVertexType(){
		return this.type;
	}
	//Set halfedge
	public void setHalfEdge(halfedge outE){
		this.halfEdgeOut.add(outE);
	}
	
	//Get halfedge	
	public ArrayList<halfedge> getHalfEdgeOut(){
		return halfEdgeOut;
	}

	//Get coordinates
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	
	//Get index
	public double getIndex(){
		return this.index;
	}
	//vertex operation
	public boolean equals(vertex v){
		if(this.x == v.x && this.y == v.y)
			return true;
		else
			return false;
	}
	
	public int compareTo(vertex v){
		if(this.y < v.y)
			return -1;
		else if(this.y > v.y)
			return 1;
		else {
			if(this.x < v.x)
				return -1;
			else if (this.x > v.x)
				return 1;
			else
				return 0;
		}
			
	}
	public enum VertexType {
	    Start,End,Merge,Split,Regular
	}
	
}
