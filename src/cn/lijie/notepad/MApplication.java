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

	
	//��װdialog����ʽ�Լ���ʵ��ʽ
	public void alertDialogControl(){
		
	}
	
	//���ݵı��ػ��洢
	public void commitData(){
		
	}
}
