package com.JAndroid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.util.Log;

public class Slice {
	public class byX implements java.util.Comparator {
		 public int compare(Object first, Object second) {
		  int sdif = ((Point)first).getOriX() - ((Point)second).getOriX();
		  return sdif;
		 }
	}
	List<Point> upper = new ArrayList<Point>();
	List<Point> lower = new ArrayList<Point>();
	List<Point> collect = new ArrayList<Point>() ;
	
	
	
	//Point left ;
	//Point right ;
	//Line leftLine ;
	//Line rightLine ;

	FaceEdge he ;
	FaceEdge fe ;
	List<LineSeg> lseg = new ArrayList() ;
	
	// for point
	public Slice(Point p , FaceEdge faceEdge ,  FaceEdge headEdge){
		collect.add(p) ;
		
		this.he = headEdge ;
		this.fe = faceEdge ;
	}
	
	public void addPoint(Point p){
		collect.add(p) ;
	}
	
	public void setRect(int w){
		Map<Integer,Integer> table = new TreeMap<Integer,Integer>() ; //x,y
		
		//for all point
		for(int k=0 ; k<collect.size() ; k++){
			
			Point p = collect.get(k) ;
			int x = p.getOriX() ;
			int y = p.getOriY() ;
			
			// find rect
			for( int newx = x-w ; newx <=x+w ; newx++ ){
				for( int newy = y-w ; newy <= y+w ; newy++ ){
					Point cur = new Point( Axis.toNewAxisX( newx ) , Axis.toNewAxisY( newy ) ) ;
					
					if( cur.isHigherThan(he) && cur.isHigherThan(fe) ){
						
						if( table.get( cur.getX() ) == null ) {
							table.put( cur.getX() , cur.getY() ) ; // x,y
						}else{ //!=null
							
							if( cur.getY() < table.get(cur.getX()) ){
								//delete
								table.remove(cur.getX()) ;
								//put
								table.put( cur.getX() , cur.getY() ) ;
							}
							
						}
						
					}else if( cur.isHigherThan(fe) ){
						lower.add(cur) ;
					}
				}
			}
		}
		//table
		Collection c = table.keySet() ;
		Iterator iterator = c.iterator() ;
			
	    while(iterator.hasNext()) {
	        	int xkey = (Integer) iterator.next(); 
	     	upper.add( new Point( xkey , table.get(xkey) )) ;
	    }
	        
	    //sort upper
	    Collections.sort(upper , new byX() );
	        
	    //upper's boundary , lower done
		
	}
	
	public List<Point> getCollectList(){
		return collect ;
	}
	public List<Point> getUpperBoundaryList(){
		return upper ;
	}
	
	public List<Point> getUpperList(){
		if(upper.size() == 0){
			return new ArrayList<Point>() ;
		}
	
		//find line seg
		int lp = 0 ;
		int rp = 0 ;
		LineSeg.upper = upper ;
		
		//detect left , right boundary for each line seg
		for(int i=0 ; i<upper.size() ; i++){
			
			if( i==0 ){ //Start
				//something change
				lp = 0 ;
			}else{
				//if( ((upper.get(i).getOriX()  -  upper.get(i-1).getOriX()) != 1)  ||  (upper.get(i).getOriY() != upper.get(i-1).getOriY())  ){
				if( ((upper.get(i).getOriX()  -  upper.get(i-1).getOriX()) != 1) ){
					//something change
					rp = i-1 ;
					//new seg l,r
					lseg.add( new LineSeg(lp , rp) ) ;
					
					lp = i ;
				}
			}
		}
		//add last one
		rp = upper.size() -1  ;
		lseg.add( new LineSeg(lp , rp) ) ;
		// LineSeg is done
		
		//setTheda(_theda) ;
		//find area
		
		return getUpperAreaList( TiHair.picw ) ;		
	}
	
	public List<Point> getLowerList(){
		return lower ;
	}
	
	/*
	private void setTheda(int _theda){
		this.theda = _theda*0.0175 ;
		Point leftRotate ;
		Point rightRotate ;
			
		if(_theda == 0){
			leftRotate = left ;
			rightRotate = right ;
		}else{
			leftRotate = rotate(left , -1*theda) ;
			rightRotate = rotate(right , theda ) ;
		}
		
		leftLine = new Line(leftRotate) ;
		rightLine = new Line(rightRotate) ;
	}
	*/
	
	private List<Point> getUpperAreaList(int w) {
		List<Point> list = new ArrayList<Point>() ;
		
		// for the search area
		for(int x=Axis.toNewAxisX(0) ; x<(Axis.toNewAxisX(w)-1)  ; x++ ){
				
				int y = he.getY(x);
				Log.e( "TAG" , Integer.toString(x)+":"+Integer.toString(y) ) ;
				
				//for each point above head(he)
				for(int j=y ; j< Axis.toNewAxisY(0) ; j++ ){
					
					Point tmp = new Point(x,j) ;
					
					for(int i=0 ; i<lseg.size() ; i++){
						if( lseg.get(i).isIn(tmp) ){
							list.add(tmp) ;
							break;
						}
					}	
				}
		}
		
		return list ;
	}
	
	
	private Point rotate(Point p, double theda){
		return new Point( (int) Math.round( ( p.getX()*Math.cos(theda) ) - ( p.getY()*Math.sin(theda) ) )  , (int) Math.round((p.getX()*Math.sin(theda)) + (p.getY()*Math.cos(theda))) ) ;
	}

}
