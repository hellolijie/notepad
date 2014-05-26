package cn.lijie.notepad;

import android.app.Application;
import android.content.res.Configuration;

public class MApplication extends  Application{

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
