package cn.sdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStream2Byte {

	static final int BUFF_SIZE = 512;
	static final int MAX_READ_LIMIT_BYTES = 10*1024*1024;
	
	 public static final InputStream byteToInputStream(byte[] buf) {  
	        return new ByteArrayInputStream(buf);  
	    }
	 
	 /**
	  * 在无需知道流大小的情况下， 完整读取输入流中的数据
	  * 防止ByteArrayOutputStream内部有溢出，强制InputStream中不超过MAX_READ_LIMIT_BYTES字节
	  * @param inStream
	  * @return
	  * @throws IOException
	  */
	 // overflow-conscious code
	 public static final byte[] inputStreamToByte(InputStream inStream) throws IOException {  
	    ByteArrayOutputStream swapStream = new ByteArrayOutputStream(BUFF_SIZE);  
	    byte[] buff = new byte[BUFF_SIZE];  
	    int totalBytes = 0;
	    int rc = -1;  
	    while ((rc = inStream.read(buff, 0, BUFF_SIZE)) != -1) {   
	        totalBytes += rc;
	        if(totalBytes > MAX_READ_LIMIT_BYTES){
	        	throw new RuntimeException("MAX_READ_LIMIT_BYTES");
	        }
	        swapStream.write(buff, 0, rc);
	    }  
	    byte[] in2b = swapStream.toByteArray(); 
	    return in2b;  
	 }  
}
