package cn.lijie.notepad.data;

import java.io.Serializable;

public class NoteInfo implements Serializable{
	public int noteId;		//便签id
	public long createTime;		//便签创建时间
	public int padBookId;	//便签本id
	public int padType;	//便签类型
	public String noteFilePath;  //文件路径
	public String noteName;	//便签标题
}
