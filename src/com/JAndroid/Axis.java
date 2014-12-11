package com.JAndroid;

public class Axis {

	public static int centerx ;
	public static int centery ;
	
	public static int toNewAxisX(int x){
    	return (x-centerx) ;
    }
    
    public static int toNewAxisY(int y){
    	return -1*(y-centery) ;
    }
    
    public static int toOldAxisX(int x){
    	return (x+centerx) ;
    }
    public static int toOldAxisY(int y){
    	return (centery - y) ;
    }
   
}
