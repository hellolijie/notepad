package cn.lijie.notepad;

import android.app.Application;
import android.content.res.Configuration;
import cn.lijie.notepad.data.PadInfo;

public class MApplication extends  Application{
	//�ļ�������
	public static String FOLD_ROOTFOLDNAME="fnotepad";
	public static String FOLD_AUDIOFOLDNAME="audio";
	public static String FOLD_TEXTFOLDNAME="text";
	public static String FOLD_DRAWFOLDNAME="draw";
	
	//��ǩ����
	public static int TYPE_TEXT=0;
	public static int TYPE_DRAW=1;
	public static int TYPE_AUDIO=2;
//	public static String CONFIGNAME="config.xml";

	//��ǰ��ǩ��
	public PadInfo curPadInfo;
	
	public int rushData=-1;	//-1����ˢ�� 
	
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
