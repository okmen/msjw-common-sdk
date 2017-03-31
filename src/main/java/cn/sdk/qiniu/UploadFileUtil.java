package cn.sdk.qiniu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.rs.PutPolicy;


public class UploadFileUtil {
	private static Mac mac = new Mac(QiniuUtil.ACCESS_KEY, QiniuUtil.SECRET_KEY);
	private static PutPolicy putPolicy = new PutPolicy(QiniuUtil.bucketName);
	private Logger log = Logger.getLogger( UploadFileUtil.class );
	
	/**
	 * 上传文件
	 * @param uploadfile
	 * @param fileName
	 * @return
	 */
	public static String uploadFile(File uploadfile,String fullFileName) {
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		// 创建目录文件夹
		String fileDir = "/workspace/uploadFileTemp/";
		File file = new File(fileDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String uploadPath = "";
		if (uploadfile != null) {
			uploadPath = fileDir + fullFileName;
			uploadFileUtil.uploadImg(uploadPath,uploadfile);
		}
		uploadPath = fileDir + fullFileName;
		uploadPath = uploadFileUtil.uploadFileToQiNiu(uploadPath);
		
	
		
		return uploadPath;
	}
	/**
	 * 上传图片到七牛
	 * @param userId
	 * @param fileUrl
	 * @return
	 */
	private String uploadFileToQiNiu(String fileUrl) {
		try {
			String uptoken = putPolicy.token(mac);
			PutExtra extra = new PutExtra();
			String fileName= getRandomFileName() ;//七牛放保存的文件名
			fileName = "0" + "/" + fileName;
			IoApi.putFile(uptoken, fileName, fileUrl, extra);
			return QiniuUtil.qiniuImgDomain + "/" + fileName;
		} catch (Exception e) {
			log.error("uploadFileToQiNiu 异常", e);
		}
		return "";
	}
	
	private  String getRandomFileName(){
		Random random = new Random();
		int result=random.nextInt(9999);
		String dirName = new Date().getTime()+""+result+".jpg";   //格式为:upload/+时间戳+0-4位随机数+.jpg
		return dirName;
	}
	
    
	private  void uploadImg(String path,File f) {
		BufferedReader bis = null; // 字符读流
		BufferedWriter bw = null; // 字符写流
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;

		InputStream is = null;
		try {
			is = new FileInputStream(f);
			if (is != null) {
				fos = new FileOutputStream(path);
				byte[] b = new byte[4096];
				int len = 0;
				while ((len = is.read(b)) > 0) {
					fos.write(b, 0, len);
				}
				fos.flush();
				is.close();
			}

		} catch (Exception e) {
			log.error("uploadImg 异常", e);
		} finally {
			closeIO(bis, bw, fos, osw, null);
		}
	}
	
	private  void closeIO(BufferedReader is, BufferedWriter bw,
			FileOutputStream fos, OutputStreamWriter osw, InputStream fin) {
		try {
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (osw != null) {
				osw.close();
			}
			if (bw != null) {
				bw.close();
			}
			if (fin != null) {
				fin.close();
			}
		} catch (IOException e) {
			log.error("closeIO 异常", e);
		}
	}
	
	
	public static void main(String args[]){
		String filenameString="1_bing662078.jpg";
		File file = new File("E:/1_bing662078.jpg");
		String uploadResult = uploadFile(file, filenameString);
		System.out.println(uploadResult);
		
	}
}
