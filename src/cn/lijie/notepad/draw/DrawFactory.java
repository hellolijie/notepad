package cn.lijie.notepad.draw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class DrawFactory {
	private Activity context;
	private static DrawFactory instance;
	
	private Draw curDraw;				//��ǰ���ƹ���
	private Bitmap bufferedBitmap;		//���Ƴ�����ͼƬ
	private Canvas bufferedCanvas;		//˫������
	private Bitmap copyBitmap;		//��ǰ��Ŀ����ǰ��ͼ��
	private SurfaceHolder holder;

	private DrawFactory(Activity context){
		this.context=context;
		bufferedBitmap=Bitmap.createBitmap(context.getWindowManager().getDefaultDisplay().getWidth(),context.getWindowManager().getDefaultDisplay().getHeight() , Config.RGB_565);
		bufferedCanvas=new Canvas(bufferedBitmap);
		bufferedCanvas.drawColor(Color.WHITE);
	}
	
	public void initSurface(SurfaceHolder holder){
		this.holder=holder;
		Canvas canvas=holder.lockCanvas();
		canvas.drawBitmap(bufferedBitmap, 0, 0, null);
		holder.unlockCanvasAndPost(canvas);
	}
	
	public static DrawFactory getInstance(Activity context){
		if(instance==null)
			instance=new DrawFactory(context);
		return instance;
	}
	
	public void setCurDraw(Draw draw){
		curDraw=draw;
	}
	
	//�����˵�
	public void showPopMenu(Activity context,float offsetLeft,float offsetTop){
		curDraw.popMenu(context,offsetLeft,offsetTop);
	}
	
	//����
	public void doDraw(MotionEvent event,SurfaceHolder holder){
		curDraw.doDraw(event,bufferedCanvas,copyBitmap);
		Canvas canvas=holder.lockCanvas();
		canvas.drawBitmap(bufferedBitmap, 0, 0, null);
		holder.unlockCanvasAndPost(canvas);
	}
	//��ʼ
	public void rushDraw(MotionEvent event){
		copyBitmap=bufferedBitmap.copy(Config.RGB_565, true);
		curDraw.rushDraw(event);
	}
	
	//����
	public void upDraw(MotionEvent event){
		curDraw.upDraw(event, bufferedCanvas, copyBitmap);
	}
	
	//������ɫ
	public void setColor(int color){
		curDraw.setColor(color);
	}
	
	public Activity getContext() {
		return context;
	}

	public Canvas getBufferedCanvas() {
		return bufferedCanvas;
	}

	public SurfaceHolder getHolder() {
		return holder;
	}

	public Bitmap getBufferedBitmap() {
		return bufferedBitmap;
	}
	
}
