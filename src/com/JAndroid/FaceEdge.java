package com.JAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FaceEdge {
	Map<Integer, Integer> edgePoints = new HashMap<Integer, Integer>();
	
	public void insert(int x , int y){
		edgePoints.put(x,y) ;
	}
	
	public void insert(Point p){
		edgePoints.put( p.getNewX() , p.getNewY() ) ;
	}
	
	public int getY(Integer x){
		int y = 0;
		try{
			y = edgePoints.get(x) ;
		}catch(NullPointerException e){
			y = 0 ;
		}
		return y ;
	}
	
	/*
	public boolean[][] toBoolean(int w, int h){
		boolean[][] b = new boolean[w][h] ;
		
		for(int i=0 ; i<w ; i++)
			for(int j=0 ; j<h ; j++)
				b[i][j] = false ;
		
		Iterator<Integer> it = edgePoints.keySet().iterator(); 
		while(it.hasNext()) { 
		        Integer key = (Integer)it.next();
		        b[ Axis.toOldAxisX(key) ][ Axis.toOldAxisY(getY(key)) ] = true ; 
		}
		return b ;
	}*/
	
	public List<Point> toList(){
		
		List<Point> list = new ArrayList<Point>() ;
		
		Iterator<Integer> it = edgePoints.keySet().iterator(); 
		while(it.hasNext()) { 
		        Integer key = (Integer)it.next();
		        list.add( new Point( key , getY(key) ) ) ;
		        //b[ Axis.toOldAxisX(key) ][ Axis.toOldAxisY(getY(key)) ] = true ; 
		}
		
		return list ;
	}
	
	
	public Map<Integer, Integer> toHash() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		Iterator<Integer> it = edgePoints.keySet().iterator(); 
		while(it.hasNext()) { 
		        Integer key = (Integer)it.next();
		        map.put( key , getY(key) ) ;
		}
		
		return map ;
	}
	
	
}
