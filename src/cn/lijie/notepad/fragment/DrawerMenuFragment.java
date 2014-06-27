package cn.lijie.notepad.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.lijie.notepad.MApplication;
import cn.lijie.notepad.MainActivity;
import cn.lijie.notepad.R;
import cn.lijie.notepad.data.DBhelper;
import cn.lijie.notepad.data.PadInfo;
import cn.lijie.notepad.utils.FileUtils;

public class DrawerMenuFragment extends Fragment {
	private ClickListener clickListener;
	
	private ArrayList<PadInfo> padList;
	private MainActivity activity;
	private int curPadPosition;
	
	private ListView padListView;
	private View newPadContent;
	private ImageView newPad;
	
	private boolean isNewPadContentOpen;
	
	private PadListAdapter padListAdapter;
	
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		clickListener=new ClickListener();
		
		view=inflater.inflate(R.layout.drawer_menu_layout, null);
		padListView=(ListView)view.findViewById(R.id.padList);
		padListView.setAdapter(padListAdapter=new PadListAdapter());
		
		newPadContent=view.findViewById(R.id.newPadContent);
		(newPad=(ImageView) view.findViewById(R.id.newPad)).setOnClickListener(clickListener);
		view.findViewById(R.id.comfirmNewPad).setOnClickListener(clickListener);
		view.findViewById(R.id.cancelNewPad).setOnClickListener(clickListener);
		isNewPadContentOpen=false;
		
		padListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				curPadPosition=arg2;
				((MApplication)activity.getApplication()).curPadInfo=padList.get(curPadPosition);
				padListAdapter.notifyDataSetInvalidated();
				activity.notifyMenuChoised();
			}
		});
		return view;
	}

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=((MainActivity)activity);
//		this.padList=this.activity.padList;
		getPad();
	}
	
	//创建便签本
	private void createPad(SQLiteDatabase paramSQLiteDatabase,String padName,String padDesc){
		FileUtils fileUtils=new FileUtils();
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME);
		
		ContentValues padValues=new ContentValues();
		padValues.put(DBhelper.DBFIELD_PAD_PADNAME, padName);
		padValues.put(DBhelper.DBFIELD_PAD_PADDESC, padDesc);
		padValues.put(DBhelper.DBFIELD_PAD_CREATETIME, System.currentTimeMillis());
		padName=padName+System.currentTimeMillis();
		padValues.put(DBhelper.DBFIELD_PAD_PATH, fileUtils.getSDPath()+MApplication.FOLD_ROOTFOLDNAME+"/"+padName);
		paramSQLiteDatabase.insert(DBhelper.DB_PAD, null, padValues);
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME+"/"+padName);
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME+"/"+padName+"/"+MApplication.FOLD_AUDIOFOLDNAME);
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME+"/"+padName+"/"+MApplication.FOLD_DRAWFOLDNAME);
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME+"/"+padName+"/"+MApplication.FOLD_TEXTFOLDNAME);
	}
	

	//获取便签本
	private void getPad(){
		DBhelper dbHelper=new DBhelper(activity);
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery("select * from "+DBhelper.DB_PAD, null);
        padList=new ArrayList<PadInfo>();
        while(cursor.moveToNext()){
        	PadInfo padInfo=new PadInfo();
        	padInfo.createTime=cursor.getLong(cursor.getColumnIndex(DBhelper.DBFIELD_PAD_CREATETIME));
        	padInfo.padBookDesc=cursor.getString(cursor.getColumnIndex(DBhelper.DBFIELD_PAD_PADDESC));
        	padInfo.padBookName=cursor.getString(cursor.getColumnIndex(DBhelper.DBFIELD_PAD_PADNAME));
        	padInfo.padBookId=cursor.getInt(cursor.getColumnIndex(DBhelper.DBFIELD_PAD_PADID));
        	padInfo.padPath=cursor.getString(cursor.getColumnIndex(DBhelper.DBFIELD_PAD_PATH));
        	padList.add(padInfo);
        }
        database.close();
//        curPadPosition=0;
//        padListView.setAdapter(new PadListAdapter());
        ((MApplication)activity.getApplication()).curPadInfo=padList.get(curPadPosition);
	}

	class PadListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return padList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if(arg1==null)
				arg1=LayoutInflater.from(activity).inflate(R.layout.pad_list_item, null);
			TextView padName=(TextView) arg1.findViewById(R.id.padName);
			padName.setText(padList.get(arg0).padBookName);
			if(curPadPosition==arg0){
				arg1.setBackgroundColor(Color.parseColor("#F7F7F7"));
			}
			else{
				arg1.setBackgroundDrawable(null);
			}
			return arg1;
		}
	}
	
	
	private void newPadContentToggle(){
		if(isNewPadContentOpen){
			newPadContent.setVisibility(View.GONE);
			newPad.setBackgroundDrawable(null);
			isNewPadContentOpen=false;
		}
		else{
			newPadContent.setVisibility(View.VISIBLE);
			newPad.setBackgroundColor(Color.WHITE);
			isNewPadContentOpen=true;
		}
	}
	
	class ClickListener implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.newPad:
				newPadContentToggle();
				break;
			case R.id.comfirmNewPad:
				String padName=((EditText)view.findViewById(R.id.padName)).getText().toString();
				if(padName==null||"".equals(padName)){
					Toast.makeText(activity, "请输入便签本名字", Toast.LENGTH_SHORT).show();
					return;
				}
				String padDesc=((EditText)view.findViewById(R.id.padDesc)).getText().toString();
				createPad(new DBhelper(activity).getWritableDatabase(), padName, padDesc);
				newPadContentToggle();
				getPad();
				padListAdapter.notifyDataSetInvalidated();
				break;
			case R.id.cancelNewPad:
				newPadContentToggle();
				break;
			}
		}
		
	}
}
