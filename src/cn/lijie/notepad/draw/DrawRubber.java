package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class DrawRubber extends Draw {
	private float originX,originY,radius;	//Ô²ÐÄ¡¢°ë¾¶
	
	public DrawRubber(){
	}

	@Override
	public void rushDraw(MotionEvent event) {
		originX=event.getX();
		originY=event.getY();
	}

	@Override
	public void doDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		int color=paint.getColor();
		paint.setColor(Color.WHITE);
		originX=event.getX();
		originY=event.getY();
		measureRadiusByPress(event);
		canvas.drawCircle(originX, originY, radius, paint);
		paint.setColor(color);
	}

	@Override
	public void popMenu(Activity context, float offsetLeft, float offsetTop) {
		
	}

	private void measureRadiusByPress(MotionEvent event){
		float presure=event.getPressure();
		if(presure-0.2>0){
			presure=(float) ((presure-0.2)*500);
		}
		presure=30;
		radius=10+presure;
	}

	@Override
	public void upDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		// TODO Auto-generated method stub
		
	}
}
