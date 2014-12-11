package com.JAndroid;

public class Line {
	
	double slope ;
	double theda ;
	
	public Line(Point target){
		slope = target.getSlope() ;
		theda = target.getTheda() ;
	}
	
	
	public int getX(int y){
		return (int)Math.round( y/(double)slope );
	}
	
	
	public boolean isAtRight(Point p){
		
		if( p.getTheda() <= theda ){
			return true ;
		}else{
			return false ;
		}
		
	}
	
	public boolean isAtLeft(Point p){
		
		if( p.getTheda() >= theda ){
			return true ;
		}else{
			return false ;
		}
		
	}


}
