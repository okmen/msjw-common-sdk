/**
 * @filename			SerializableUtil.java
 * @author				skyz <skyzhw@gmail.com>
 * @copyright 			skyzhw@gmail.com
 * @datetime			May 13, 2008
 * @lastmodify			May 13, 2008
 */
package cn.sdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.io.Serializable;
/**
 * SerializableUtil
 * 
 * @author zhanghuawei
 * 
 */
public class SerializableUtil implements Serializable{

	private static final long serialVersionUID = -7566218099337349154L;

	public static byte[] serialize(Object object) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8 * 1024);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(object);
		oos.close();
		byte[] data = bos.toByteArray();
		bos.close();
		return data;
	}

	public static Object deSerialize(byte[] data) throws Exception {
		Object object = null;
		if (data != null) {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bis);
			object = ois.readObject();
		}
		return object;
	}
	
 
    public static Timestamp readTimestamp(ObjectInput in, long nullFlag) throws ClassNotFoundException, IOException {
        Timestamp stamp = null;
        long value = in.readLong();
        if (value != nullFlag) {
            stamp = new Timestamp(value);
        }
        return stamp;
    }

    public static void writeTimestamp(Timestamp stamp, ObjectOutput out, long nullFlag) throws IOException {
        if (stamp != null) {
            long value = stamp.getTime();
            out.writeLong(value);
        } else {
            out.writeLong(nullFlag);
        }
    }

    public static String readString(ObjectInput in) throws ClassNotFoundException, IOException {
        String str = null;
        byte[] bytes = (byte[]) in.readObject();
        if (bytes != null) {
            str = new String(bytes, "UTF-8");
        }
        return str;
    }

    public static void writeString(String string, ObjectOutput out) throws IOException {
        byte[] bytes = null;
        if (string != null) {
            bytes = string.getBytes("UTF-8");
        }
        out.writeObject(bytes);
    }

    public static Long readLong(ObjectInput in, long nullFlag) throws ClassNotFoundException, IOException {
        Long num = null;
        long value = in.readLong();
        if (value != nullFlag) {
            num = new Long(value);
        }
        return num;
    }

    public static void writeLong(Long num, ObjectOutput out, long nullFlag) throws IOException {
        if (num != null) {
            out.writeLong(num.longValue());
        } else {
            out.writeLong(nullFlag);
        }
	}	
}
