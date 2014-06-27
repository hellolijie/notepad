package cn.lijie.notepad.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.lijie.notepad.MApplication;
import cn.lijie.notepad.R;
import cn.lijie.notepad.audio.AudioPlayer;
import cn.lijie.notepad.audio.AudioRecorder;
import cn.lijie.notepad.data.AudioNoteHelper;
import cn.lijie.notepad.data.DBhelper;
import cn.lijie.notepad.data.NoteInfo;
import cn.lijie.notepad.utils.GeneralUtils;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class AudioActivity extends BaseActivity{

	private ClickListener clickListener;
	
	private TextView timer;
	private boolean isRecoding,isPlaying;
	private int timeCount,itemTimeCount;
	
	private AudioRecorder audioRecoder;
	private AudioPlayer audioPlayer;
	
	private EditText title;
	private ImageView audioSwitch;
	private List<String> paths;
	
	private LinearLayout audioContent;
	
	private List<String> fileNames;
	
	private View curPlayingView,curRecordingView;
	private OnCompletionListener onCompletionListener;
	
	private NoteInfo noteInfo;
	private boolean isUpdate;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.audio_layout);
		clickListener=new ClickListener();
		audioRecoder=AudioRecorder.getInstance();
		audioPlayer=AudioPlayer.getInstance();
		paths=new ArrayList<String>();
		fileNames=new ArrayList<String>();
		onCompletionListener=new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				audioPlayer.stopPlaying();
				isPlaying=false;
				((ImageView)curPlayingView).setImageResource(R.drawable.play);
				curPlayingView=null;
			}
		};
		
		timer=(TextView) findViewById(R.id.timer);
		(audioSwitch=(ImageView) findViewById(R.id.audioSwitch)).setOnClickListener(clickListener);
		audioContent=(LinearLayout) findViewById(R.id.audioContent);
		
		isRecoding=false;
		isPlaying=false;
		timeCount=0;
		
		title=(EditText) findViewById(R.id.title);
        title.setText(GeneralUtils.formatTime("yyyy-MM-dd HH:mm",System.currentTimeMillis()));
        
        Intent intent=getIntent();
        if(intent.getBooleanExtra("isUpdate", false)){
        	noteInfo=(NoteInfo) intent.getSerializableExtra("noteInfo");
        	isUpdate=true;
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
			if(isRecoding){
				isRecoding=false;
				audioSwitch.setImageResource(R.drawable.audio);
				audioRecoder.stopRecording();
				fileNames.add(itemTimeCount+"("+System.currentTimeMillis()+")");
			}
			
			String strTitle=title.getText().toString();
			if(strTitle==null||strTitle.equals("")){
				strTitle=GeneralUtils.formatTime("yyyy-MM-dd HH:mm",System.currentTimeMillis());
			}
			AudioNoteHelper.getInstance().saveAudioNote(new DBhelper(getApplication()).getWritableDatabase(), 
					paths, strTitle, (MApplication)getApplication(),fileNames);
			((MApplication)getApplication()).rushData=1;
			finish();
			break;
		case android.R.id.home:
			for(String oldPath:paths){
				new File(oldPath).delete();
			}
			break;
		case R.id.addToNotifcation:
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
		if(isRecoding){
			isRecoding=false;
			audioSwitch.setImageResource(R.drawable.audio);
			audioRecoder.stopRecording();
		}
		
		if(isPlaying){
			audioPlayer.stopPlaying();
			isPlaying=false;
			((ImageView)curPlayingView).setImageResource(R.drawable.play);
			curPlayingView=null;
		}
	}

	private void countTime(){
		
		timer.postDelayed(new Runnable() {
			@Override
			public void run() {
				timeCount++;
				itemTimeCount++;
				measureTimeAndShow(timeCount, timer);
				measureTimeAndShow(itemTimeCount, (TextView)curRecordingView.findViewById(R.id.startTime));
				if(isRecoding){
					countTime();
				}
				
			}
		}, 1000);
	}
	
	private void measureTimeAndShow(int timeCount,TextView timer){
		int minute=(int) (timeCount/60);
		int second=(int) (timeCount%60);
		String strMinute=minute>9?""+minute:"0"+minute;
		String strSecond=second>9?""+second:"0"+second;
		timer.setText(strMinute+":"+strSecond);
	}
	
	private void addAudioItem(String tempPath){
		Long curTime=System.currentTimeMillis();
//		fileNames.add(curTime+"");
		View item=LayoutInflater.from(getApplication()).inflate(R.layout.audio_display_item, null);
		item.setTag(curTime+"!"+tempPath);
//		((TextView)item.findViewById(R.id.startTime)).setText(GeneralUtils.formatTime("yyyy-MM-dd HH:mm:ss", curTime));
		((TextView)item.findViewById(R.id.startTime)).setText("00:00");
		View deleteAudio=item.findViewById(R.id.deleteAudio);
		deleteAudio.setOnClickListener(clickListener);
		deleteAudio.setTag(item);
		View playAudio=item.findViewById(R.id.playAudio);
		playAudio.setOnClickListener(clickListener);
		playAudio.setTag(item);
		curRecordingView=item;
		audioContent.addView(item);
	}
	
	class ClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.audioSwitch:
				if(isRecoding){
					isRecoding=false;
					audioSwitch.setImageResource(R.drawable.audio);
					audioRecoder.stopRecording();
					fileNames.add(itemTimeCount+"("+System.currentTimeMillis()+")");
				}
				else{
					if(isPlaying){
						audioPlayer.stopPlaying();
						curPlayingView=null;
						isPlaying=false;
						((ImageView)v).setImageResource(R.drawable.play);
					}
					
					isRecoding=true;
					audioSwitch.setImageResource(R.drawable.no_audio);
					String tempPath=audioRecoder.startRecording(getApplication());
					paths.add(tempPath);
					addAudioItem(tempPath);
					itemTimeCount=0;
					countTime();
				}
				break;
			case R.id.deleteAudio:
				if(!isRecoding){
					View audioItem=(View) v.getTag();
					String[] names=((String) audioItem.getTag()).split("!");
					fileNames.remove(names[0]);
					paths.remove(names[1]);
					audioContent.removeView(audioItem);
					new File(names[1]).delete();
				}
				else{
					Toast.makeText(getApplication(), "«Îœ»Õ£÷π¬º“Ù", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.playAudio:
				if(!isRecoding){
					if(!isPlaying){
						View audioItem=(View) v.getTag();
						String[] names=((String) audioItem.getTag()).split("!");
						audioPlayer.startPlaying(names[1],onCompletionListener);
						((ImageView)v).setImageResource(R.drawable.pause);
						curPlayingView=v;
						isPlaying=true;
					}
					else{
						audioPlayer.stopPlaying();
						if(!v.equals(curPlayingView)){
							View audioItem=(View) v.getTag();
							String[] names=((String) audioItem.getTag()).split("!");
							audioPlayer.startPlaying(names[1],onCompletionListener);
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
				}
				else{
					Toast.makeText(getApplication(), "«Îœ»Õ£÷π¬º“Ù", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
		
	}
}
