package com.JAndroid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


// i am tangent
public class Tangent {
	double slope ;
	int intsectXNew ;
	int intsectYNew ;
	
	public Tangent(int intpointx, int intpointy , double slope){
		this.slope = slope ;
		this.intsectXNew = intpointx ;
		this.intsectYNew = intpointy ;
	}
	
	public int findY(int xNew){
		// given x , return y point
		
		//return ((x-intpointx)*slope + intpointy) ;
		return (int)Math.round( ((xNew-intsectXNew)*slope + intsectYNew) );
	}
	
	/*
	public boolean[][] toBoolean(int w ,int h ,int xStart ,int length) {
		boolean[][] b = new boolean[w][h] ;
		
		for(int i=0 ; i<w ; i++)
			for(int j=0 ; j<h ; j++)
				b[i][j] = false ;
		
		
		for( int i=0 ; i<length ; i++ ){
			int x = xStart+i ;
			b[ Axis.toOldAxisX(x) ][  Axis.toOldAxisY( findY(x) )  ] = true ;
		}
		
		return b ;
	}*/
	
	
	public List<Point> toList(int xStart ,int length){
		
		List<Point> list = new ArrayList<Point>() ;
		
		
		for( int i=0 ; i<length ; i++ ){
			int x = xStart+i ;
			list.add( new Point( x  , findY(x) ) ) ;
			//b[ Axis.toOldAxisX(x) ][  Axis.toOldAxisY( findY(x) )  ] = true ;
		}
		
		return list ;
	}
	
}
