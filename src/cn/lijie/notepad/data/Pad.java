package cn.lijie.notepad.data;

public abstract class Pad {
	//�洢
	abstract void save();
	//��ȡ
	abstract <T> T getFile(String filePath);
}
