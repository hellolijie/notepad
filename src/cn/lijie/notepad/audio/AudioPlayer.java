package cn.lijie.notepad.audio;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

public class AudioPlayer {
	private static AudioPlayer instance=new AudioPlayer();
	
	private MediaPlayer mPlayer;
	
	private AudioPlayer(){}
	public static AudioPlayer getInstance(){
		return instance;
	}
	
	private void startPlaying(String mFileName) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("tag", "prepare() failed");
        }
    }
	 
	private void stopPlaying() {
		if(mPlayer!=null){
	        mPlayer.release();
	        mPlayer = null;
		}
    }
}
