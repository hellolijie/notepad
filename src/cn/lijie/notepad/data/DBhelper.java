package cn.lijie.notepad.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cn.lijie.notepad.MApplication;
import cn.lijie.notepad.utils.FileUtils;

public class DBhelper extends SQLiteOpenHelper{
	//数据库及字段名称
	public static int DB_DATABASEVERSION=1;
	public static String DB_PAD="pad";
	public static String DB_NOTE="note";
	public static String DBFIELD_PAD_PADID="_ID";
	public static String DBFIELD_PAD_CREATETIME="create_time";
	public static String DBFIELD_PAD_PADNAME="name";
	public static String DBFIELD_PAD_PADDESC="description";
	public static String DBFIELD_PAD_PATH="path";
	
	
	public static String DBFIELD_NOTE_NOTEID="_ID";
	public static String DBFIELD_NOTE_CREATETIME="create_time";
	public static String DBFIELD_NOTE_PADID="pad_id";
	public static String DBFIELD_NOTE_NOTETYPE="type";
	public static String DBFIELD_NOTE_NOTEFILEPATH="filepath";
	public static String DBFIELD_NOTE_NAME="name";
	public static String DBNAME="notepad.db";
	
	private static String INITPADNAME="firstpad";
	
	public DBhelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt){
    super(paramContext, paramString, paramCursorFactory, paramInt);
	}
  
  	public DBhelper(Context paramContext){
	  	super(paramContext, DBNAME, null, DB_DATABASEVERSION);
  	}

  	public void onCreate(SQLiteDatabase paramSQLiteDatabase){
	    paramSQLiteDatabase.execSQL("create table if not exists "+DB_NOTE+" (" +
	    		DBFIELD_NOTE_NOTEID+" integer primary key autoincrement," +
	    		DBFIELD_NOTE_PADID+" integer," +
	    		DBFIELD_NOTE_NOTETYPE+ " integer," +
	    		DBFIELD_NOTE_CREATETIME+ " integer," +
	    		DBFIELD_NOTE_NOTEFILEPATH+ " text," +
	    		DBFIELD_NOTE_NAME+ " text)");
	    paramSQLiteDatabase.execSQL("create table if not exists "+DB_PAD+" (" +
	    		DBFIELD_PAD_PADID+" integer primary key autoincrement," +
	    		DBFIELD_PAD_PADNAME+" text," +
	    		DBFIELD_PAD_PADDESC+ " text," +
	    		DBFIELD_PAD_CREATETIME+ " integer," +
	    		DBFIELD_PAD_PATH+ " text)");
	    
	    initialData(paramSQLiteDatabase);
//	    paramSQLiteDatabase.close();
  	}
  	
  	//初始化数据
  	private void initialData(SQLiteDatabase paramSQLiteDatabase){
		FileUtils fileUtils=new FileUtils();
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME);
		
		ContentValues padValues=new ContentValues();
		padValues.put(DBFIELD_PAD_PADNAME, INITPADNAME);
		padValues.put(DBFIELD_PAD_PADDESC, "系统建立的第一个便签本");
		padValues.put(DBFIELD_PAD_CREATETIME, System.currentTimeMillis());
		padValues.put(DBFIELD_PAD_PATH, fileUtils.getSDPath()+MApplication.FOLD_ROOTFOLDNAME+"/"+INITPADNAME);
		paramSQLiteDatabase.insert(DB_PAD, null, padValues);
		Log.i("tag", "dddd1");
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME+"/"+INITPADNAME);
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME+"/"+INITPADNAME+"/"+MApplication.FOLD_AUDIOFOLDNAME);
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME+"/"+INITPADNAME+"/"+MApplication.FOLD_DRAWFOLDNAME);
		fileUtils.createSDDir(MApplication.FOLD_ROOTFOLDNAME+"/"+INITPADNAME+"/"+MApplication.FOLD_TEXTFOLDNAME);
		
		
		ContentValues noteValues=new ContentValues();
		noteValues.put(DBFIELD_NOTE_PADID, 1);
		noteValues.put(DBFIELD_NOTE_NOTETYPE, MApplication.TYPE_TEXT);
		noteValues.put(DBFIELD_NOTE_CREATETIME, System.currentTimeMillis());
		noteValues.put(DBFIELD_NOTE_NAME, "你好");
		
		String path=MApplication.FOLD_ROOTFOLDNAME+"/"+INITPADNAME+"/"+MApplication.FOLD_TEXTFOLDNAME;
		fileUtils.createSDDir(path);
		path+="/test.txt";
		fileUtils.createSDFile(path);
		String content="hello word";
		path=fileUtils.getSDPath()+path;
		
		noteValues.put(DBFIELD_NOTE_NOTEFILEPATH, path);
		
		Log.i("tag", "dddd2");
		
		paramSQLiteDatabase.insert(DB_NOTE, null, noteValues);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(path, true)));
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
		Log.i("tag", "dddd3");
	}
  	
  	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2){
  	}
}