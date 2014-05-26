package cn.lijie.notepad.draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;
import cn.lijie.notepad.R;

public class DrawText extends Draw {

	private PopupWindow popWindow;			//µ¯³ö²Ëµ¥
	private View popView;
	private ClickListener clickListener;
	
	private float left,top;
	
	private boolean drawFlag;
	private String drawText;
	public DrawText(){
		drawFlag=false;
	}
	
	@Override
	public void rushDraw(MotionEvent event) {
		if(popWindow==null||!popWindow.isShowing()){
			showEditLayout(event.getX(),event.getY());
			left=event.getX();
			top=event.getY();
		}
	}

	@Override
	public void doDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		if(!drawFlag){
			popWindow.dismiss();
			showEditLayout(event.getX(),event.getY());
			left=event.getX();
			top=event.getY();
		}
		else{
			drawFlag=false;
			canvas.drawText(drawText, left, top, paint);
		}
	}

	@Override
	public void popMenu(Activity context, float offsetLeft, float offsetTop) {
		Toast.makeText(context, "dddd", Toast.LENGTH_SHORT).show();
	}

	private void showEditLayout(float offsetLeft,float offsetTop){
		if(popWindow==null){
			popView=LayoutInflater.from(DrawFactory.getInstance(null).getContext()).inflate(R.layout.draw_text_layout, null);
			popWindow=new PopupWindow(popView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			popWindow.setFocusable(true);
			clickListener=new ClickListener();
			popView.findViewById(R.id.cancel).setOnClickListener(clickListener);
			popView.findViewById(R.id.confirm).setOnClickListener(clickListener);
		}
		popWindow.showAtLocation(DrawFactory.getInstance(null).getContext().findViewById(R.id.mySurface), Gravity.TOP|Gravity.LEFT, (int)offsetLeft, (int)(offsetTop)+popView.getHeight());
	}
	
	class ClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.confirm:
				drawFlag=true;
				drawText=((EditText)popView.findViewById(R.id.textCountent)).getText().toString();
				DrawFactory.getInstance(null).doDraw(null, DrawFactory.getInstance(null).getHolder());
				popWindow.dismiss();
				break;
			case R.id.cancel:
				popWindow.dismiss();
				break;
			}
		}
	}

	@Override
	public void upDraw(MotionEvent event, Canvas canvas, Bitmap copyBitmap) {
		// TODO Auto-generated method stub
		
	}
}
