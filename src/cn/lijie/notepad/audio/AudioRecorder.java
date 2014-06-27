package cn.lijie.notepad.audio;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;
import android.widget.Toast;

public class AudioRecorder {
	private static AudioRecorder instance=new AudioRecorder();
	
	private MediaRecorder mRecorder;
	
	
	private AudioRecorder(){
	}
	public static AudioRecorder getInstance(){
		return instance;
	}
	
	public String startRecording(Context context) {
		try {
	        mRecorder = new MediaRecorder();
	        mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
	        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
	        try {
	        	File audioFile = File.createTempFile("record_", ".amr");
	//        mRecorder.setOutputFile(fileUtils.getSDPath()+"audiorecordtest.3gp");
	        	mRecorder.setOutputFile(audioFile.getAbsolutePath());
	//        	mRecorder.setOutputFile(path);
	        	mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
	            mRecorder.prepare();
	            mRecorder.start();
	            return audioFile.getAbsolutePath();
	        } catch (IOException e) {
	            Log.e("tag", "prepare() failed");
	        }
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
			// TODO: handle exception
		}
        return null;
    }
	
	public void stopRecording() {
		if(mRecorder!=null){
	        mRecorder.stop();
	        mRecorder.release();
	        mRecorder = null;
		}
    }
	 
	
}
