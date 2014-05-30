package cn.lijie.notepad.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import cn.lijie.notepad.R;

public class AudioActivity extends BaseActivity{

	private ClickListener clickListener;
	
	private TextView timer;
	private boolean isRecoding;
	private Long timeCount;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.audio_layout);
		clickListener=new ClickListener();
		timer=(TextView) findViewById(R.id.timer);
		findViewById(R.id.audioSwitch).setOnClickListener(clickListener);
		
		isRecoding=false;
		timeCount=0l;
		
		
	}
	
	private void countTime(){
		timer.postDelayed(new Runnable() {
			@Override
			public void run() {
				timeCount++;
				int minute=(int) (timeCount/60);
				int second=(int) (timeCount%60);
				String strMinute=minute>9?""+minute:"0"+minute;
				String strSecond=second>9?""+second:"0"+second;
				if(isRecoding){
					timer.setText(strMinute+":"+strSecond);
					countTime();
				}
				Log.i("tag", timeCount+"");
			}
		}, 1000);
	}
	
	class ClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.audioSwitch:
				if(isRecoding){
					isRecoding=false;
				}
				else{
					isRecoding=true;
					countTime();
				}
			}
		}
		
	}
}
