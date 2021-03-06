package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;


public abstract class Draw {
	protected static Paint paint;
	protected static int lineWidth,textSize;//线条原始宽度,字体大小
	static{
		paint=new Paint();
		lineWidth=2;
		textSize=30;
		paint.setStrokeWidth(lineWidth);
		paint.setTextSize(textSize);
	}
	
	public void setColor(int color){
		paint.setColor(color);
	}

	protected void measurePress(MotionEvent event){
		float presure=event.getPressure();
		Log.i("tag", presure+"");
		if(presure-1.4>0){
			presure=(float) ((presure-0.4)*100);
		}
//		presure=0;
		paint.setStrokeWidth(lineWidth+presure);
	}
	
	abstract public void rushDraw(MotionEvent event);
	abstract public void doDraw(MotionEvent event,Canvas canvas,Bitmap copyBitmap);
	abstract public void upDraw(MotionEvent event,Canvas canvas,Bitmap copyBitmap);
	abstract public void popMenu(Activity context,float offsetLeft,float offsetTop);
}
