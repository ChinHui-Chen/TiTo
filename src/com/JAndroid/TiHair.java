package com.JAndroid;

import java.util.Arrays;
import java.util.List;

import com.JAndroid.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.graphics.Color ;
 
public class TiHair extends Activity {
	public static int picw = 320; //default
    public static int pich = 240; //default
    
    // Constructor
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Load bitmap
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources() , R.drawable.pic3);
        picw = mBitmap.getWidth() ;
        pich = mBitmap.getHeight() ;

        // Load PixelMap : pmap
        Pixel[][] pmap = Pixel.toPixetMap(mBitmap) ;
        
        // Set Center Point //set by hand
        Axis.centerx =  (picw / 2) + 3 ;
        Axis.centery =  (pich / 2) - 13 ;
        
        Pixel.drawColor( pmap , 255,0,0 , new Point(0 , 0) ) ;
        
        // Get face edge
        FaceEdge faceEdge = getFaceEdge() ; 
        Pixel.drawColor( pmap , 255,0,0 , faceEdge.toList() ) ; 
        
        // Get Hair edge
        FaceEdge hairEdge = getEdge(faceEdge, 60) ;
        Pixel.drawColor( pmap , 255,100,100 , hairEdge.toList() ) ; 
        
        // Get Head edge
        FaceEdge headEdge = getEdge(faceEdge, 30) ;
        Pixel.drawColor( pmap , 255,100,0 , headEdge.toList() ) ; 
        
        
        // Main Part
        // Find slice
        Slice slice = new Slice( new Point(-45,115) , faceEdge , headEdge ) ;
        for(int i=1 ; i<30 ; i++){
        	slice.addPoint( new Point(-45+i,115+(i*2)) ) ;
        }
        
        slice.setRect(3) ;
        Pixel.drawColor( pmap , 0,255,255 , slice.getUpperList() ) ;
        Pixel.drawColor( pmap , 0,0,255 , slice.getUpperBoundaryList() ) ;
        Pixel.drawColor( pmap , 0,125,0 , slice.getLowerList() ) ;
        Pixel.drawColor( pmap , 100,100,100 , slice.getCollectList() ) ;
        
        
        
        
        
        // Find intersection
        //Tangent tangent = getTangent(faceEdge , 15) ;
        // Drawing
        //Pixel.drawColor( pmap , 0,255,0 , tangent.toList(-15 , 60) ) ; //need to modify
        
        
        
        
        
        //Pixel[][] r = Pixel.getRegion( pmap , midx , midy , 100 , 200 ) ;
        //Pixel.copyRegion( pmap , r , 100 , 200 ) ;
        
        //int[][] original = new int[picw][pich] ;
        //original[centerx][centery] = 1;
        //int[][] region = detectRegion( pmap , original ) ;
        //Pixel.drawColor( pmap , 60,50,90 , region ) ;
        
        
        
        
        
        
        // Convert PixelMap to Bitmap
        mBitmap = Pixel.toBitMap(pmap) ;
        // Set view
        ImageView mIV = (ImageView)findViewById(R.id.picview) ;
        mIV.setImageBitmap(mBitmap) ;
        mIV.invalidate() ;
    }
    
    private Tangent getTangent(FaceEdge faceEdge , int intersXNew){
    	
    	// find intersect point
    	int intersYNew = faceEdge.getY(intersXNew) ;
    	
    	double slope = -1*((double)intersXNew/intersYNew) ;
    	
    	return new Tangent( intersXNew , intersYNew , slope ) ;
    }
    
    private FaceEdge getEdge(FaceEdge fe , int offset){
    	FaceEdge out = new FaceEdge();
    	
    	List<Point> list = fe.toList() ;
    	for(int i=0 ; i<list.size() ; i++){
    		out.insert(list.get(i).getNewX() ,list.get(i).getNewY() + offset ) ;
    		//Point p = new Point( list.get(i).getOriX() , list.get(i).getOriY() + offset ) ;
    	}
    	
    	return out ;
    }
    
    private FaceEdge getFaceEdge(){
    	
    	FaceEdge fe = new FaceEdge() ;
    	int r = 117 ; //distance
        
        Log.e("TAG" , "R="+Integer.toString(r));
        
        
    	for(int x=(-1*r) ; x<r ; x++){
        	
        	// find y
    		int y = (int) Math.round(  Math.sqrt(  (r*r) -   (x*x/0.7)  )  ) ;
        	
        	fe.insert( new Point(x , y) ) ; 
        }
    	return fe ;
    	
    }
    
    private int[][] detectRegion( Pixel[][] pmap , int[][] original ) {
    	int picw = pmap.length ;
		int pich = pmap[0].length ;
		
    	int[][] output = new int[picw][pich] ;
    	
    	// copy
    	for(int i=0 ; i<picw ; i++)
    		for(int j=0 ; j<pich ; j++)
    			output[i][j] = original[i][j] ;
    	
    	
    	// detect
    	for( int i=0 ; i<picw ; i++ ){
    		for(int j=0 ; j<pich ; j++){
    			if( original[i][j] == 1 ){
    				detectRegionRun( pmap , i , j , output ) ;
    			}
    		}
    	}
    	
    	return output ;
	}
    
    private void detectRegionRun( Pixel[][] pmap , int x , int y , int[][] output ){
    	int picw = pmap.length ;
		int pich = pmap[0].length ;

		
		int flag = 0 ;
		// i-1,j
    	if( (x-1)>=0 && (output[x-1][y] == 0)  &&  pmap[x][y].isSimiliar( pmap[x-1][y] ) ){
    		output[x-1][y] = 1;
    		flag++ ;
    		//detectRegionRun( pmap , x-1,y , output ) ;
    	}
    	// i+1,j
    	if( (x+1)<picw && (output[x+1][y] == 0) && pmap[x][y].isSimiliar( pmap[x+1][y] ) ){
    		output[x+1][y] = 1 ;
    		flag++ ;
    		//detectRegionRun( pmap , x+1,y , output ) ;
    	}
    	// i,j-1
    	if( (y-1)>=0 && (output[x][y-1] == 0) && pmap[x][y].isSimiliar( pmap[x][y-1] ) ){
    		output[x][y-1] = 1 ;
    		flag++ ;
    		//detectRegionRun( pmap , x,y-1 , output ) ;
    	}
    	// i,j+1
    	if( (y+1)<pich && (output[x][y+1] == 0) && pmap[x][y].isSimiliar( pmap[x][y+1] ) ){
    		output[x][y+1] = 1 ;
    		flag++ ;
    		//detectRegionRun( pmap , x,y+1 , output ) ;
    	}
    	
    	if(flag == 4)
    		return ;
    	
    	
    	if( (x-1)>=0 && (output[x-1][y] == 1) &&  pmap[x][y].isSimiliar( pmap[x-1][y] ) ){
    		detectRegionRun( pmap , x-1,y , output ) ;
    	}
    	
    	
    	
    	
    	
    	return ;
    }
    
    
    
    /*
    // When Click
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	   if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
    	      TintThePicture(20);
    	      return (true);
    	    }
    	   return super.onKeyDown(keyCode, event);
    }
    */
    
        
    /*
    private void TintThePicture(int deg) {
    	   int[] pix = new int[picw * pich];
    	   mBitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);

    	   int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
    	   double angle = (3.14159d * (double)deg) / 180.0d;
    	   int S = (int)(256.0d * Math.sin(angle));
    	   int C = (int)(256.0d * Math.cos(angle));

    	   for (int y = 0; y < pich; y++)
    		   for (int x = 0; x < picw; x++)
    	       {
    	      int index = y * picw + x;
    	      int r = (pix[index] >> 16) & 0xff;
    	      int g = (pix[index] >> 8) & 0xff;
    	      int b = pix[index] & 0xff;
    	      RY = ( 70 * r - 59 * g - 11 * b) / 100;
    	      GY = (-30 * r + 41 * g - 11 * b) / 100;
    	      BY = (-30 * r - 59 * g + 89 * b) / 100;
    	      Y  = ( 30 * r + 59 * g + 11 * b) / 100;
    	      RYY = (S * BY + C * RY) / 256;
    	      BYY = (C * BY - S * RY) / 256;
    	      GYY = (-51 * RYY - 19 * BYY) / 100;
    	      R = Y + RYY;
    	      R = (R < 0) ? 0 : ((R > 255) ? 255 : R);
    	      G = Y + GYY;
    	      G = (G < 0) ? 0 : ((G > 255) ? 255 : G);
    	      B = Y + BYY;
    	      B = (B < 0) ? 0 : ((B > 255) ? 255 : B);
    	      pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
    	      }

    	   
    	   Bitmap bm = Bitmap.createBitmap(picw, pich, Bitmap.Config.valueOf("RGB_565"));
    	   bm.setPixels(pix, 0, picw, 0, 0, picw, pich);

    	   // Put the updated bitmap into the main view
    	   mIV.setImageBitmap(bm);
    	   mIV.invalidate();

    	   mBitmap = bm;
    	   pix = null;
    }
    */
}