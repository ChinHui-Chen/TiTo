package com.JAndroid;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class Pixel {
	int pixel ;
	float[] hsv = new float[3];
	
	public Pixel(){
		pixel = 0;
	}
	
	public Pixel( int pixel ){
		this.pixel = pixel ;
	
		//Color.RGBToHSV( Color::red(pixel) , Color::green(pixel) , Color::blue(pixel) , hsv) ;
		Color.colorToHSV(pixel , hsv) ;
	}

	public int getColor(String str){
		
		if( str.equals("R") ){
			int r = (pixel >> 16) & 0xff;
		    return r ;
		}else if( str.equals("G") ){
			int g = (pixel >> 8) & 0xff;
		    return g ;
		}else{ // B
			int b = pixel & 0xff;
		    return b ;
		}
		
		/*
		int r = (pix[index] >> 16) & 0xff;
	    int g = (pix[index] >> 8) & 0xff;
	    int b = pix[index] & 0xff;
	    */
	}
	
	public float getHSV(String str){
		if(  str.equals("H") ){
			return hsv[0] ;
		}else if(str.equals("S")) {
			return hsv[1] ;
		}else{
			return hsv[2] ;
		}
	}
	
	
	public void setColor(int R, int G, int B){
		pixel = 0xff000000 | (R << 16) | (G << 8) | B;
	}
	
	public int getRaw(){
		return pixel ;
	}
	

	public boolean isSimiliar(Pixel p){
		if( Math.abs( getHSV("H") - p.getHSV("H") ) < 20 ){
			return true ;
		}
		return false ;
	}
	
	public static Pixel[][] toPixetMap(Bitmap mBitmap){
		int picw = mBitmap.getWidth() ;
		int pich = mBitmap.getHeight() ;
		Pixel[][] pmap = new Pixel[picw][pich] ;
		
		//public void setPixelMap( Pixel[][] pmap ){
	    int[] pix = new int[picw * pich];
	 	mBitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
	    	
	    for (int y = 0 ; y < pich; y++){
	 	   for (int x = 0 ; x < picw; x++)
	 	    {
	 		  pmap[x][y] = new Pixel(pix[ y * picw + x ]);
	 	     }
	    }
	    //}
		
		return pmap ;
	}
	
	public static Bitmap toBitMap(Pixel[][] pmap){
		int picw = pmap.length ;
		int pich = pmap[0].length ;
		
		//public Bitmap getBitMap(Pixel[][] pmap){
	    	//pmap -> pix -> mBitmap
	    int[] pix = new int[picw * pich];
	 	   
	    for (int y = 0; y < pich; y++){
	  	   for (int x = 0; x < picw; x++)
	  	   {
	  		   pix[ y * picw + x ] = pmap[x][y].getRaw() ;
	  	      //pmap[x][y] = new Pixel(pix[ y * picw + x ]);
	  	    }
	    }
	    	
	    Bitmap bm = Bitmap.createBitmap(picw, pich, Bitmap.Config.valueOf("RGB_565"));
	 	bm.setPixels(pix, 0, picw, 0, 0, picw, pich);
	 	    
	 	return bm ;
	    //}
	}
	
	public static void drawColor(Pixel[][] pmap , int r ,int g , int b , Point p){
       pmap[p.getOriX()][p.getOriY()].setColor(r,g,b) ;
	}
	
	
	/*
	public static void drawColor(Pixel[][] pmap , int r ,int g , int b , boolean[][] fb){
		int picw = pmap.length ;
		int pich = pmap[0].length ;
		
		for( int x=0 ; x<picw ; x++){
        	for(int y=0 ; y<pich ; y++){
        		if( fb[x][y] )
        			pmap[x][y].setColor(r,g,b) ;
        	}
       }
	}
	*/
	
	public static void drawColor(Pixel[][] pmap , int r ,int g , int b , List<Point> list){
		
		for(int i = 0; i < list.size(); i++){
			Point p = list.get(i);
			pmap[ p.getOriX() ][ p.getOriY()  ].setColor(r,g,b) ;
		}
			
	}
		
	
	
	
	
	public static void copyRegion( Pixel[][] dist ,  Pixel[][] src , int x , int y ) {
		int srcw = src.length ;
		int srch = src[0].length ;
		
		int x0 = x - (srcw/2) ;
		int y0 = y - (srch/2) ;
		if( x0<0 )
			x0 = 0 ;
		if( y0<0 )
			y0 = 0 ;
		
		
		for(int i=0 ; i<srcw ; i++){
			for(int j=0 ; j<srch ; j++){
			
				int indexX = i + x0 ;
				int indexY = j + y0 ;
				
				
				//Log.e("TAG" , "i"+Integer.toString(i) +" x0:" + Integer.toString(x0) ) ;
				//Log.e("TAG" , "j"+Integer.toString(j) +" y0:" + Integer.toString(y0) ) ;
				
				dist[indexX][indexY] = src[i][j] ;
				
			}
		}
		
		
	}
	
	public static Pixel[][] getRegion( Pixel[][] pmap , int x , int y , int w , int h ) {
		Pixel[][] region = new Pixel[w][h] ;
		
		int x0 = x - (w/2) ;
		int y0 = y - (h/2) ;
		
		if( x0<0 )
			x0 = 0 ;
		if( y0<0 )
			y0 = 0 ;
		
		for(int i=0 ; i<w ; i++){
			for(int j=0 ; j<h ; j++){
				
				int indexX = i + x0 ;
				int indexY = j + y0 ;
				
				region[i][j] = pmap[indexX][indexY] ; 
				
			}
		}
		
		return region ;
	}
	
	
}
