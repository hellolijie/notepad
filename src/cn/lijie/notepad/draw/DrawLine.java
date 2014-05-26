package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import cn.lijie.notepad.R;

public class DrawLine extends Draw {
	private static final int DRAWMODE_SCREWL=0;
	private static final int DRAWMODE_CIRCLE=1;
	private static final int DRAWMODE_STRAIGHTLINE=2;
	
	private float startX,startY,endX,endY;	//线条开始点和终点
	private float originX,originY,radius;	//圆心、半径
	
	private Paint paint;
	private PopupWindow popWindow;			//弹出菜单
	private int drawMode;		//绘制模式
	
	
	public DrawLine(){
		paint=new Paint();
		paint.setColor(Color.BLACK);
		drawMode=DRAWMODE_SCREWL;
	}
	@Override
	public void doDraw(MotionEvent event,Canvas canvas,Bitmap copyBitmap) {
		float presure=event.getPressure();
		if(presure-0.2>0){
			presure=(float) ((presure-0.2)*100);
		}
		paint.setStrokeWidth(2+presure);
		switch(drawMode){
		case DRAWMODE_SCREWL:
			drawScrewl(event,canvas);
			break;
		case DRAWMODE_CIRCLE:
			drawCircle(event,canvas,copyBitmap);
			break;
		case DRAWMODE_STRAIGHTLINE:
			drawStraightLine(event,canvas,copyBitmap);
			break;
		}
	}
	
	//涂鸦
	private void drawScrewl(MotionEvent event,Canvas canvas){
		
		canvas.drawLine(startX, startY, event.getX(), event.getY(), paint);
		startX=event.getX();
		startY=event.getY();
	}
	//画圆
	private void drawCircle(MotionEvent event,Canvas canvas,Bitmap copyBitmap){
		if(event.getPointerCount()>1){
			canvas.drawBitmap(copyBitmap, 0, 0, null);
			originX=event.getX(0)-(event.getX(0)-event.getX(1))/2;
			originY=event.getY(0)-(event.getY(0)-event.getY(1))/2;
			radius=(float) Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)))/2;
			
			canvas.drawCircle(originX, originY, radius, paint);
		}
	}
	
	//直线
	private void drawStraightLine(MotionEvent event,Canvas canvas,Bitmap copyBitmap){
		canvas.drawBitmap(copyBitmap, 0, 0, null);
		endX=event.getX();
		endY=event.getY();
		canvas.drawLine(startX, startY, endX, endY, paint);
		
	}
	
	//开始画
	public void rushDraw(MotionEvent event){
		switch(drawMode){
		case DRAWMODE_SCREWL:
			startX=event.getX();
			startY=event.getY();
			break;
		case DRAWMODE_CIRCLE:
			if(event.getPointerCount()>1){
				originX=event.getX(0)-(event.getX(0)-event.getX(1))/2;
				originY=event.getY(0)-(event.getY(0)-event.getY(1))/2;
				radius=(float) Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)))/2;
			}
		case DRAWMODE_STRAIGHTLINE:
			startX=event.getX();
			startY=event.getY();
			endX=event.getX();
			endY=event.getY();
			break;
		}
		
	}
	@Override
	public void popMenu(Activity context,float offsetLeft,float offsetTop) {
		if(popWindow==null){
			View popView=LayoutInflater.from(context).inflate(R.layout.drawline_pop_layout, null);
			MenuClickListener clickListener=new MenuClickListener();
			popView.findViewById(R.id.circle).setOnClickListener(clickListener);
			popView.findViewById(R.id.screwl).setOnClickListener(clickListener);
			popView.findViewById(R.id.straightLine).setOnClickListener(clickListener);
			
			popWindow=new PopupWindow(popView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		}
		if(!popWindow.isShowing()){
			popWindow.showAtLocation(context.findViewById(R.id.mySurface), Gravity.TOP|Gravity.LEFT, (int)offsetLeft, (int)offsetTop);
		}
		else{
			popWindow.dismiss();
		}
	}
	
	class MenuClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.circle:			//画圆
				drawMode=DRAWMODE_CIRCLE;
				break;
			case R.id.screwl:			//涂鸦
				drawMode=DRAWMODE_SCREWL;
				break;
			case R.id.straightLine:		//直线
				drawMode=DRAWMODE_STRAIGHTLINE;
				break;
			}
		}
	}

	@Override
	public void upDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		// TODO Auto-generated method stub
		
	}

}
