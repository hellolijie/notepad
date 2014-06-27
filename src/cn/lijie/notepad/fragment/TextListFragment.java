package cn.lijie.notepad.fragment;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.lijie.notepad.MApplication;
import cn.lijie.notepad.R;
import cn.lijie.notepad.activity.TextActivity;
import cn.lijie.notepad.data.DBhelper;
import cn.lijie.notepad.data.NoteInfo;
import cn.lijie.notepad.data.TextNoteHelper;

public class TextListFragment extends Fragment{
	
	private List<NoteInfo> noteList;
	private TextNoteHelper textNoteHelper;
	
	private ListView listView;
	
	private TextListAdapter listAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		textNoteHelper=TextNoteHelper.getInstance();
		noteList=textNoteHelper.getAllTextPad(new DBhelper(getActivity()).getReadableDatabase(),(MApplication)getActivity().getApplication());
//		View view=inflater.inflate(R.layout.text_list_layout, null);
		listView=new ListView(getActivity());
		listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		listView=(ListView) view.findViewById(R.id.list);
		listView.setAdapter((listAdapter=new TextListAdapter()));
		listView.setDividerHeight(0);
		listView.setBackgroundColor(Color.parseColor("#F7F7F7"));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(getActivity(), TextActivity.class);
				intent.putExtra("isUpdate", true);
				intent.putExtra("noteInfo", noteList.get(arg2));
				startActivity(intent);
			}
		});
		return listView;
	}

	@Override
	public void onResume() {
		MApplication application=(MApplication) getActivity().getApplication();
		if(application.rushData==0){
			noteList=textNoteHelper.getAllTextPad(new DBhelper(getActivity()).getReadableDatabase(),(MApplication)getActivity().getApplication());
			listAdapter.notifyDataSetInvalidated();
			application.rushData=-1;
		}
		super.onResume();
	}

	class TextListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return noteList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return noteList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null)
				convertView=LayoutInflater.from(getActivity()).inflate(R.layout.text_list_item, null);
			NoteInfo noteInfo=noteList.get(noteList.size()-1-position);
			convertView.setTag(noteInfo);
			((TextView)convertView.findViewById(R.id.title)).setText(noteInfo.noteName);
			((TextView)convertView.findViewById(R.id.desc)).setText(textNoteHelper.getContent(noteInfo.noteFilePath));
			
			return convertView;
		}
		
	}
}
