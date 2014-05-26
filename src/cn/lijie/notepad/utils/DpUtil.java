package cn.lijie.notepad.utils;

import android.content.Context;

public class DpUtil {
	//dpתpx
	public int Dp2Px(Context context,float dp) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dp * scale + 0.5f); 
	} 
	 
	//pxתdp
	public int Px2Dp(Context context,float px) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (px / scale + 0.5f); 
	}
	
}
