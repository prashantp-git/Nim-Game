package com.example.nim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class NimBoard extends View {
	
	Bitmap background= BitmapFactory.decodeResource(getResources(),R.drawable.background);
	Bitmap stick1=BitmapFactory.decodeResource(getResources(),R.drawable.stick_1);
	Bitmap stick2=BitmapFactory.decodeResource(getResources(),R.drawable.stick_2);
	Bitmap player1=BitmapFactory.decodeResource(getResources(),R.drawable.player_1);
	Bitmap player2=BitmapFactory.decodeResource(getResources(),R.drawable.player_2);
	Bitmap player1won=BitmapFactory.decodeResource(getResources(),R.drawable.player_1_won);
	Bitmap player2won=BitmapFactory.decodeResource(getResources(),R.drawable.player_2_won);
	int stickX=stick1.getWidth();
	int stickY=stick1.getHeight();
	int getMid;
	int getOffsetY;	
	int visitedElements[]=new int[11];
	int BlockNumVisited[]=new int[11];
	Sticks elements[]=new Sticks[11];
	int rowActive=0;
	boolean firstMove=true;
	boolean GameOver;
	int blockNum;
	int PLAYER_1=1;
	int PLAYER_2=2;
	int CurrPlayer;
	static int i;
	
	public void CreateObjects()
	{
		Sticks.BitmapX=stickX;
		Sticks.BitmapY=stickY;
		elements[0]=new Sticks(getMid-25, getOffsetY);
		elements[1]=new Sticks(getMid-75, getOffsetY+100+25);
		elements[2]=new Sticks(getMid+25, getOffsetY+100+25);
		elements[3]=new Sticks(getMid-100, getOffsetY+200+50);
		elements[4]=new Sticks(getMid-25, getOffsetY+200+50);
		elements[5]=new Sticks(getMid+50, getOffsetY+200+50);
		elements[6]=new Sticks(getMid-175, getOffsetY+300+75);
		elements[7]=new Sticks(getMid-100, getOffsetY+300+75);
		elements[8]=new Sticks(getMid-25, getOffsetY+300+75);
		elements[9]=new Sticks(getMid+50, getOffsetY+300+75);
		elements[10]=new Sticks(getMid+125, getOffsetY+300+75);
	}
	
	public NimBoard(Context context,int width, int height) {
		super(context);
		getMid=  width/2;
		getOffsetY= height - (int)(height*0.8);
		this.CreateObjects();	
		for(int i=0;i<11;i++)
		{
			visitedElements[i]=0;
			BlockNumVisited[i]=0;
		}
		this.CurrPlayer=PLAYER_1;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(background, 0, 0, null);		
		this.updateCanvas(canvas);
		super.onDraw(canvas);
		/*if(GameOver)
		{
			//invalidate()
			//threadSleep();
		}*/
	}
	
	/*public void threadSleep()
	{
		 
		if(i==0)
		{
			i++;
			Log.e("Thread","Going to sleep");
			invalidate();
		}
		else
		{
			try{
				Thread.sleep(3000);
				Log.e("Thread","Press to go back");
				GameOver=false;
			}catch(Exception e)
			{
				
			}
			MainUI mainUI=(MainUI)getContext();
			mainUI.setPanelView();
			
		}
	}*/
	
	@Override
    public boolean onTouchEvent(MotionEvent event) 
    {
	    this.render(event);
	    this.validate();
	    invalidate();
        return super.onTouchEvent(event);
        
    }
	
	public void setPanelView()
	{
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		@Override
		public void run() {
			Log.e("Thread","Back to Panel");
			MainUI mainUI=(MainUI)getContext();
			mainUI.setPanelView();
		}
		}, 1000);
	}
	
	public void updateCanvas(Canvas canvas)
	{
		if(CurrPlayer==1)
		{
			if(GameOver)
			{
				canvas.drawBitmap(player1won, 0,0, null);
				setPanelView();
				
			}
			else{
			canvas.drawBitmap(player1, 0,0, null);
			}
		}
		else if (CurrPlayer==2)
		{
			if(GameOver)
			{
				canvas.drawBitmap(player2won, 0,0, null);
				setPanelView();
			}
			else{
			canvas.drawBitmap(player2,0,0, null);}
		}
		for(int i=0;i<11;i++)
		{
			if(elements[i].getCurrState()==2)
			{
				canvas.drawBitmap(stick2, elements[i].getX1(), elements[i].getY1(), null);
			}
			else if(elements[i].getCurrState()==1)
			{
				canvas.drawBitmap(stick1, elements[i].getX1(), elements[i].getY1(), null);
			}
		}
	}
	
	public void validate()
	{
		GameOver=false;
		boolean togglePlayer=false;
		for(int i=0;i<11;i++)
		{			
			if(visitedElements[i]==1)
			{
				Log.e("Validate","Block "+i+" is 1");
				return;
			}
			else if(visitedElements[i]==0 && blockNum!=-1)
			{			
					if(BlockNumVisited[blockNum]==1)
					{
						togglePlayer=true;
						if(blockNum==0)
						{
							BlockNumVisited[0]=2;
						}
						else if(blockNum==1 || blockNum==2)
							{
								BlockNumVisited[1]=2;
								BlockNumVisited[2]=2;
							}
						else if(blockNum>=3 && blockNum<=5)
						{
							BlockNumVisited[3]=2;
							BlockNumVisited[4]=2;
							BlockNumVisited[5]=2;
						}
						else
						{
							for(int k=6;k<11;k++)
							{
								BlockNumVisited[k]=2;
							}
						}
					}
					break;
			}
			else
			{
				togglePlayer=false;
				break;
			}
		}
		if(togglePlayer)
		{		
			int toggle=(CurrPlayer==1)?2:1;
			Log.e("Validate","Player "+CurrPlayer+" -> Player "+toggle);
			CurrPlayer=(CurrPlayer==1)?2:1;
			for(int i=0;i<11;i++)
			{
				if(elements[i].getCurrState()==3)
				{
					GameOver=true;								
				}
				else
				{
					GameOver=false;	
					break;
				}
			}
			if(GameOver)
			{
				CurrPlayer=CurrPlayer==1?2:1;
				Log.e("Validate","Player "+toggle+" -> Player "+CurrPlayer);
				Log.e("Validate","Player "+CurrPlayer+ " won the game");
			}
		}		
	}
	
	public int BlockTouched(MotionEvent event)
	{
		for(int i=0;i<11;i++)
		{
			if(elements[i].box.contains((int)event.getX(), (int)event.getY()))
			{
				return i;
			}
		}
		return -1;
	}
	
	public void render(MotionEvent event)
	{
	    blockNum=this.BlockTouched(event);
		if(blockNum==0)
		{
			for(int i=1;i<11;i++)
			{
				if(visitedElements[i]==1)
				{
					Log.e("render","Blockno : "+i+" is visited so no first block");
					return;
				}		
			}			
			if(elements[blockNum].getCurrState()==1)
			{
				elements[blockNum].setCurrState(2);
				visitedElements[blockNum]=1;
			}
			else if(elements[blockNum].getCurrState()==2)
			{
				elements[blockNum].setCurrState(3);
				visitedElements[blockNum]=0;
				BlockNumVisited[blockNum]=1;
			}
			Log.e("render","Blockno : "+blockNum+" Locked : "+!firstMove);
			return;
		}
		else
		{
			if(visitedElements[0]==0)
			{
				if(blockNum==1 || blockNum==2)
				{
					if(firstMove || rowActive==1)
					{
						if(elements[blockNum].getCurrState()==1)
						{
							elements[blockNum].setCurrState(2);
							visitedElements[blockNum]=1;
							rowActive=1;
							firstMove=false;
						}
						else if(elements[blockNum].getCurrState()==2)
						{
							elements[blockNum].setCurrState(3);
							if(visitedElements[1]==1 && visitedElements[2]==1)
							{
								elements[1].setCurrState(3);
								elements[2].setCurrState(3);
								visitedElements[1]=0;
								visitedElements[2]=0;
								BlockNumVisited[1]=1;
								BlockNumVisited[2]=1;
							}
							visitedElements[blockNum]=0;
							BlockNumVisited[blockNum]=1;
							//for(int i=0;i<11;i++)
							firstMove=true;
							
						}
						Log.e("render","Blockno : "+blockNum+" Locked : "+!firstMove);
						return;
					}
				}
				else if(blockNum==3 || blockNum==4 || blockNum==5)
				{
					if(firstMove || rowActive==2)
					{
						
						if(elements[blockNum].getCurrState()==1)
						{
							elements[blockNum].setCurrState(2);
							visitedElements[blockNum]=1;
							rowActive=2;
							firstMove=false;
						}
						else if(elements[blockNum].getCurrState()==2)
						{
							int blocks[]=new int[3];
							int j=0;
							for(int i=0;i<11;i++)
							{								
								if(visitedElements[i]==1)
								{
									blocks[j++]=i;
								}
							}
							for(int i=0;i<j;i++)
							{
								elements[blocks[i]].setCurrState(3);
								visitedElements[blocks[i]]=0;
								BlockNumVisited[blocks[i]]=1;
							}
							firstMove=true;
						}		
						Log.e("render","Blockno : "+blockNum+" Locked : "+!firstMove);
						return;
					}
					
				}
				else if (blockNum!=-1)
				{
					if(firstMove || rowActive==3)
					{
						if(elements[blockNum].getCurrState()==1)
						{
							elements[blockNum].setCurrState(2);
							visitedElements[blockNum]=1;
							rowActive=3;
							firstMove=false;
						}
						else if(elements[blockNum].getCurrState()==2)
						{
							int blocks[]=new int[5];
							int j=0;
							for(int i=0;i<11;i++)
							{								
								if(visitedElements[i]==1)
								{
									blocks[j++]=i;
								}
							}
							for(int i=0;i<j;i++)
							{
								elements[blocks[i]].setCurrState(3);
								visitedElements[blocks[i]]=0;
								BlockNumVisited[blocks[i]]=1;
							}
							firstMove=true;
						}		
						Log.e("render","Blockno : "+blockNum+" Locked : "+!firstMove);
						return;
					}
				}
			}
		}
		if(blockNum==-1)
		{
			
		}			
		}	
	}

