package cn.lijie.notepad.data;

import java.io.Serializable;

public class NoteInfo implements Serializable{
	public int noteId;		//��ǩid
	public long createTime;		//��ǩ����ʱ��
	public int padBookId;	//��ǩ��id
	public int padType;	//��ǩ����
	public String noteFilePath;  //�ļ�·��
	public String noteName;	//��ǩ����
}
