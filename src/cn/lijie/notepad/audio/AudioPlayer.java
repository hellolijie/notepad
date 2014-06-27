package cn.lijie.notepad.audio;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class AudioPlayer {
	private static AudioPlayer instance=new AudioPlayer();
	
	private MediaPlayer mPlayer;
	
	private AudioPlayer(){}
	public static AudioPlayer getInstance(){
		return instance;
	}
	
	public void startPlaying(String mFileName,OnCompletionListener onCompletionListener) {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(onCompletionListener);
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("tag", "prepare() failed");
        }
    }
	 
	public void stopPlaying() {
		if(mPlayer!=null){
	        mPlayer.release();
	        mPlayer = null;
		}
    }
}
