package DCEL;

public class Segment {
	vertex v1;
	vertex v2;
	
	//Constructor
	public Segment(vertex V1, vertex V2){
		if (V1.compareTo(V2) == 1)
		{
			this.v1 = V1;
			this.v2 = V2;
		}		
		else
		{
			this.v2 = V1;
			this.v1 = V2;
		}		 
	}
}
