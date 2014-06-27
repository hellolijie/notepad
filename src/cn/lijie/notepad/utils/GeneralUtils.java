package cn.lijie.notepad.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.WindowManager;

public class GeneralUtils {
	public static class ScreenSize{
		public int screenWidth;
		public int screenHeight;
	}
	
	private static ScreenSize screenSize;
	public static ScreenSize getScreenSize(Context context){
		if(screenSize==null){
			screenSize=new ScreenSize();
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			screenSize.screenWidth = wm.getDefaultDisplay().getWidth();//ÆÁÄ»¿í¶È
			screenSize.screenHeight = wm.getDefaultDisplay().getHeight();	//ÆÁÄ»¸ß¶È
		}
		return screenSize;
	}
	
	public static String formatTime(String formatString,long timeMillis){
		return new SimpleDateFormat(formatString).format(new Date(timeMillis));
	}
	

}
