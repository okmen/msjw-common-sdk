package cn.sdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;


/**
 * @author gangtaoyu
 * 20150413
 */
public class SerializationUtil {
	public static byte[] getBytes(Map<String, Object> map) {
		byte[] bytes = null;
		
		Kryo kryo = new Kryo();
		Output output = new Output(600, 4096);
		kryo.writeObject(output, map);
		bytes = output.toBytes();
		output.flush();
        output.close();
		return bytes;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(byte[] bytes) {
		Map<String, Object> map = null;
		
		if (bytes == null) {
			return null;
		}
		Kryo kryo = new Kryo();
		Input input = new Input(bytes);
		map = kryo.readObject(input, HashMap.class);
		input.close();
		
		return map;
	}
	
	
	
	
	public static byte[] getBytes0(Map<String, Object> map) {
		byte[] bytes = null;
		// 建立字节数组输出流
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		try {
			// 建立对象序列化输出流
			ObjectOutputStream out = new ObjectOutputStream(o);
			out.writeObject(map);
			bytes = o.toByteArray();
			out.flush();
			out.close();
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bytes;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap0(byte[] bytes) {
		Map<String, Object> map = null;
		// 建立字节数组输入流
		ByteArrayInputStream i = new ByteArrayInputStream(bytes);
		try {
			// 建立对象序列化输入流
			ObjectInputStream in = new ObjectInputStream(i);
			// 按制定类型还原对象
			map = (Map<String, Object>) in.readObject();
			in.close();
			i.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}