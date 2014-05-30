package cn.lijie.notepad.draw;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback{
	private Activity context;
	private SurfaceHolder holder;
	
	
	public MySurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=(Activity) context;
		getHolder().addCallback(this);
		setOnTouchListener(new MyTouchLisener());
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.holder=holder;
		DrawFactory.getInstance(context).initSurface(holder);
		DrawFactory.getInstance(context).setCurDraw(new DrawScrew());
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	class MyTouchLisener implements View.OnTouchListener{
		float x,y;	//初始位置
		private boolean clickFlag=false;	
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = (event.getAction()&MotionEvent.ACTION_MASK)%5;//统一单点和多点 
			int pointCount=event.getPointerCount();
			switch(action){
			case MotionEvent.ACTION_DOWN:
				x=event.getX();
				y=event.getY();
				DrawFactory.getInstance(context).rushDraw(event);
				break;
			case MotionEvent.ACTION_MOVE:
				if(Math.abs(x-event.getX(0))>10||Math.abs(y-event.getY(0))>10){
					clickFlag=true;
					DrawFactory.getInstance(context).doDraw(event, holder);
				}
				else{
					clickFlag=false;
				}
				break;
			case MotionEvent.ACTION_UP:
				DrawFactory.getInstance(context).upDraw(event);
				if(clickFlag==false){
					DrawFactory.getInstance(context).showPopMenu(context,event.getX(),event.getY());
				}
				break;
			}
			return true;
		}
		
	}
	
	

}
