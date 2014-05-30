package cn.lijie.notepad.data;

import cn.lijie.notepad.MApplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper{
	//数据库及字段名称
	public static int DB_DATABASEVERSION=1;
	public static String DB_PADBOOK="padBook";
	public static String DB_PAD="pad";
	public static String DBFIELD_PADBOOK_PADOBOOKID="_ID";
	public static String DBFIELD_PADBOOK_CREATETIME="create_time";
	public static String DBFIELD_PADBOOK_PADBOOKNAME="name";
	public static String DBFIELD_PADBOOK_PADBOOKDESC="description";
	public static String DBFIELD_PAD_PADID="_ID";
	public static String DBFIELD_PAD_CREATETIME="create_time";
	public static String DBFIELD_PAD_PADBOOKID="padbook_id";
	public static String DBFIELD_PAD_PADTYPE="type";
	public static String DBFIELD_PAD_PADFILEPATH="filepath";
	public static String DBNAME="notepad.db";
	

	public DBhelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt){
    super(paramContext, paramString, paramCursorFactory, paramInt);
	}
  
  	public DBhelper(Context paramContext){
	  	super(paramContext, DBNAME, null, DB_DATABASEVERSION);
  	}

  	public void onCreate(SQLiteDatabase paramSQLiteDatabase){
	    paramSQLiteDatabase.execSQL("create table if not exists "+DB_PAD+" (" +
	    		DBFIELD_PAD_PADID+" integer primary key autoincrement," +
	    		DBFIELD_PAD_PADBOOKID+" integer," +
	    		DBFIELD_PAD_PADTYPE+ " integer," +
	    		DBFIELD_PAD_CREATETIME+ " integer)");
	    paramSQLiteDatabase.execSQL("create table if not exists "+DB_PADBOOK+" (" +
	    		DBFIELD_PADBOOK_PADOBOOKID+" integer primary key autoincrement," +
	    		DBFIELD_PADBOOK_PADBOOKNAME+" text," +
	    		DBFIELD_PADBOOK_PADBOOKDESC+ " text," +
	    		DBFIELD_PADBOOK_CREATETIME+ " integer" +
	    		DBFIELD_PAD_PADFILEPATH+	" text)");
  	}

  	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2){
  	}
}