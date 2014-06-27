package cn.lijie.notepad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import cn.lijie.notepad.MApplication;
import cn.lijie.notepad.R;
import cn.lijie.notepad.data.DBhelper;
import cn.lijie.notepad.data.NoteInfo;
import cn.lijie.notepad.data.TextNoteHelper;
import cn.lijie.notepad.utils.GeneralUtils;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class TextActivity extends BaseActivity{

	private EditText title,textContent;
	private boolean isUpdate;
	private NoteInfo noteInfo;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.text_layout);
		initViews();
		
	}
	
	private void initViews(){
		title=(EditText) findViewById(R.id.title);
		textContent=(EditText) findViewById(R.id.textContent);
		
		title.setText(GeneralUtils.formatTime("yyyy-MM-dd HH:mm:ss",System.currentTimeMillis()));
		Intent intent=getIntent();
		if(intent.getBooleanExtra("isUpdate", false)){
			isUpdate=true;
			noteInfo=(NoteInfo) intent.getSerializableExtra("noteInfo");
			title.setText(noteInfo.noteName);
			title.setFocusable(false);
			
			textContent.setText(TextNoteHelper.getInstance().getContent(noteInfo.noteFilePath));
		}
		else{
			isUpdate=false;
		}
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.text_new_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.save:
			String content=textContent.getText().toString();
			if(content==null||content.equals("")){
				Toast.makeText(getApplication(), "请先输入内容", Toast.LENGTH_SHORT).show();
				return true;
			}
			String strTitle=title.getText().toString();
			if(strTitle==null||strTitle.equals("")){
				strTitle=GeneralUtils.formatTime("yyyy-MM-dd HH:mm:ss",System.currentTimeMillis());
			}
			if(isUpdate)
				TextNoteHelper.getInstance().saveTextNote(new DBhelper(getApplication()).getWritableDatabase(), content, strTitle, (MApplication)getApplication());
			else{
				TextNoteHelper.getInstance().updateTextNote(new DBhelper(getApplication()).getWritableDatabase(), noteInfo, content);
			}
				
			((MApplication)getApplication()).rushData=0;
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//保存
	
}
