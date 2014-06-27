package cn.lijie.notepad.fragment;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.lijie.notepad.MApplication;
import cn.lijie.notepad.R;
import cn.lijie.notepad.activity.DrawActivity;
import cn.lijie.notepad.data.DBhelper;
import cn.lijie.notepad.data.DrawNoteHelper;
import cn.lijie.notepad.data.NoteInfo;

public class DrawListFragment extends Fragment{
	private List<NoteInfo> noteList;
	private DrawNoteHelper drawNoteHelper;
	
	private ListView listView;
	
	private DrawListAdapter listAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		drawNoteHelper=DrawNoteHelper.getInstance();
		noteList=drawNoteHelper.getAllDrawPad(new DBhelper(getActivity()).getReadableDatabase(),(MApplication)getActivity().getApplication());
		listView=new ListView(getActivity());
		listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		listView.setAdapter(listAdapter=new DrawListAdapter());
		listView.setDividerHeight(0);
		listView.setBackgroundColor(Color.parseColor("#F7F7F7"));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(getActivity(), DrawActivity.class);
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
		if(application.rushData==2){
			noteList=drawNoteHelper.getAllDrawPad(new DBhelper(getActivity()).getReadableDatabase(),(MApplication)getActivity().getApplication());
			listAdapter.notifyDataSetInvalidated();
			application.rushData=-1;
		}
		super.onResume();
	}



	class DrawListAdapter extends BaseAdapter{

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
				convertView=LayoutInflater.from(getActivity()).inflate(R.layout.draw_list_item, null);
			NoteInfo noteInfo=noteList.get(noteList.size()-1-position);
			convertView.setTag(noteInfo);
			((TextView)convertView.findViewById(R.id.title)).setText(noteInfo.noteName);
			((ImageView)convertView.findViewById(R.id.content)).setImageURI(Uri.fromFile(new File(noteInfo.noteFilePath)));
			return convertView;
		}
		
	}
}
