package cn.lijie.notepad.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.lijie.notepad.MApplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

public class DrawNoteHelper {
	private static DrawNoteHelper instance=new DrawNoteHelper();
	
	public static DrawNoteHelper getInstance(){
		return instance;
	}
	
	private DrawNoteHelper(){}
	
	//保存
	public void saveDrawNote(SQLiteDatabase paramSQLiteDatabase,Bitmap bitmap,String name,MApplication application){
		String path=application.curPadInfo.padPath+"/"+MApplication.FOLD_DRAWFOLDNAME+"/"+System.currentTimeMillis()+".JPEG";
		File file=new File(path);
		try {
			file.createNewFile();
			saveFile(bitmap,path);
			insetToDatabase(paramSQLiteDatabase,path,name,application.curPadInfo.padBookId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//保存文件
	private void saveFile(Bitmap bitmap,String path){
		File dirFile = new File(path);
		if (!dirFile.exists())
			dirFile.mkdirs();
		File file = new File(path);
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {
			Log.w("ImageFileCache", "FileNotFoundException");
		} catch (IOException e) {
			
		}
	}
	//在数据库中创建新条目
	private void insetToDatabase(SQLiteDatabase paramSQLiteDatabase,String path,String name,int padId){
		ContentValues noteValues=new ContentValues();
		noteValues.put(DBhelper.DBFIELD_NOTE_PADID, padId);
		noteValues.put(DBhelper.DBFIELD_NOTE_NOTETYPE, MApplication.TYPE_DRAW);
		noteValues.put(DBhelper.DBFIELD_NOTE_CREATETIME, System.currentTimeMillis());
		noteValues.put(DBhelper.DBFIELD_NOTE_NOTEFILEPATH, path);
		noteValues.put(DBhelper.DBFIELD_NOTE_NAME, name);
		paramSQLiteDatabase.insert(DBhelper.DB_NOTE, null, noteValues);
		paramSQLiteDatabase.close();
	}
	//更新数据库中的数据
	private void updateToDatabase(){
	}
	
	//从数据库中获取所有
	public List<NoteInfo> getAllDrawPad(SQLiteDatabase paramSQLiteDatabase,MApplication application){
		Cursor cursor=paramSQLiteDatabase.rawQuery(
				"select * from "+DBhelper.DB_NOTE+" where "+DBhelper.DBFIELD_NOTE_NOTETYPE+"="+MApplication.TYPE_DRAW
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
			noteInfo.padType=MApplication.TYPE_DRAW;
			infoList.add(noteInfo);
		}
		paramSQLiteDatabase.close();
		return infoList;
	}
}
