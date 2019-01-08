package com.example.nim;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Sticks {
	private int x1;
	private int y1;	
	static int BitmapX;
	static int BitmapY;
	Boolean touched;	
	Rect box;
	int CurrState;   //black=1, red=2, blank=3
	
	public Sticks(int x1,int y1)
	{
		this.x1=x1;
		this.y1=y1;		
		this.box=new Rect(x1,y1,x1+BitmapX,y1+BitmapY);
		this.touched=false;
		this.CurrState=1;  
		//Log.e("sticks","X1="+x1+" Y1="+y1);
	}
	
	public int getX1()
	{
		return x1;
	}	
	
	public int getY1()
	{
		return y1;
	}
	
	public void setTouched()
	{
	this.touched=true;	
	}
	
	public Boolean getTouched()
	{
		return touched;
	}
	
	public void setCurrState(int state)
	{
	this.CurrState=state;	
	}
	
	public int getCurrState()
	{
		return CurrState;
	}
	
}
