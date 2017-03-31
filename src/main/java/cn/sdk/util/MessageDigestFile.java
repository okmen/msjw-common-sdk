/**
 * @filename			Md5File.java
 * @function			取文件的md5
 * @author				skyz <skyzhw@gmail.com>
 * @copyright 			ku6.com
 * @datetime			Jul 1, 2008
 * @lastmodify			Jul 1, 2008
 */
package cn.sdk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author skyz
 * @since Mar 4, 2008
 * @version 1.1
 */
public class MessageDigestFile {
	private MessageDigest messageDigest;
	public MessageDigestFile(){
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
	}
	public MessageDigestFile(String type){
		try {
			messageDigest = MessageDigest.getInstance(type);
		} catch (NoSuchAlgorithmException e) {
		}
	}	
	/**
	 * 重置较验缓冲区
	 * @param data
	 */
	public void reset(){
		messageDigest.reset();
	}
	/**
	 * 更新md5较验缓冲区
	 * @param data
	 */
	public void update(byte[] data){
		messageDigest.update(data);
	}	
	/**
	 * 返回md5值
	 * @return
	 */
	public String getMessageDigest(){
		String result=bytesToString(messageDigest.digest());
		reset();
		return result;
	}
	/**
	 * 获取文件md5值
	 * @param file
	 * @return
	 */
    public String getMessageDigest(File file) {
        try {
			return getMessageDigest(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			return null;
		}
    }  
	/**
	 * 获取文件md5值
	 * @param file
	 * @return
	 */
    public String getMessageDigest(InputStream inputStream) {
        FileInputStream fis =(FileInputStream)inputStream;
        try {
            byte[] buffer = new byte[8192];
            int length = -1;
            reset();
            while ((length = fis.read(buffer)) != -1) {
            	messageDigest.update(buffer, 0, length);
            }
            return bytesToString(messageDigest.digest());
        } catch (IOException ex) {
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
            }
        }
    }    
    /**
     * 将字节码转换为16进的字符串
     * @param data
     * @return
     */
    private String bytesToString(byte[] data) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        char[] temp = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            temp[i * 2] = hexDigits[b >>> 4 & 0x0f];
            temp[i * 2 + 1] = hexDigits[b & 0x0f];
        }
        return new String(temp);
    }	
}
