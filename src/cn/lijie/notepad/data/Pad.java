package cn.lijie.notepad.data;

public abstract class Pad {
	//¥Ê¥¢
	abstract void save();
	//ªÒ»°
	abstract <T> T getFile(String filePath);
}
