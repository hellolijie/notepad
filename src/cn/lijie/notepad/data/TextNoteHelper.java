package cn.lijie.notepad.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.lijie.notepad.MApplication;

public class TextNoteHelper {
	private static TextNoteHelper instance=new TextNoteHelper();
	
//	private DBhelper dbHelper;
	
	private TextNoteHelper(){
//		dbHelper=new DBhelper(context);
	}
	
	public static TextNoteHelper getInstance(){
		return instance;
	}
	//保存内容
	public void saveTextNote(SQLiteDatabase paramSQLiteDatabase,String content,String name,MApplication application){
		String path=application.curPadInfo.padPath+"/"+MApplication.FOLD_TEXTFOLDNAME+"/"+System.currentTimeMillis()+".txt";
		File file=new File(path);
		try {
			file.createNewFile();
			saveFile(content,path);
			insetToDatabase(paramSQLiteDatabase,path,name,application.curPadInfo.padBookId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//更新内容
	public void updateTextNote(SQLiteDatabase paramSQLiteDatabase,NoteInfo noteInfo,String content){
		saveFile(content, noteInfo.noteFilePath);
		updateToDatabase(paramSQLiteDatabase, noteInfo);
	}
	
	//保存文件
	private void saveFile(String content ,String path){
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(path, false)));
			out.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//在数据库中创建新条目
	private void insetToDatabase(SQLiteDatabase paramSQLiteDatabase,String path,String name,int padId){
		ContentValues noteValues=new ContentValues();
		noteValues.put(DBhelper.DBFIELD_NOTE_PADID, padId);
		noteValues.put(DBhelper.DBFIELD_NOTE_NOTETYPE, MApplication.TYPE_TEXT);
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
	public List<NoteInfo> getAllTextPad(SQLiteDatabase paramSQLiteDatabase,MApplication application){
		Cursor cursor=paramSQLiteDatabase.rawQuery(
				"select * from "+DBhelper.DB_NOTE+" where "+DBhelper.DBFIELD_NOTE_NOTETYPE+"="+MApplication.TYPE_TEXT
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
			noteInfo.padType=MApplication.TYPE_TEXT;
			infoList.add(noteInfo);
		}
		paramSQLiteDatabase.close();
		return infoList;
	}
	
	//从文件中获取note内容
	public String getContent(String filePath){
		BufferedReader reader=null;
		char[] buffer=new char[1024];
		String content="";
		try {
			reader=new BufferedReader(new FileReader(filePath));
			try {
				int len=0;
				while((len=reader.read(buffer, 0, buffer.length))!=-1){
					content+=new String(buffer, 0, len);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	
	//删除
	private void deleteNote(NoteInfo noteInfo){
		
	}
	
}
