package cn.lijie.notepad;

import android.app.Application;
import android.content.res.Configuration;
import cn.lijie.notepad.data.PadInfo;

public class MApplication extends  Application{
	//文件夹名称
	public static String FOLD_ROOTFOLDNAME="fnotepad";
	public static String FOLD_AUDIOFOLDNAME="audio";
	public static String FOLD_TEXTFOLDNAME="text";
	public static String FOLD_DRAWFOLDNAME="draw";
	
	//便签类型
	public static int TYPE_TEXT=0;
	public static int TYPE_DRAW=1;
	public static int TYPE_AUDIO=2;
//	public static String CONFIGNAME="config.xml";

	//当前便签薄
	public PadInfo curPadInfo;
	
	public int rushData=-1;	//-1：不刷新 
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		
		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {
		
		super.onTrimMemory(level);
	}

	
	//封装dialog的样式以及现实方式
	public void alertDialogControl(){
		
	}
	
	//数据的本地化存储
	public void commitData(){
		
	}
}
