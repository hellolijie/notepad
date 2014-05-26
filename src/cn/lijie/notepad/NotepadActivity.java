package cn.lijie.notepad;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingSerlockActivity;

public class NotepadActivity extends SlidingSerlockActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setBehindContentView(R.layout.main_frame_menu);
        
        SlidingMenu mSlidingMenu = getSlidingMenu();
        
        mSlidingMenu.setShadowDrawable(R.drawable.drawer_shadow);//������ӰͼƬ
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width); //������ӰͼƬ�Ŀ��
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset); //SlidingMenu����ʱ��ҳ����ʾ��ʣ����
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        
        ActionBar actionBar = getSupportActionBar();  
		actionBar.show();
    }
}