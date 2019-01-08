package com.example.nim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PanelView extends View {
	Bitmap button_off=BitmapFactory.decodeResource(getResources(),R.drawable.button_off);
	Bitmap button_on=BitmapFactory.decodeResource(getResources(),R.drawable.button_on);
	Bitmap background= BitmapFactory.decodeResource(getResources(),R.drawable.main_ui);
	int offsetX;
	int offsetY;
	Rect button;
	int currButton;
	
	
	public PanelView(Context context,int width,int height) {
		super(context);
		offsetX=width/2-64;
		offsetY=(int)(height*0.6);
		button=new Rect(offsetX, offsetY, offsetX+128, offsetY+128);
		currButton=1;
		Log.e("Panel","View created");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(background, 0, 0, null);
		if(currButton==1)
		{
			canvas.drawBitmap(button_off, offsetX, offsetY, null);
			}
		else if(currButton==2)
		{
			
			canvas.drawBitmap(button_on, offsetX, offsetY, null);
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				currButton=3;				
				invalidate();
			}
			}, 150);
		}
		else
		{
			canvas.drawBitmap(button_off, offsetX, offsetY, null);
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				MainUI mainUI=(MainUI)getContext();
				mainUI.setGameBoard();
			}
			}, 200);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(button.contains((int)event.getX(),(int)event.getY()))
		{
			currButton=2;	
		}
		invalidate();
		return super.onTouchEvent(event);
	}
	
	

}
