package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class DrawRectangle extends Draw {
	private float left,top,right,bottom;
	private boolean isSingle;
	
	public DrawRectangle(){
	}
	@Override
	public void rushDraw(MotionEvent event) {
		if(event.getPointerCount()>1){
			isSingle=false;
			left=event.getX(0)<event.getX(1)?event.getX(0):event.getX(1);
			top=event.getY(0)<event.getY(1)?event.getY(0):event.getY(1);
			right=event.getX(0)>event.getX(1)?event.getX(0):event.getX(1);
			bottom=event.getY(0)>event.getY(1)?event.getY(0):event.getY(1);
		}
		else{
			isSingle=true;
			left=event.getX();
			top=event.getY();
		}
	}

	@Override
	public void doDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		canvas.drawBitmap(copyBitmap, 0, 0, null);
		
		if(event.getPointerCount()>1){
			left=event.getX(0)<event.getX(1)?event.getX(0):event.getX(1);
			top=event.getY(0)<event.getY(1)?event.getY(0):event.getY(1);
			right=event.getX(0)>event.getX(1)?event.getX(0):event.getX(1);
			bottom=event.getY(0)>event.getY(1)?event.getY(0):event.getY(1);
		}
		else if(isSingle){
			right=event.getX();
			bottom=event.getY();
		}
		canvas.drawRect(left, top, right, bottom, paint);
	}

	@Override
	public void popMenu(Activity context, float offsetLeft, float offsetTop) {
		
	}
	@Override
	public void upDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		// TODO Auto-generated method stub
		
	}

}
