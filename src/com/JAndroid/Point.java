package com.JAndroid;

public class Point {

	int x ;
	int y ;
	
	Point(int x, int y){
		this.x = x ;
		this.y = y ;
	}
	
	public int getX(){
		return x ;
	}
	public int getY(){
		return y ;
	}
	
	public int getNewX(){
		return getX() ;
	}
	
	public int getNewY(){
		return getY() ;
	}
	
	public int getOriX(){
		return Axis.toOldAxisX(x) ;
	}
	
	public int getOriY(){
		return Axis.toOldAxisY(y) ;
	}
	
	
	public double getSlope(){
		if(getX() == 0){
			return Double.POSITIVE_INFINITY ;
		}else{
			return ( getY() /(double) getX() ) ; 
		}
	}
	
	public double getTheda(){
		//if( x == 0 ){
		//	return 90 ;
		//}else{
		//	return Math.toDegrees( Math.atan( y/(double)x ) ) ;
		//}
		return Math.toDegrees( Math.acos(  x/(double)Math.pow( x*x+y*y , 0.5 )  ) ) ;
	}
	
	public double getDist(){
		return (x*x) + (y*y) ;
	}
	
	
	public boolean isHigherThan(FaceEdge e){ 
		if( y > e.getY(x)  ){
			return true ;
		}
		return false ;
	}
	
	
	
}
