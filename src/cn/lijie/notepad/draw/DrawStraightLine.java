package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class DrawStraightLine extends Draw {
	private float startX,startY,endX,endY;	//线条开始点和终点
	
	public DrawStraightLine(){
	}
	
	@Override
	public void rushDraw(MotionEvent event) {
		startX=event.getX();
		startY=event.getY();
		endX=event.getX();
		endY=event.getY();
	}

	@Override
	public void doDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		measurePress(event);
		canvas.drawBitmap(copyBitmap, 0, 0, null);
		endX=event.getX();
		endY=event.getY();
		canvas.drawLine(startX, startY, endX, endY, paint);
	}

	@Override
	public void popMenu(Activity context, float offsetLeft, float offsetTop) {
		
	}

	@Override
	public void upDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		// TODO Auto-generated method stub
		
	}
	
}
