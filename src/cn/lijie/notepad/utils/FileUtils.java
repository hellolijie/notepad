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
        //�õ���ǰ�ⲿ�洢�豸��Ŀ¼
        SDPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
    }
    
    public String getSDPath(){
    	return SDPath;
    }
    /**
     * ��SD���ϴ����ļ�
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
     * ��SD���ϴ���Ŀ¼
     * @param dirName
     * @return
*/
    public File createSDDir(String dirName){
        File file=new File(SDPath+dirName);
        file.mkdirs();
        return file;
    }
    
    /**
     * �ж�SD�����ļ��Ƿ����
     * @param fileName
     * @return
*/
    public boolean isFileExist(String fileName){
        File file=new File(SDPath+fileName);
        return file.exists();
    }
    /**
     * ��һ��inputStream���������д��SD����
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
    
  //ɾ���ļ���
	//param folderPath �ļ�����������·��

	public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //ɾ����������������
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //ɾ�����ļ���
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}

	//ɾ��ָ���ļ����������ļ�
	//param path �ļ�����������·��
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
	             delAllFile(path + "/" + tempList[i]);//��ɾ���ļ���������ļ�
	             delFolder(path + "/" + tempList[i]);//��ɾ�����ļ���
	             flag = true;
	          }
	       }
	       return flag;
	}
	
	/** 
     * ���Ƶ����ļ� 
     * @param oldPath String ԭ�ļ�·�� �磺c:/fqf.txt 
     * @param newPath String ���ƺ�·�� �磺f:/fqf.txt 
     * @return boolean 
     */ 
   public void copyFile(String oldPath, String newPath) { 
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //�ļ�����ʱ 
               InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ� 
               FileOutputStream fs = new FileOutputStream(newPath); 
               byte[] buffer = new byte[1444]; 
               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //�ֽ��� �ļ���С 
                   System.out.println(bytesum); 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
           } 
       } 
       catch (Exception e) { 
           System.out.println("���Ƶ����ļ���������"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * ���������ļ������� 
     * @param oldPath String ԭ�ļ�·�� �磺c:/fqf 
     * @param newPath String ���ƺ�·�� �磺f:/fqf/ff 
     * @return boolean 
     */ 
   public void copyFolder(String oldPath, String newPath) { 

       try { 
           (new File(newPath)).mkdirs(); //����ļ��в����� �������ļ��� 
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
               if(temp.isDirectory()){//��������ļ��� 
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
               } 
           } 
       } 
       catch (Exception e) { 
           System.out.println("���������ļ������ݲ�������"); 
           e.printStackTrace(); 

       } 

   }
}