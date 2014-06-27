package cn.lijie.notepad.fragment;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
import cn.lijie.notepad.activity.AudioActivity;
import cn.lijie.notepad.audio.AudioPlayer;
import cn.lijie.notepad.data.AudioNoteHelper;
import cn.lijie.notepad.data.DBhelper;
import cn.lijie.notepad.data.NoteInfo;

public class AudioListFragment extends Fragment{
	private List<NoteInfo> noteList;
	private AudioNoteHelper audioNoteHelper;
	private ListView listView;
	private boolean isPlaying;
	private AudioPlayer audioPlayer;
	private View curPlayingView;
	private ClickListener clickListener;
	private OnCompletionListener onCompletionListener;
	private AudioListAdapter listAdapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		MApplication application=(MApplication) getActivity().getApplication();
		if(application.rushData==1){
			noteList=audioNoteHelper.getAllAudioPad(new DBhelper(getActivity()).getReadableDatabase(),(MApplication)getActivity().getApplication());
			listAdapter.notifyDataSetInvalidated();
			application.rushData=-1;
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		if(isPlaying){
			audioPlayer.stopPlaying();
			isPlaying=false;
			((ImageView)curPlayingView).setImageResource(R.drawable.play);
			curPlayingView=null;
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		audioNoteHelper=AudioNoteHelper.getInstance();
		audioPlayer=AudioPlayer.getInstance();
		clickListener=new ClickListener();
		noteList=audioNoteHelper.getAllAudioPad(new DBhelper(getActivity()).getReadableDatabase(),(MApplication)getActivity().getApplication());
		onCompletionListener=new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				audioPlayer.stopPlaying();
				isPlaying=false;
				((ImageView)curPlayingView).setImageResource(R.drawable.play);
				curPlayingView=null;
			}
		};
		
		listView=new ListView(getActivity());
		listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		listView.setAdapter(listAdapter=new AudioListAdapter());
		listView.setDividerHeight(0);
		listView.setBackgroundColor(Color.parseColor("#F7F7F7"));
		
		isPlaying=false;
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(getActivity(), AudioActivity.class);
				intent.putExtra("isUpdate", true);
				intent.putExtra("noteInfo", noteList.get(arg2));
				startActivity(intent);
			}
		});
		
		return listView;
	}

	class AudioListAdapter extends BaseAdapter{

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
				convertView=LayoutInflater.from(getActivity()).inflate(R.layout.audio_list_item, null);
			NoteInfo noteInfo=noteList.get(noteList.size()-1-position);
			File[] files=audioNoteHelper.getAllAudioFile(noteInfo.noteFilePath);
			ViewGroup audioContent=(ViewGroup)convertView.findViewById(R.id.audioContent);
			audioContent.removeAllViews();
			for(File file:files){
				if(file.isFile()){
					String fileName=file.getName();
					fileName=fileName.replace("(", ".");
					String[] f=fileName.split("\\.");
					long time;
					if(f.length==0)
						time=Long.valueOf(fileName);
					else
						time=Long.valueOf(f[0]);
					addAudioItem(audioContent, file.getAbsolutePath(), time);
				}
			}
			return convertView;
		}
	}
	
	private void addAudioItem(ViewGroup audioContent,String path,long time){
		View item=LayoutInflater.from(getActivity().getApplication()).inflate(R.layout.audio_display_item, null);
		TextView startTime=((TextView)item.findViewById(R.id.startTime));
		measureTimeAndShow(time, startTime);
		startTime.setTag(time);
		View playAudio=item.findViewById(R.id.playAudio);
		playAudio.setTag(path);
		View deleteAudio=item.findViewById(R.id.deleteAudio);
		deleteAudio.setVisibility(View.GONE);
//		deleteAudio.setTag(item);
		playAudio.setOnClickListener(clickListener);
		deleteAudio.setOnClickListener(clickListener);
		audioContent.addView(item);
	}
	
	private void measureTimeAndShow(long timeCount,TextView timer){
		int minute=(int) (timeCount/60);
		int second=(int) (timeCount%60);
		String strMinute=minute>9?""+minute:"0"+minute;
		String strSecond=second>9?""+second:"0"+second;
		timer.setText(strMinute+":"+strSecond);
	}
	
	class ClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.playAudio:
				if(!isPlaying){
					audioPlayer.startPlaying((String)v.getTag(),onCompletionListener);
					((ImageView)v).setImageResource(R.drawable.pause);
					curPlayingView=v;
					isPlaying=true;
				}
				else{
					audioPlayer.stopPlaying();
					if(!v.equals(curPlayingView)){
						audioPlayer.startPlaying((String)v.getTag(),onCompletionListener);
						((ImageView)v).setImageResource(R.drawable.pause);
						((ImageView)curPlayingView).setImageResource(R.drawable.play);
						curPlayingView=v;
					}
					else{
						curPlayingView=null;
						isPlaying=false;
						((ImageView)v).setImageResource(R.drawable.play);
					}
				}
				break;
			}
		}
		
	}
}
