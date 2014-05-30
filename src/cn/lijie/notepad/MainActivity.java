package cn.lijie.notepad;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;
import cn.lijie.notepad.activity.AudioActivity;
import cn.lijie.notepad.activity.BaseActivity;
import cn.lijie.notepad.activity.DrawActivity;
import cn.lijie.notepad.activity.TextActivity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends BaseActivity implements TabListener{
	private ClickListener clickListener;
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main_layout);
		clickListener=new ClickListener();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_launcher, R.string.hello_world, R.string.hello_world) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("ddd");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("hhh");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.collections);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.collections).setText("ni hao").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("ni hao").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("hehe").setTabListener(this));
        initViews();
	}

	private void initViews(){
		findViewById(R.id.text).setOnClickListener(clickListener);
		findViewById(R.id.audio).setOnClickListener(clickListener);
		findViewById(R.id.draw).setOnClickListener(clickListener);
	}
	
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            getSupportMenuInflater().inflate(R.menu.actionmenu, menu);
//            menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (item.getItemId()==android.R.id.home) {
        	if(mDrawerLayout.isDrawerOpen(mDrawerList))
        		mDrawerLayout.closeDrawers();
        	else
        		mDrawerLayout.openDrawer(mDrawerList);
        	return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
	
	class ClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent;
			switch(v.getId()){
			case R.id.text:
				intent=new Intent(getApplicationContext(), TextActivity.class);
				startActivity(intent);
				break;
			case R.id.draw:
				intent=new Intent(getApplicationContext(), DrawActivity.class);
				startActivity(intent);
				break;
			case R.id.audio:
				intent=new Intent(getApplicationContext(), AudioActivity.class);
				startActivity(intent);
				break;
			}
		}
		
	}

	@Override
	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
