package cn.lijie.notepad;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.lijie.notepad.activity.AudioActivity;
import cn.lijie.notepad.activity.BaseActivity;
import cn.lijie.notepad.activity.DrawActivity;
import cn.lijie.notepad.activity.TextActivity;
import cn.lijie.notepad.data.DBhelper;
import cn.lijie.notepad.data.PadInfo;
import cn.lijie.notepad.fragment.AudioListFragment;
import cn.lijie.notepad.fragment.DrawListFragment;
import cn.lijie.notepad.fragment.TextListFragment;
import cn.lijie.notepad.utils.FileUtils;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends BaseActivity {
	private ClickListener clickListener;
	
	private DrawerLayout mDrawerLayout;
//    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewPager viewPager;
    private PopupWindow mPop;
    private TextView textTab,drawTab,audioTab;
    
    
    private int curPadPosition;
    private PgFragmentAdapter pgFragmentAdapter;
    private List<Fragment> fragmentList;
    
    public ArrayList<PadInfo> padList;
    
    private View drawer;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main_layout);
		clickListener=new ClickListener();
		fragmentList=new ArrayList<Fragment>();
		fragmentList.add(new TextListFragment());
		fragmentList.add(new AudioListFragment());
		fragmentList.add(new DrawListFragment());
        initViews();
        
        
//        dbHelper.getWritableDatabase();
	}
	
	//≥ı ºªØ
	private void initViews(){
		ActionBar actionBar=getSupportActionBar();
		actionBar.setTitle("");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer=findViewById(R.id.navigation_drawer);
//		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		viewPager=(ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter((pgFragmentAdapter=new PgFragmentAdapter(getSupportFragmentManager())));
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		(textTab=(TextView) findViewById(R.id.text)).setOnClickListener(clickListener);
		(drawTab=(TextView) findViewById(R.id.audio)).setOnClickListener(clickListener);
		(audioTab=(TextView) findViewById(R.id.draw)).setOnClickListener(clickListener);
		textTab.setTextColor(Color.GREEN);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				textTab.setTextColor(Color.BLACK);
				drawTab.setTextColor(Color.BLACK);
				audioTab.setTextColor(Color.BLACK);
				switch(arg0){
				case 0:
					textTab.setTextColor(Color.GREEN);
					break;
				case 1:
					drawTab.setTextColor(Color.GREEN);
					break;
				case 2:
					audioTab.setTextColor(Color.GREEN);
					break;
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_launcher, R.string.hello_world, R.string.hello_world) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
//                getSupportActionBar().setTitle("ddd");
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("hhh");
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		
//		getPad();
	}
	
	
	@Override
	protected void onResume() {
		MApplication application=(MApplication) getApplication();
		int rushData=application.rushData;
		if(rushData!=-1){
			viewPager.setCurrentItem(rushData, false);
		}
		
		super.onResume();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(drawer);
            getSupportMenuInflater().inflate(R.menu.main_menu, menu);
//            menu.findItem(R.id.newNote).setActionView(new TextView(getBaseContext()));
//            menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (item.getItemId()==android.R.id.home) {
        	if(mDrawerLayout.isDrawerOpen(drawer))
        		mDrawerLayout.closeDrawers();
        	else
        		mDrawerLayout.openDrawer(drawer);
        	return true;
        }
        Intent intent;
        switch(item.getItemId()){
        case R.id.newTextNote:
        	intent=new Intent(getApplicationContext(), TextActivity.class);
        	startActivity(intent);
        	break;
        case R.id.newAudioNote:
        	intent=new Intent(getApplicationContext(), AudioActivity.class);
			startActivity(intent);
        	break;
        case R.id.newDrawNote:
        	intent=new Intent(getApplicationContext(), DrawActivity.class);
			startActivity(intent);
        	break;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
	
	
	
	class PgFragmentAdapter extends FragmentPagerAdapter{

		public PgFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList.size();
		}
		
	}
	
	class ClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent;
			switch(v.getId()){
			case R.id.text:
				viewPager.setCurrentItem(0, true);
				break;
			case R.id.draw:
				viewPager.setCurrentItem(2, true);
				break;
			case R.id.audio:
				viewPager.setCurrentItem(1, true);
				break;
			}
		}
	}

	public void notifyMenuChoised(){
		int curItem=viewPager.getCurrentItem();
		fragmentList.clear();
		fragmentList.add(new TextListFragment());
		fragmentList.add(new AudioListFragment());
		fragmentList.add(new DrawListFragment());
		
		mDrawerLayout.closeDrawers();
		viewPager.setAdapter((pgFragmentAdapter=new PgFragmentAdapter(getSupportFragmentManager())));
		viewPager.setCurrentItem(curItem,true);
	}
}
