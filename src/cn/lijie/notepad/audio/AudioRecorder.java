package cn.lijie.notepad.audio;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

public class AudioRecorder {
	private static AudioRecorder instance=new AudioRecorder();
	
	private MediaRecorder mRecorder;
	
	
	private AudioRecorder(){
	}
	public static AudioRecorder getInstance(){
		return instance;
	}
	
	private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        try {
        	File audioFile = File.createTempFile("record_", ".3gp");
//        mRecorder.setOutputFile(fileUtils.getSDPath()+"audiorecordtest.3gp");
        	mRecorder.setOutputFile(audioFile.getAbsolutePath());
        	mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("tag", "prepare() failed");
        }

        mRecorder.start();
    }
	
	private void stopRecording() {
		if(mRecorder!=null){
	        mRecorder.stop();
	        mRecorder.release();
	        mRecorder = null;
		}
    }
	 
	
}
