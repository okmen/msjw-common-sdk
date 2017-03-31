/**
 * @filename			IpUtils.java
 * @function			客户端Ip通用处理类
 * @author				skyz <skyzhw@gmail.com>
 * @datetime			Mar	1, 2008
 * @lastmodify			Mar	1, 2008
 */
package cn.sdk.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * 客户端Ip通用处理类
 * 
 * @author skyz <skyzhw@gmail.com>
 * @version 1.0
 */
public class IPUtil {
	/**
	 * ip地址转化为长整型
	 * @param ip
	 * @return
	 * @throws UnknownHostException
	 */
	public static long ip2long(String ip){
		int ipNum=0;
		try {
			ipNum = str2Ip(ip);
		} catch (UnknownHostException e) {
			return ipNum;
		}
		return int2long(ipNum);
	}
	/**
	 * 长整开型转化为ip地址
	 * @param ip
	 * @return
	 */
	public static String long2ip(long ip) {
		int[] b = new int[4];
		b[0] = (int) ((ip >> 24) & 0xff);
		b[1] = (int) ((ip >> 16) & 0xff);
		b[2] = (int) ((ip >> 8) & 0xff);
		b[3] = (int) (ip & 0xff);
		String x;
		x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2])
				+ "." + Integer.toString(b[3]);

		return x;
	}	
	/**
	 * int转化为长整型
	 * @param i
	 * @return
	 */
	private static long int2long(int i) {
		long l = i & 0x7fffffffL;
		if (i < 0) {
			l |= 0x080000000L;
		}
		return l;
	}
	private static int str2Ip(String ip) throws UnknownHostException {
		InetAddress address = InetAddress.getByName(ip);
		byte[] bytes = address.getAddress();
		int a, b, c, d;
		a = byte2int(bytes[0]);
		b = byte2int(bytes[1]);
		c = byte2int(bytes[2]);
		d = byte2int(bytes[3]);
		int result = (a << 24) | (b << 16) | (c << 8) | d;
		return result;
	}

	private static int byte2int(byte b) {
		int l = b & 0x07f;
		if (b < 0) {
			l |= 0x80;
		}
		return l;
	}

    public static Collection<InetAddress> getAllHostAddress() {   
        try {   
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();   
            Collection<InetAddress> addresses = new ArrayList<InetAddress>();   
            while (networkInterfaces.hasMoreElements()) {   
                NetworkInterface networkInterface = networkInterfaces.nextElement();   
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();   
                while (inetAddresses.hasMoreElements()) {   
                    InetAddress inetAddress = inetAddresses.nextElement();  
                    if(inetAddress instanceof Inet4Address){
                    		addresses.add(inetAddress);   
                    }
                }   
            }   
            return addresses;   
        } catch (SocketException e) {   
            throw new RuntimeException(e.getMessage(), e);   
        }   
    }   
       
    public static Collection<String> getAllNoLoopbackAddresses() {   
        Collection<String> noLoopbackAddresses = new ArrayList<String>();   
        Collection<InetAddress> allInetAddresses = getAllHostAddress();   
           
        for (InetAddress address : allInetAddresses) { 
            if (!address.isLoopbackAddress()) { 
                noLoopbackAddresses.add(address.getHostAddress());   
            }   
        }   
        return noLoopbackAddresses;   
    }   
       
    public static String getFirstNoLoopbackAddress() {   
        Collection<String> allNoLoopbackAddresses = getAllNoLoopbackAddresses();
        if(allNoLoopbackAddresses.iterator().hasNext()){
        		return allNoLoopbackAddresses.iterator().next();
        }
        return null;
    }   	
}
