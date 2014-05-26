package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

public class DrawSelect extends Draw {
	private float left,top,right,bottom,moveLeft,moveTop;
	private int state;	//Ñ¡Ôñ×´Ì¬ 0£ºÑ¡Ôñ·¶Î§	1£ºÒÆ¶¯
	private Bitmap selectedBitmap;
	
	public DrawSelect(){
		state=0;
	}
	@Override
	public void rushDraw(MotionEvent event) {
		if(state==0){
			left=event.getX();
			top=event.getY();
		}
		else{
			moveLeft=event.getX();
			moveTop=event.getY();
		}
	}

	@Override
	public void doDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		if(state==0){
			canvas.drawBitmap(copyBitmap, 0, 0, null);
			right=event.getX();
			bottom=event.getY();
			
			Style oldSyle=paint.getStyle();
			paint.setStyle(Style.STROKE);
			canvas.drawRect(left, top, right, bottom, paint);
			paint.setStyle(oldSyle);
		}
		else{
			
		}
	}

	@Override
	public void popMenu(Activity context, float offsetLeft, float offsetTop) {
		
	}
	@Override
	public void upDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		if(state==0){
			selectedBitmap=Bitmap.createBitmap(copyBitmap, (int)left, (int)top, (int)(right-left),(int)(bottom-top ));
			state=1;
		}
	}

}
