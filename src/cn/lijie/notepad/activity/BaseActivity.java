package cn.lijie.notepad.activity;

import android.os.Bundle;

import cn.lijie.notepad.R;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class BaseActivity extends SherlockActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
