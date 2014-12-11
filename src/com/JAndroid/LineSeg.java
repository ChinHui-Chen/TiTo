package com.JAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class LineSeg {
	static List<Point> upper ;
	
	int thedaMax = 0 ;
	int thedaMin = 0 ;
	Line leftLine = null ;
	Line rightLine = null ;
	
	Map<Integer,Double> thedaDist = new HashMap<Integer,Double>() ;
	//List<Integer> thedaList = new ArrayList<Integer>() ;
	
	public LineSeg(int lp , int rp){
		
		// for each lineseg point , build theda->dist
		for(int i=lp ; i<=rp ; i++){
			Point p = upper.get(i) ;
			
			//build theda -> dist map
			int theda = (int)Math.round(p.getTheda()) ;
			double dist = p.getDist() ;
			if( thedaDist.get(theda) == null ){
				thedaDist.put( theda , dist ) ;
				//thedaList.add(theda) ;
			}
			
			//find theda min/max
			if( i==lp ){
				thedaMin = thedaMax = theda ;
			}
			if( i==rp ){
				thedaMin = theda ;
			}
		}
		
		//fill in thedaDist hashMap
		double d = 0 ;
		for(int t=thedaMin ; t<=thedaMax ; t++){
			if( thedaDist.get( t ) != null ){
				d = thedaDist.get( t ) ;
			}else{ // == null
				thedaDist.put( t , d ) ;
			}
		}
		
		leftLine = new Line( upper.get(lp) ) ;
		rightLine = new Line( upper.get(rp) ) ;
	}
	
	public boolean isIn(Point p){
	
		int theda_p = (int)Math.round(p.getTheda()) ;
		
		//double dist_p = p.getDist() ;
		//find dist 
		//double dist_near = thedaDist.get( getNearestTheda(theda_p) ) ;
		
		if( leftLine.isAtRight(p) && rightLine.isAtLeft(p) ){
			
			if( thedaDist.get( getNearestTheda(theda_p) ) ==null ){
				Log.e("TAG" , "ERROR : theda not found") ;
			}
			
			if( p.getDist() > thedaDist.get( getNearestTheda(theda_p) ) ){
				return true;
			}
			
		}
		return false ;
		
		//if( (p.getY() > lower.getY())  &&  leftLine.isAtRight(p) && rightLine.isAtLeft(p) ){
		//	return true ;
		//}
		//return false ;
	}
	
	
	private int getNearestTheda( int theda ){
		if(theda > thedaMax){
			return thedaMax ;
		}
		if(theda < thedaMin){
			return thedaMin ;
		}
		return theda ;
		//return thedaDist.get(theda) ;
		
		//if( thedaDist.get(theda) != null ){
		//	return theda ;
		//}
		//do binary search on thedaList
		//return binarySearch(theda) ;
	}
	/*
	private int binarySearch( int theda )
    {
		List<Integer> a = thedaList ;
		
        int low = 0;
        int high = a.size() - 1;
        int mid;

        while( low <= high )
        {
            mid = ( low + high ) / 2;

            if( a.get(mid) < theda )
                low = mid + 1;
            else if( a.get(mid) > theda )
                high = mid - 1;
            else
                return a.get(mid);
        }
        return a.get(low) ;
        //return NOT_FOUND;     // NOT_FOUND = -1
    }*/
}
