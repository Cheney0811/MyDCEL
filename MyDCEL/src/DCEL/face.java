package DCEL;

public class face {
	protected halfedge outerComponent; 
	protected halfedge innerComponent;
	
	public face(){
		outerComponent = null;
		innerComponent = null;
	}
	
	public face(halfedge in, halfedge out){
		innerComponent = in;
		outerComponent = out;
	}
	
	//Setter
	public void setinnerComponent(halfedge e){
		innerComponent = e;
	}
	
	public void setouterComponent(halfedge e){
		outerComponent = e;
	}
	
	//Getter
		public halfedge getinnerComponent(){
			return innerComponent;
		}
		
		public halfedge getouterComponent(){
			return outerComponent;
		}
}
