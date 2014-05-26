package cn.lijie.notepad.utils;

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
			screenSize.screenWidth = wm.getDefaultDisplay().getWidth();//фад╩©М╤х
			screenSize.screenHeight = wm.getDefaultDisplay().getHeight();	//фад╩╦ъ╤х
		}
		return screenSize;
	}
}
