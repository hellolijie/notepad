package cn.lijie.notepad.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import android.util.Log;

public class FileUtils {
    private String SDPath;
    
    public FileUtils(){
        //得到当前外部存储设备的目录
        SDPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
    }
    
    public String getSDPath(){
    	return SDPath;
    }
    /**
     * 在SD卡上创建文件
     * @param fileName
     * @return
*/
    public File createSDFile(String fileName){
        File file=new File(SDPath+fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    
    /**
     * 在SD卡上创建目录
     * @param dirName
     * @return
*/
    public File createSDDir(String dirName){
        File file=new File(SDPath+dirName);
        file.mkdirs();
        return file;
    }
    
    /**
     * 判断SD卡上文件是否存在
     * @param fileName
     * @return
*/
    public boolean isFileExist(String fileName){
        File file=new File(SDPath+fileName);
        return file.exists();
    }
    /**
     * 将一个inputStream里面的数据写到SD卡中
     * @param path
     * @param fileName
     * @param inputStream
     * @return
*/
    public File writeToSDfromInput(String path,String fileName,InputStream inputStream){
        //createSDDir(path);
        File file=createSDFile(path+fileName);
        OutputStream outStream=null;
        try {
            outStream=new FileOutputStream(file);
            byte[] buffer=new byte[4*1024];
            int len;
            while((len=inputStream.read(buffer))!=-1){
//                outStream.write(buffer);
                outStream.write(buffer, 0, len);
                Log.i("tag", len+"");
            }
            outStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
  //删除文件夹
	//param folderPath 文件夹完整绝对路径

	public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}

	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	}
	
	/** 
     * 复制单个文件 
     * @param oldPath String 原文件路径 如：c:/fqf.txt 
     * @param newPath String 复制后路径 如：f:/fqf.txt 
     * @return boolean 
     */ 
   public void copyFile(String oldPath, String newPath) { 
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //文件存在时 
               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
               FileOutputStream fs = new FileOutputStream(newPath); 
               byte[] buffer = new byte[1444]; 
               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //字节数 文件大小 
                   System.out.println(bytesum); 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
           } 
       } 
       catch (Exception e) { 
           System.out.println("复制单个文件操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 复制整个文件夹内容 
     * @param oldPath String 原文件路径 如：c:/fqf 
     * @param newPath String 复制后路径 如：f:/fqf/ff 
     * @return boolean 
     */ 
   public void copyFolder(String oldPath, String newPath) { 

       try { 
           (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
           File a=new File(oldPath); 
           String[] file=a.list(); 
           File temp=null; 
           for (int i = 0; i < file.length; i++) { 
               if(oldPath.endsWith(File.separator)){ 
                   temp=new File(oldPath+file[i]); 
               } 
               else{ 
                   temp=new File(oldPath+File.separator+file[i]); 
               } 

               if(temp.isFile()){ 
                   FileInputStream input = new FileInputStream(temp); 
                   FileOutputStream output = new FileOutputStream(newPath + "/" + 
                           (temp.getName()).toString()); 
                   byte[] b = new byte[1024 * 5]; 
                   int len; 
                   while ( (len = input.read(b)) != -1) { 
                       output.write(b, 0, len); 
                   } 
                   output.flush(); 
                   output.close(); 
                   input.close(); 
               } 
               if(temp.isDirectory()){//如果是子文件夹 
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
               } 
           } 
       } 
       catch (Exception e) { 
           System.out.println("复制整个文件夹内容操作出错"); 
           e.printStackTrace(); 

       } 

   }
}