package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class DrawScrew extends Draw {
	private float startX,startY;	//线条开始点和终点
	
	public DrawScrew(){
	}
	
	
	@Override
	public void rushDraw(MotionEvent event) {
		startX=event.getX();
		startY=event.getY();
	}

	@Override
	public void doDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		measurePress(event);
		canvas.drawLine(startX, startY, event.getX(), event.getY(), paint);
		startX=event.getX();
		startY=event.getY();
	}

	@Override
	public void popMenu(Activity context, float offsetLeft, float offsetTop) {
		
	}


	@Override
	public void upDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		// TODO Auto-generated method stub
		
	}

}
