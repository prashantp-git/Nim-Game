package com.example.nim;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2) 
public class MainUI extends Activity{
	Display display;
	Point size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
    	display.getSize(size);
    	setPanelView();
    }
    
    public void setPanelView()
    {
    	
    	setContentView(new PanelView(this,size.x,size.y));
    }

    public void setGameBoard()
    {
    	setContentView(new NimBoard(this,size.x,size.y));
    }
    @Override
	public void onBackPressed() 
	{

    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                this);
        alertDialog.setTitle("Do you want to exit?");
        alertDialog.setMessage("Are you sure you want to exit the game?");
        alertDialog.setIcon(R.drawable.icon);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
	}
}
