package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import cn.lijie.notepad.R;

public class DrawCircle extends Draw{
	private float originX,originY,radius;	//Ô²ÐÄ¡¢°ë¾¶
	private boolean isSingle;
	
	private PopupWindow popWindow;			//µ¯³ö²Ëµ¥
	
	public DrawCircle(){
		isSingle=true;
	}
	
	@Override
	public void rushDraw(MotionEvent event) {
		if(event.getPointerCount()>1){
			originX=event.getX(0)-(event.getX(0)-event.getX(1))/2;
			originY=event.getY(0)-(event.getY(0)-event.getY(1))/2;
			radius=(float) Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)))/2;
			isSingle=false;
		}
		else{
			isSingle=true;
			originX=event.getX();
			originY=event.getY();
		}
	}

	@Override
	public void doDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		canvas.drawBitmap(copyBitmap, 0, 0, null);
		if(event.getPointerCount()>1){
			originX=event.getX(0)-(event.getX(0)-event.getX(1))/2;
			originY=event.getY(0)-(event.getY(0)-event.getY(1))/2;
			radius=(float) Math.sqrt((event.getX(0)-event.getX(1))*(event.getX(0)-event.getX(1))+(event.getY(0)-event.getY(1))*(event.getY(0)-event.getY(1)))/2;
		}
		else if(isSingle){
				originX=event.getX();
				originY=event.getY();
				measureRadiusByPress(event);
		}
		canvas.drawCircle(originX, originY, radius, paint);
	}

	@Override
	public void popMenu(Activity context, float offsetLeft, float offsetTop) {
//		if(popWindow==null){
//			View popView=LayoutInflater.from(context).inflate(R.layout.drawline_pop_layout, null);
//			MenuClickListener clickListener=new MenuClickListener();
//			popView.findViewById(R.id.circle).setOnClickListener(clickListener);
//			popView.findViewById(R.id.screwl).setOnClickListener(clickListener);
//			popView.findViewById(R.id.straightLine).setOnClickListener(clickListener);
//			
//			popWindow=new PopupWindow(popView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		}
//		if(!popWindow.isShowing()){
//			popWindow.showAtLocation(context.findViewById(R.id.mySurface), Gravity.TOP|Gravity.LEFT, (int)offsetLeft, (int)offsetTop);
//		}
//		else{
//			popWindow.dismiss();
//		}
	}
	
//	class MenuClickListener implements View.OnClickListener{
//		@Override
//		public void onClick(View v) {
//			switch(v.getId()){
//			case R.id.circle:			//»­Ô²
//				break;
//			case R.id.screwl:			//Í¿Ñ»
//				break;
//			case R.id.straightLine:		//Ö±Ïß
//				break;
//			}
//		}
//	}
	
	private void measureRadiusByPress(MotionEvent event){
		float presure=event.getPressure();
		if(presure-0.2>0){
			presure=(float) ((presure-0.2)*700);
		}
		presure=(float) (Math.random()*10);
		radius=10+presure;
	}

	@Override
	public void upDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		// TODO Auto-generated method stub
		
	}
}
