package cn.lijie.notepad.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class IntentUtils {
	
	//�����ʼ�
	public static int sendMailByIntent(Context context,String title,String content,String address) {  
		try{
			Intent data=new Intent(Intent.ACTION_SENDTO); 
			data.setData(Uri.parse("mailto:"+address)); 
			data.putExtra(Intent.EXTRA_SUBJECT, title); 
			data.putExtra(Intent.EXTRA_TEXT, content); 
			context.startActivity(data);         
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
        return 1;  
  
    }  
	
	//���Ͷ���
	public static int sendSms(Context context,String content,String number){
		Uri uri = Uri.parse("smsto:"+number);          
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);          
		intent.putExtra("sms_body", content);          
		context.startActivity(intent);
		
		//ֱ�ӵ��ö��Žӿڷ�����
//		SmsManager smsManager = SmsManager.getDefault();
//		List<String> divideContents = smsManager.divideMessage(content);  
//		for (String text : divideContents) {  
//			smsManager.sendTextMessage("150xxxxxxxx", null, text, sentPI, deliverPI);  
//		}
		return 1;
	}
	
	//�������
	public static void startCamera(Activity activity,String path,String fileName,int requestCode){
		//��������ͷ
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file=new File(path);
		file.mkdirs();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(file,fileName+".jpg")));
		activity.startActivityForResult(intent, requestCode);
	}
	
	//�����������
	public static void startImageExploer(Activity activity,int requestCode){
		//����ͼƬ
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
		intent.addCategory(Intent.CATEGORY_OPENABLE); 
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(Intent.createChooser(intent,"ѡ��ͼƬ"),requestCode);
	}
	
	//����
	public static void share(Context context,String type,String subject,String content,String title) {

		Intent intent=new Intent(Intent.ACTION_SEND); 
//		intent.setType("image/*"); 
		intent.setType(type); 
		intent.putExtra(Intent.EXTRA_SUBJECT, subject); 
		intent.putExtra(Intent.EXTRA_TEXT, content);  
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context.startActivity(Intent.createChooser(intent, title)); 

	}

	
   /*this method can't be used in android mobile successful,but it can run normally in PC. 
    Because it will cause the java.lang.NoClassDefFoundError: javax.activation.DataHandler error 
    May be there are some way to solove it ......there are always javax package not found in android virtual mobile. 
    By the way ,the method use Apache mail jar       
    */  
//    public int sendMailByApache() {  
//  
//        try {  
//            HtmlEmail email = new HtmlEmail();  
//            // �����Ƿ��ͷ�����������  
//            email.setHostName("smtp.gmail.com");  
//            // ���뼯������  
//            email.setTLS(true);  
//            email.setSSL(true);  
//  
//            email.setCharset("gbk");  
//            // �ռ��˵�����  
//            email.addTo("181712000@qq.com");  
//            // �����˵�����  
//            email.setFrom("wcf0000@gmail.com");  
//            // �����Ҫ��֤��Ϣ�Ļ���������֤���û���-���롣�ֱ�Ϊ���������ʼ��������ϵ�ע�����ƺ�����  
//            email.setAuthentication("wcf1000", "00000");  
//            email.setSubject("����Email Apache");  
//            // Ҫ���͵���Ϣ  
//            email.setMsg("����Email Apache");  
//            // ����  
//            email.send();  
//        } catch (EmailException e) {  
//            // TODO Auto-generated catch block  
//            Log.i("IcetestActivity", e.getMessage());  
//        }  
//  
//        return 1;  
//    }  
/* 
 * this method use javamail for android ,it is a good jar, 
 * you can see the demo in http://www.jondev.net/articles/Sending_Emails_without_User_Intervention_(no_Intents)_in_Android 
 * and you also need three jars ,which I offered in attachement 
 *  
 * */  
//    public int sendMailByJavaMail() {  
//        Mail m = new Mail("wcfXXXX@gmail.com", "XXXXX");  
//        m.set_debuggable(true);  
//        String[] toArr = {"18170000@qq.com"};   
//        m.set_to(toArr);  
//        m.set_from("18170000@qq.com");  
//        m.set_subject("This is an email sent using icetest from an Android device");  
//        m.setBody("Email body. test by Java Mail");  
//        try {  
//            //m.addAttachment("/sdcard/filelocation");   
//            if(m.send()) {   
//            Log.i("IcetestActivity","Email was sent successfully.");  
//                          
//            } else {  
//                Log.i("IcetestActivity","Email was sent failed.");  
//            }  
//        } catch (Exception e) {  
//            // Toast.makeText(MailApp.this,  
//            // "There was a problem sending the email.",  
//            // Toast.LENGTH_LONG).show();  
//            Log.e("MailApp", "Could not send email", e);  
//        }  
//  
//        return 1;  
//    }  
}
