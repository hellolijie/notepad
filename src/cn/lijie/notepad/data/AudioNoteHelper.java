package cn.lijie.notepad.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.lijie.notepad.MApplication;
import cn.lijie.notepad.utils.FileUtils;

public class AudioNoteHelper {
	private static AudioNoteHelper instance=new AudioNoteHelper();
	private AudioNoteHelper(){}
	
	public static AudioNoteHelper getInstance(){
		return instance;
	}
	
	//保存
	public void saveAudioNote(SQLiteDatabase paramSQLiteDatabase,List<String> paths,String name,MApplication application,List<String> fileNames){
		String path=application.curPadInfo.padPath+"/"+MApplication.FOLD_AUDIOFOLDNAME+"/"+System.currentTimeMillis();
		File file=new File(path);
		file.mkdirs();
		saveFile(paths,path,fileNames);
		insetToDatabase(paramSQLiteDatabase,path,name,application.curPadInfo.padBookId);
	}
	
	//保存文件
	public void saveFile(List<String> paths,String path,List<String> fileNames){
		FileUtils fileUtils=new FileUtils();
		int i=0;
		for(String oldPath:paths){
			String newPath=path+("/"+fileNames.get(i)+".amr");
			fileUtils.copyFile(oldPath,newPath);
			new File(oldPath).delete();
			i++;
		}
	}
	
	
	
	//在数据库中创建新条目
	private void insetToDatabase(SQLiteDatabase paramSQLiteDatabase,String path,String name,int padId){
		ContentValues noteValues=new ContentValues();
		noteValues.put(DBhelper.DBFIELD_NOTE_PADID, padId);
		noteValues.put(DBhelper.DBFIELD_NOTE_NOTETYPE, MApplication.TYPE_AUDIO);
		noteValues.put(DBhelper.DBFIELD_NOTE_CREATETIME, System.currentTimeMillis());
		noteValues.put(DBhelper.DBFIELD_NOTE_NOTEFILEPATH, path);
		noteValues.put(DBhelper.DBFIELD_NOTE_NAME, name);
		paramSQLiteDatabase.insert(DBhelper.DB_NOTE, null, noteValues);
		paramSQLiteDatabase.close();
	}
	//更新数据库中的数据
	private void updateToDatabase(SQLiteDatabase paramSQLiteDatabase,NoteInfo noteInfo){
		ContentValues noteValues=new ContentValues();
		noteValues.put(DBhelper.DBFIELD_NOTE_PADID, noteInfo.padBookId);
		noteValues.put(DBhelper.DBFIELD_NOTE_NOTETYPE, noteInfo.padType);
		noteValues.put(DBhelper.DBFIELD_NOTE_CREATETIME, noteInfo.createTime);
		noteValues.put(DBhelper.DBFIELD_NOTE_NOTEFILEPATH, noteInfo.noteFilePath);
		noteValues.put(DBhelper.DBFIELD_NOTE_NAME, noteInfo.noteName);
		paramSQLiteDatabase.update(DBhelper.DB_NOTE, noteValues, DBhelper.DBFIELD_NOTE_NOTEID+"="+noteInfo.noteId, null);
	}
	
	//从数据库中获取所有text内容
	public List<NoteInfo> getAllAudioPad(SQLiteDatabase paramSQLiteDatabase,MApplication application){
		Cursor cursor=paramSQLiteDatabase.rawQuery(
				"select * from "+DBhelper.DB_NOTE+" where "+DBhelper.DBFIELD_NOTE_NOTETYPE+"="+MApplication.TYPE_AUDIO
				+" and "+DBhelper.DBFIELD_NOTE_PADID+"="+application.curPadInfo.padBookId, 
				null);
		List<NoteInfo> infoList=new ArrayList<NoteInfo>();
		while(cursor.moveToNext()){
			NoteInfo noteInfo=new NoteInfo();
			noteInfo.createTime=cursor.getLong(cursor.getColumnIndex(DBhelper.DBFIELD_NOTE_CREATETIME));
			noteInfo.noteName=cursor.getString(cursor.getColumnIndex(DBhelper.DBFIELD_NOTE_NAME));
			noteInfo.padBookId=cursor.getInt(cursor.getColumnIndex(DBhelper.DBFIELD_NOTE_PADID));
			noteInfo.noteFilePath=cursor.getString(cursor.getColumnIndex(DBhelper.DBFIELD_NOTE_NOTEFILEPATH));
			noteInfo.noteId=cursor.getInt(cursor.getColumnIndex(DBhelper.DBFIELD_NOTE_NOTEID));
			noteInfo.padType=MApplication.TYPE_AUDIO;
			infoList.add(noteInfo);
		}
		paramSQLiteDatabase.close();
		return infoList;
	}
	
	//获取文件夹下所有音频文件路径
	public File[] getAllAudioFile(String path){
		File file=new File(path);
		File[] files=null;
		if(file.isDirectory()){
			files=file.listFiles();
		}
		return files;
	}
	
	
}
