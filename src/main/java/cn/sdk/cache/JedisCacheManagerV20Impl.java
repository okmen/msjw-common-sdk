package cn.sdk.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Tuple;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.UnsafeInput;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * 缓存管理器redis实现. 对象压缩存储
 * 
 * @author mage
 * 
 * @param <T>
 */
public class JedisCacheManagerV20Impl<T extends Object> implements ICacheManger<T> {
	private final static Log logger = LogFactory.getLog(JedisCacheManagerV20Impl.class);
	private final static int MAX_CACHE_SEC = 30 * 86400;// 30 days
	private final static int MIN_CACHE_SEC = 60;// 测试需要，暂时修改。60; // 30 minites
	private final static int DEFAULT_CACHE_SEC = 86400; // 1 day
	private BilinShardedJedisPool jedisPool;
	private KryoPool pool;
	
	public JedisCacheManagerV20Impl() {
	}
	
	public JedisCacheManagerV20Impl( KryoFactory kryoFactory ) {
		if( kryoFactory != null ){
			pool = new KryoPool.Builder(kryoFactory).softReferences().build();
		}
	}
 
	
	public BilinShardedJedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(BilinShardedJedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	private int getRealCacheTime(int seconds) {
		// 最长只能放30天
		seconds = seconds > MAX_CACHE_SEC ? MAX_CACHE_SEC : seconds;
		// 最短也要30分钟
		return seconds < MIN_CACHE_SEC ? MIN_CACHE_SEC : seconds;
	}

	public boolean setByKryo(String key, Object obj) {
		return setByKryo(key, obj, 0);
	}

	public boolean setByKryo(String key, Object object_, int seconds) {
		if (object_ == null) {
			return false;
		}
		ShardedJedis commonJedis = null;
		byte[] data_ = null;
		boolean success = false;
		try {
			commonJedis = jedisPool.getResource();
			Kryo kryo = new Kryo();
			Output output = new Output( 256, 4096);
			kryo.writeObject(output, object_);
			data_ = output.toBytes();
			output.flush();
			output.close();
			commonJedis.setex(key.getBytes(), getRealCacheTime(seconds), data_);
			success = true;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("setByMsgPack object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return success;
	}

	@Override
	public boolean setByRegisterKryo(String key, Object obj) {
		return setByRegisterKryo(key, obj, 0);
	}
	
	@Override
	public boolean setByRegisterKryo(String key, Object object_, int seconds) {
		if (object_ == null || pool == null ) {
			logger.error(" object=" + object_ + ", setByRegisterKryo don't config kryoPool or params is wrong");
			return false;
		}
		ShardedJedis commonJedis = null;
		Kryo kryo = null;
		byte[] data_ = null;
		boolean success = false;
		try {
			commonJedis = jedisPool.getResource();
			kryo = pool.borrow();
			Output output = new UnsafeOutput( 256, 4096);
			kryo.writeObject(output, object_);
			data_ = output.toBytes();
			output.flush();
			output.close();
			commonJedis.setex(key.getBytes(), getRealCacheTime(seconds), data_);
			success = true;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("setByRegisterKryo object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
			if( kryo != null ){
				pool.release( kryo );	
			}
		}
		
		return success;
	}
	
	
	public Object getByKryo(String key, Class<?> myClass) {
		return getByKryo(key, myClass, 0);
	}

	public Object getByKryo(String key, Class<?> myClass, int seconds) {
		ShardedJedis commonJedis = null;
		Object object_ = null;
		try {
			commonJedis = jedisPool.getResource();
			byte[] data_ = commonJedis.get(key.getBytes());
			if (data_ == null) {
				return null;
			}
			Kryo kryo = new Kryo();
			Input input = new Input(data_);
			object_ = kryo.readObject(input, myClass);
			input.close();
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error(" key=" + key + ", getByMsgPack object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return object_;
	}

	@Override
	public Object getByRegisterKryo(String key, Class<?> myClass) {
		return getByRegisterKryo(key, myClass, 0);
	}
	
	@Override
	public Object getByRegisterKryo(String key, Class<?> myClass, int seconds) {
		if( pool == null ){
			logger.error(" key=" + key + ", getByRegisterKryo don't config kryoPool");
			return null;
		}
		ShardedJedis commonJedis = null;
		Kryo kryo = null;
		Object object_ = null;
		try {
			commonJedis = jedisPool.getResource();
			byte[] data_ = commonJedis.get(key.getBytes());
			if (data_ == null) {
				return null;
			}
			kryo = pool.borrow();
			Input input = new UnsafeInput(data_);
			object_ = kryo.readObject(input, myClass);
			input.close();
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error(" key=" + key + ", getByRegisterKryo object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
			if( kryo != null ){
				pool.release( kryo );
			}
		}
		return object_;
	}
	
	public Map<String, Object> mgetByKryo(Class<?> myClass, String... keys) {
		if (keys == null || keys.length == 0) {
			return null;
		}
		ShardedJedis commonJedis = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			// keys 转 byte
			List<byte[]> byteList = new ArrayList<byte[]>();
			for (String k : keys) {
				byte[] b = k.getBytes("UTF-8");
				byteList.add(b);
			}
			byte[][] byteKeys = byteList.toArray(new byte[byteList.size()][]);

			commonJedis = jedisPool.getResource();
			Map<byte[], byte[]> result = commonJedis.mget(byteKeys);
			if (result == null || result.isEmpty()) {
				return null;
			}

			String key = null;
			Object object_ = null;
			for (byte[] k : result.keySet()) {
				key = new String(k, "UTF-8");
				Kryo kryo = new Kryo();
				Input input = new Input(result.get(k));
				object_ = kryo.readObject(input, myClass);
				input.close();
				map.put(key, object_);
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("keys=" + keys + ",mgetByKryo object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> mgetByRegisterKryo(Class<?> myClass, String... keys) {
		if (keys == null || keys.length == 0 || pool == null ) {
			if( keys != null ){
				for (String s : keys) {
					logger.error(" key=" + keys + ", k=" + s + ", mgetByRegisterKryo don't config kryoPool, or params is wrong");
				}
			}
			logger.error(" keys=" + keys + ", length=" + ( keys == null ? 0 : keys.length  ) + ", mgetByRegisterKryo don't config kryoPool, or params is wrong");
			logger.error(" pool=" + pool + ", mgetByRegisterKryo don't config kryoPool, or params is wrong");
			return null;
		}
		ShardedJedis commonJedis = null;
		Kryo kryo = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			// keys 转 byte
			List<byte[]> byteList = new ArrayList<byte[]>();
			for (String k : keys) {
				byte[] b = k.getBytes("UTF-8");
				byteList.add(b);
			}
			byte[][] byteKeys = byteList.toArray(new byte[byteList.size()][]);

			commonJedis = jedisPool.getResource();
			Map<byte[], byte[]> result = commonJedis.mget(byteKeys);
			if (result == null || result.isEmpty()) {
				return null;
			}

			String key = null;
			Object object_ = null;
			kryo = pool.borrow();
			for (byte[] k : result.keySet()) {
				key = new String(k, "UTF-8");
				Input input = new UnsafeInput(result.get(k));
				object_ = kryo.readObject(input, myClass);
				input.close();
				map.put(key, object_);
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("keys=" + keys + ",mgetByKryo object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
			if( kryo != null ){
				pool.release( kryo );
			}
		}
		return map;
	}
	
	
	/**
	 * @Override public boolean setByMsgPack(String key, Object object_) {
	 *           return setByMsgPack(key,object_,DEFAULT_CACHE_SEC); }
	 * @Override public boolean setByMsgPack(String key,Object object_,int
	 *           seconds){ if (object_ == null) { return false; } ShardedJedis
	 *           commonJedis = null; byte[] data_ = null; boolean success =
	 *           false; try { commonJedis = jedisPool.getResource();
	 *           //messagepack MessagePack msgPack = new MessagePack(); data_ =
	 *           msgPack.write(object_);
	 * 
	 *           commonJedis.setex(key.getBytes(), getRealCacheTime(seconds),
	 *           data_); success = true; } catch (Exception e) {
	 *           jedisPool.returnBrokenResource(commonJedis); commonJedis =
	 *           null; logger.error("setByMsgPack object cache error:", e); }
	 *           finally { if (commonJedis != null) {
	 *           jedisPool.returnResource(commonJedis); } } return success; }
	 * @Override public Object getByMsgPack(String key,Class<?> myClass) {
	 *           return getByMsgPack(key,myClass,0); }
	 * @Override public Object getByMsgPack(String key,Class<?> myClass,int
	 *           seconds) { ShardedJedis commonJedis = null; Object object_ =
	 *           null; try { commonJedis = jedisPool.getResource(); byte[] data_
	 *           = commonJedis.get(key.getBytes()); if (data_ == null) { return
	 *           null; } MessagePack msgPack = new MessagePack(); object_ =
	 *           msgPack.read(data_,myClass); if (seconds > 0) {
	 *           commonJedis.expire(key, getRealCacheTime(seconds)); } } catch
	 *           (Exception e) { jedisPool.returnBrokenResource(commonJedis);
	 *           commonJedis = null;
	 *           logger.error("getByMsgPack object cache error:", e); } finally
	 *           { if (commonJedis != null) {
	 *           jedisPool.returnResource(commonJedis); } } return object_; }
	 */

	/**
	 * 没有设置有效期的默认放置一天
	 */
	public boolean set(String key, T object_) {
		// 默认缓存24小时
		return set(key, object_, DEFAULT_CACHE_SEC);
	}

	public boolean set(String key, T object_, int seconds) {
		if (object_ == null) {
			return false;
		}
		ShardedJedis commonJedis = null;
		byte[] data_ = null;
		boolean success = false;
		try {
			commonJedis = jedisPool.getResource();
			// 建立字节数组输出流
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			// 建立gzip压缩输出流
			GZIPOutputStream gzout = new GZIPOutputStream(o);
			// 建立对象序列化输出流
			ObjectOutputStream out = new ObjectOutputStream(gzout);
			out.writeObject(object_);
			out.flush();
			out.close();
			gzout.close();

			// 返回压缩字节流
			data_ = o.toByteArray();
			o.close();

			commonJedis.setex(key.getBytes(), getRealCacheTime(seconds), data_);
			success = true;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("set object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return success;
	}

	public T get(String key) {
		return get(key, 0);
	}

	@SuppressWarnings("unchecked")
	public T get(String key, int seconds) {
		ShardedJedis commonJedis = null;
		T object_ = null;
		try {
			commonJedis = jedisPool.getResource();
			byte[] data_ = commonJedis.get(key.getBytes());
			if (data_ == null) {
				return null;
			}
			// 建立字节数组输入流
			ByteArrayInputStream i = new ByteArrayInputStream(data_);
			// 建立gzip解压输入流
			GZIPInputStream gzin = new GZIPInputStream(i);
			// 建立对象序列化输入流
			ObjectInputStream in = new ObjectInputStream(gzin);
			// 按制定类型还原对象
			object_ = (T) in.readObject();

			i.close();
			gzin.close();
			in.close();
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("get object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return object_;
	}

	public void del(String key) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.del(key.getBytes());
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("del cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void setint(String key, int initialValue) {
		// 默认缓存24小时
		setint(key, initialValue, DEFAULT_CACHE_SEC);
	}

	public void setint(String key, int initialValue, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.setex(key, getRealCacheTime(seconds), String.valueOf(initialValue));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("set int cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public int getint(String key) {
		ShardedJedis commonJedis = null;
		int number = 0;
		try {
			commonJedis = jedisPool.getResource();
			number = commonJedis.get(key) == null ? 0 : Integer.parseInt(commonJedis.get(key));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("get int cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return number;
	}

	public void addint(String key, int increment, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.incrBy(key, increment);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("add int cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void reduceint(String key, int reduction, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.decrBy(key, reduction);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("reduce int cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void setshort(String key, short initialValue) {
		// 默认缓存24小时
		setshort(key, initialValue, DEFAULT_CACHE_SEC);
	}

	public void setshort(String key, short initialValue, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.setex(key, getRealCacheTime(seconds), String.valueOf(initialValue));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("set short cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public short getshort(String key) {
		ShardedJedis commonJedis = null;
		short number = 0;
		try {
			commonJedis = jedisPool.getResource();
			number = commonJedis.get(key) == null ? 0 : Short.parseShort(commonJedis.get(key));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("get short cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return number;
	}

	public void addshort(String key, short increment, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.incrBy(key, increment);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("add short cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void reduceshort(String key, short reduction, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.decrBy(key, reduction);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("reduce short cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void setlong(String key, long initialValue) {
		// 默认缓存24小时
		setlong(key, initialValue, DEFAULT_CACHE_SEC);
	}

	public void setlong(String key, long initialValue, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.setex(key, getRealCacheTime(seconds), String.valueOf(initialValue));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("set long cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public long getlong(String key) {
		ShardedJedis commonJedis = null;
		long number = 0;
		try {
			commonJedis = jedisPool.getResource();
			number = commonJedis.get(key) == null ? 0 : Long.parseLong(commonJedis.get(key));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("get long cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return number;
	}

	public void addlong(String key, long increment, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.incrBy(key, increment);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("add long cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void reducelong(String key, long reduction, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.decrBy(key, reduction);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("reduce long cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public long zadd(String key, long score, String member, int seconds) {
		long z = -1;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			z = commonJedis.zadd(key, score, member);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zadd cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return z;
	}

	public long zaddByReport(String key, long score, String member) {
		long z = -1;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			z = commonJedis.zadd(key, score, member);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zadd cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return z;
	}

	public long zadd(String key, long score, String member) {
		return zadd(key, score, member, DEFAULT_CACHE_SEC);
	}

	public long zaddMap(String key, Map<String, Double> scoreMembers) {
		return zaddMap(key, scoreMembers, DEFAULT_CACHE_SEC);
	}

	public long zaddMap(String key, Map<String, Double> scoreMembers, int seconds) {
		long z = -1;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			z = commonJedis.zadd(key, scoreMembers);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zadd cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return z;
	}

	public Double zscore(String key, String member) {
		Double score = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			score = commonJedis.zscore(key, member);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zscore cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return score;
	}

	public long zcard(String key) {
		long count = -1;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			count = commonJedis.zcard(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zcard cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return count;
	}

	public long zremrangeByRank(String key, long start, long end) {
		long count = -1;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			count = commonJedis.zremrangeByRank(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zremrangeByRank cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return count;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		Set<Tuple> set = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			set = commonJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zrevrangeByScoreWithScores cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return set;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		Set<Tuple> set = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			set = commonJedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zrevrangeByScoreWithScores cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return set;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		Set<Tuple> set = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			set = commonJedis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zrevrangeByScoreWithScores cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return set;
	}

	public void zrem(String key, String members, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.zrem(key, members);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zrem cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void zrem(String key, String members) {
		zrem(key, members, DEFAULT_CACHE_SEC);
	}

	/**
	 * 缓存管理器hset初始化
	 */
	public void hset(String key, String field, String value) {
		hset(key, field, value, DEFAULT_CACHE_SEC);
	}

	/**
	 * 缓存管理器hset初始化
	 */
	public void hset(String key, String field, String value, int seconds) {
		if (value == null) {
			return;
		}
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.hset(key, field, value);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hset cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	/**
	 * 缓存管理器hgetAll获取hset数据总条数
	 * 
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		Map<String, String> map = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			map = commonJedis.hgetAll(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hgetAll cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return map;
	}

	public List<String> hvalues(String key) {
		List<String> list = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			list = commonJedis.hvals(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hvalues cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> hvaluesObject(String key) {
		List<T> list = null;
		ShardedJedis commonJedis = null;
		T object = null;
		List<byte[]> listByte = null;
		try {
			list = new ArrayList<T>();
			commonJedis = jedisPool.getResource();
			listByte = (List<byte[]>) commonJedis.hvals(key.getBytes());
			ByteArrayInputStream i = null;
			GZIPInputStream gzin = null;
			ObjectInputStream in = null;
			for (byte[] valueItem : listByte) {
				// 建立字节数组输入流
				i = new ByteArrayInputStream(valueItem);
				// 建立gzip解压输入流
				gzin = new GZIPInputStream(i);
				// 建立对象序列化输入流
				in = new ObjectInputStream(gzin);
				// 按制定类型还原对象
				object = (T) in.readObject();
				list.add(object);
			}
			if (i != null && gzin != null && in != null) {
				i.close();
				gzin.close();
				in.close();
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hvaluesObject cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> hvaluesObjectNoZip(String key) {
		List<T> list = null;
		ShardedJedis commonJedis = null;
		T object = null;
		List<byte[]> listByte = null;
		try {
			list = new ArrayList<T>();
			commonJedis = jedisPool.getResource();
			listByte = (List<byte[]>) commonJedis.hvals(key.getBytes());
			ByteArrayInputStream i = null;
			ObjectInputStream in = null;
			for (byte[] valueItem : listByte) {
				// 建立字节数组输入流
				i = new ByteArrayInputStream(valueItem);
				// 建立对象序列化输入流
				in = new ObjectInputStream(i);
				// 按制定类型还原对象
				object = (T) in.readObject();
				list.add(object);
			}
			if (i != null && in != null) {
				i.close();
				in.close();
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hvaluesObject cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return list;
	}

	public List<String> hmget(String key, String... fields) {
		List<String> list = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			list = commonJedis.hmget(key, fields);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hmget cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> hmgetObjects(String key, int seconds, String... fields) {
		if (key == null || fields == null) {
			return null;
		}
		List<T> list = null;
		ShardedJedis commonJedis = null;
		byte[][] byteArray = new byte[fields.length][];
		for (int i = 0; i < fields.length; i++) {
			byteArray[i] = fields[i].getBytes();
		}
		List<byte[]> listByte = null;
		T object = null;
		try {
			commonJedis = jedisPool.getResource();
			listByte = commonJedis.hmget(key.getBytes(), byteArray);
			if (listByte == null || listByte.isEmpty()) {
				return null;
			}
			list = new ArrayList<T>();
			ByteArrayInputStream i = null;
			GZIPInputStream gzin = null;
			ObjectInputStream in = null;
			for (byte[] byteItem : listByte) {
				if (byteItem == null || byteItem.length == 0) {
					continue;
				}
				// 建立字节数组输入流
				i = new ByteArrayInputStream(byteItem);
				// 建立gzip解压输入流
				gzin = new GZIPInputStream(i);
				// 建立对象序列化输入流
				in = new ObjectInputStream(gzin);
				// 按制定类型还原对象
				object = (T) in.readObject();
				list.add(object);
			}
			if (i != null && gzin != null && in != null) {
				i.close();
				gzin.close();
				in.close();
			}
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hmgetObjects cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> hmgetObjectsNoZip(String key, String... fields) {
		if (key == null || fields == null) {
			return null;
		}
		List<T> list = null;
		ShardedJedis commonJedis = null;
		byte[][] byteArray = new byte[fields.length][];
		for (int i = 0; i < fields.length; i++) {
			byteArray[i] = fields[i].getBytes();
		}
		List<byte[]> listByte = null;
		T object = null;
		try {
			commonJedis = jedisPool.getResource();
			listByte = commonJedis.hmget(key.getBytes(), byteArray);
			if (listByte == null || listByte.isEmpty()) {
				return null;
			}
			list = new ArrayList<T>();
			ByteArrayInputStream i = null;
			ObjectInputStream in = null;
			for (byte[] byteItem : listByte) {
				if (byteItem == null || byteItem.length == 0) {
					continue;
				}
				// 建立字节数组输入流
				i = new ByteArrayInputStream(byteItem);
				// 建立对象序列化输入流
				in = new ObjectInputStream(i);
				// 按制定类型还原对象
				object = (T) in.readObject();
				list.add(object);
			}
			if (i != null && in != null) {
				i.close();
				in.close();
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hmgetObjectsNoZip cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return list;
	}

	public void hmset(String key, Map<String, String> map, int seconds) {
		if (map == null || map.isEmpty()) {
			return;
		}
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.hmset(key, map);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hmset cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	/**
	 * 缓存管理器hincrBy自增hset某一field 默认有效期
	 */
	public void hincrBy(String key, String field, int value) {
		hincrBy(key, field, value, DEFAULT_CACHE_SEC);
	}

	/**
	 * 缓存管理器hincrBy自增hset某一field 设置有效期
	 */
	public void hincrBy(String key, String field, int value, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.hincrBy(key, field, value);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hincrBy cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	/**
	 * 缓存管理器hincrBy自增hset某一field 默认有效期 带有返回值
	 */
	public long hincrByResult(String key, String field, int value) {
		return hincrByResult(key, field, value, DEFAULT_CACHE_SEC);
	}

	/**
	 * 缓存管理器hincrBy自增hset某一field 设置有效期 带有返回结果
	 */
	public long hincrByResult(String key, String field, int value, int seconds) {
		ShardedJedis commonJedis = null;
		long result = 0;
		try {
			commonJedis = jedisPool.getResource();
			result = commonJedis.hincrBy(key, field, value);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hincrBy cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return result;
	}

	/**
	 * 缓存管理器hdel删除hset某一field
	 */
	public void hdel(String key, String field, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.hdel(key, field);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hdel cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void hdels(String key, int seconds, String... field) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.hdel(key, field);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hdel cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void hdel(String key, String field) {
		hdel(key, field, DEFAULT_CACHE_SEC);
	}

	public void hdels(String key, String... field) {
		hdels(key, DEFAULT_CACHE_SEC, field);
	}

	public long hlen(String key) {
		long count = -1;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			count = commonJedis.hlen(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hlen cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return count;
	}

	public String hget(String key, String field) {
		String str = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			str = commonJedis.hget(key, field);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hget cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return str;
	}

	public boolean hexists(String key, String field) {
		boolean str = false;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			str = commonJedis.hexists(key, field);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hexists cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return str;
	}

	public boolean hexistsKeyField(String key, String field, int seconds) {
		boolean str = false;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			str = commonJedis.hexists(key, field);
			if (str) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hexists cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return str;
	}

	public long lpush(String key, String field, int seconds) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.lpush(key, field);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("lpush cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public long lpush(String key, String[] fields, int seconds) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.lpush(key, fields);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("lpush fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public long rpush(String key, String[] fields, int seconds) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.rpush(key, fields);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("rpush fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public long rpush(String key, String field, int seconds) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.rpush(key, field);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("rpush cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public String lpop(String key, int seconds) {
		String str = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			str = commonJedis.lpop(key);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("lpop cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return str;
	}

	public String lpop(String key) {
		String str = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			str = commonJedis.lpop(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("lpop cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return str;
	}

	public String rpop(String key, int seconds) {
		String str = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			str = commonJedis.rpop(key);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("rpop cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return str;
	}

	public long lrem(String key, int count, String filed, int seconds) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.lrem(key, count, filed);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("lrem cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public long llen(String key) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			return commonJedis.llen(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("llen cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return 0;
	}

	public List<String> lrange(String key, long start, long end) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			return commonJedis.lrange(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("lrange cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return null;
	}

	public long sadd(String key, String field, int seconds) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.sadd(key, field);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("sadd cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public long sadd(String key, String[] fields, int seconds) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.sadd(key, fields);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("sadd fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public long srem(String key, String member, int seconds) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.srem(key, member);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("srem fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public String spop(String key, int seconds) {
		String r = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.spop(key);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("spop fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public long scard(String key) {
		long r = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.scard(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("srem fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public boolean sismember(String key, String member) {
		boolean r = false;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.sismember(key, member);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("sismember fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public Set<String> smembers(String key) {
		Set<String> r = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.smembers(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("smembers fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	/**
	 * 设置有效期
	 */
	public void expire(String key, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hincrBy cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	/**
	 * 判断key是否存在
	 */
	public boolean exists(String key) {
		boolean c = false;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			c = commonJedis.exists(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hincrBy cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return c;
	}

	public int getMaxCacheSec() {
		return MAX_CACHE_SEC;
	}

	public int getMinCacheSec() {
		return MIN_CACHE_SEC;
	}

	public int getDefaultCacheSec() {
		return DEFAULT_CACHE_SEC;
	}

	public boolean hsetObject(String key, String field, T object, int seconds) {
		if (object == null) {
			return false;
		}
		ShardedJedis commonJedis = null;
		byte[] data_ = null;
		boolean success = false;
		try {
			commonJedis = jedisPool.getResource();
			// 建立字节数组输出流
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			// 建立gzip压缩输出流
			GZIPOutputStream gzout = new GZIPOutputStream(o);
			// 建立对象序列化输出流
			ObjectOutputStream out = new ObjectOutputStream(gzout);
			out.writeObject(object);
			out.flush();
			out.close();
			gzout.close();
			// 返回压缩字节流
			data_ = o.toByteArray();
			o.close();
			commonJedis.hset(key.getBytes(), field.getBytes(), data_);
			commonJedis.expire(key, getRealCacheTime(seconds));
			success = true;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("set object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return success;
	}

	public boolean hsetObjectNoGzip(String key, String field, T object, int seconds) {
		if (object == null) {
			return false;
		}
		ShardedJedis commonJedis = null;
		byte[] data_ = null;
		boolean success = false;
		try {
			commonJedis = jedisPool.getResource();
			// 建立字节数组输出流
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			// 建立对象序列化输出流
			ObjectOutputStream out = new ObjectOutputStream(o);
			out.writeObject(object);
			out.flush();
			out.close();
			// 返回压缩字节流
			data_ = o.toByteArray();
			o.close();
			commonJedis.hset(key.getBytes(), field.getBytes(), data_);
			commonJedis.expire(key, getRealCacheTime(seconds));
			success = true;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hsetObjectNoGzip cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	public T hgetObject(String key, String field, int seconds) {
		ShardedJedis commonJedis = null;
		T object_ = null;
		try {
			commonJedis = jedisPool.getResource();
			byte[] data_ = commonJedis.hget(key.getBytes(), field.getBytes());
			if (data_ == null) {
				return null;
			}
			// 建立字节数组输入流
			ByteArrayInputStream i = new ByteArrayInputStream(data_);
			// 建立gzip解压输入流
			GZIPInputStream gzin = new GZIPInputStream(i);
			// 建立对象序列化输入流
			ObjectInputStream in = new ObjectInputStream(gzin);
			// 按制定类型还原对象
			object_ = (T) in.readObject();
			i.close();
			gzin.close();
			in.close();
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("get object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return object_;
	}

	@SuppressWarnings("unchecked")
	public T hgetObjectNoGzip(String key, String field, int seconds) {
		ShardedJedis commonJedis = null;
		T object_ = null;
		try {
			commonJedis = jedisPool.getResource();
			byte[] data_ = commonJedis.hget(key.getBytes(), field.getBytes());
			if (data_ == null) {
				return null;
			}
			// 建立字节数组输入流
			ByteArrayInputStream i = new ByteArrayInputStream(data_);
			// 建立对象序列化输入流
			ObjectInputStream in = new ObjectInputStream(i);
			// 按制定类型还原对象
			object_ = (T) in.readObject();
			i.close();
			in.close();
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("get object cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return object_;
	}

	@SuppressWarnings("unchecked")
	public Map<String, T> hgetAllObject(String key, int seconds) {
		if (key == null) {
			return null;
		}
		Map<byte[], byte[]> hMap = null;
		ShardedJedis commonJedis = null;
		Map<String, T> returnMap = null;
		T object = null;
		try {
			commonJedis = jedisPool.getResource();
			hMap = commonJedis.hgetAll(key.getBytes());
			if (hMap == null) {
				return null;
			}
			returnMap = new HashMap<String, T>();
			Set<byte[]> keySet = hMap.keySet();
			ByteArrayInputStream i = null;
			GZIPInputStream gzin = null;
			ObjectInputStream in = null;
			for (Iterator<byte[]> it = keySet.iterator(); it.hasNext();) {
				byte[] keyItem = it.next();
				byte[] valueItem = hMap.get(keyItem);
				// 建立字节数组输入流
				i = new ByteArrayInputStream(valueItem);
				// 建立gzip解压输入流
				gzin = new GZIPInputStream(i);
				// 建立对象序列化输入流
				in = new ObjectInputStream(gzin);
				// 按制定类型还原对象
				object = (T) in.readObject();
				returnMap.put(new String(keyItem), object);
			}
			if (i != null && gzin != null && in != null) {
				i.close();
				gzin.close();
				in.close();
			}
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hgetAll cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return returnMap;
	}

	public long lpush(byte[] key, byte[] strings, int seconds) {
		long id = 0;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			id = commonJedis.lpush(key, strings);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hincrBy cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return id;
	}

	public byte[] rpop(byte[] key, int seconds) {
		byte[] obj = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			obj = commonJedis.rpop(key);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hincrBy cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return obj;
	}

	public Set<String> zrevrange(String key, long start, long end) {
		return zrevrange(key, start, end, 0);
	}

	public Set<String> zrevrange(String key, long start, long end, int seconds) {
		Set<String> set = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			set = commonJedis.zrevrange(key, start, end);
			if (seconds != 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zrevrange cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return set;
	}

	public Set<String> zrange(String key, long start, long end) {
		return zrange(key, start, end, 0);
	}

	public Set<String> zrange(String key, long start, long end, int seconds) {
		Set<String> set = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			set = commonJedis.zrange(key, start, end);
			if (seconds != 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zrange cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return set;
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return zrangeWithScores(key, start, end, 0);
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end, int seconds) {
		Set<Tuple> set = null;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			set = commonJedis.zrangeWithScores(key, start, end);
			if (seconds != 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zrange cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return set;
	}

	public long zcount(String key, long min, long max) {
		ShardedJedis commonJedis = null;
		long length = 0;
		try {
			commonJedis = jedisPool.getResource();
			length = commonJedis.zcount(key, min, max);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("zcount cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return length;
	}

	public void zremrangeByScore(String key, long start, long end) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("del cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public void zincrby(String key, String member, long score, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.zincrby(key, score, member);
			if (seconds != 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("del cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public long zrevrank(String key, String member) {
		ShardedJedis commonJedis = null;
		long index = -1;
		try {
			commonJedis = jedisPool.getResource();
			Long indexL = commonJedis.zrevrank(key, member);
			if (indexL != null) {
				index = indexL.longValue();
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("del cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return index;
	}

	public int ttl(String key) {
		// TODO Auto-generated method stub
		ShardedJedis commonJedis = null;
		int number = 0;
		try {
			commonJedis = jedisPool.getResource();
			number = commonJedis.get(key) == null ? 0 : Integer.parseInt(commonJedis.ttl(key) + "");
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("get ttl cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return number;
	}

	/**
	 * 反垃圾用户有效期设置(时间可变的) 设置有效期
	 */
	public void expireSpamUser(String key, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.expire(key, seconds);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hincrBy cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	/**
	 * 判断member是否存在zset中
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean zismember(String key, String member) {
		boolean r = false;
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			r = commonJedis.zrank(key, member) != null ? true : false;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("sismember fields cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return r;
	}

	public long hkeys(String key) {
		ShardedJedis commonJedis = null;
		long length = 0;
		try {
			commonJedis = jedisPool.getResource();
			length = commonJedis.hlen(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hincrBy cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return length;
	}

	public Set<String> hGetKeys(String key) {
		ShardedJedis commonJedis = null;
		Set<String> set = null;
		try {
			commonJedis = jedisPool.getResource();
			set = commonJedis.hkeys(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hGetKeys cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return set;
	}

	public boolean hsetObjectList(String key, Map<String, T> map, int seconds) {
		if (map == null || map.size() == 0) {
			return false;
		}
		ShardedJedis commonJedis = null;
		byte data_[] = null;
		try {
			commonJedis = jedisPool.getResource();
			Set<String> mapKeys = map.keySet();
			Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
			for (String mapKey : mapKeys) {
				// 建立字节数组输出流
				ByteArrayOutputStream o = new ByteArrayOutputStream();
				T object = map.get(mapKey);
				// 建立gzip压缩输出流
				GZIPOutputStream gzout = new GZIPOutputStream(o);
				// 建立对象序列化输出流
				ObjectOutputStream out = new ObjectOutputStream(gzout);
				out.writeObject(object);
				out.flush();
				out.close();
				gzout.close();
				// 返回压缩字节流
				data_ = o.toByteArray();
				o.close();
				byteMap.put(mapKey.getBytes(), data_);
			}
			commonJedis.hmset(key.getBytes(), byteMap);
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hsetObjectList cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return true;
	}

	public boolean hsetObjectListNoGzip(String key, Map<String, T> map, int seconds) {
		if (map == null || map.size() == 0) {
			return false;
		}
		ShardedJedis commonJedis = null;
		byte data_[] = null;
		try {
			commonJedis = jedisPool.getResource();
			Set<String> mapKeys = map.keySet();
			Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
			for (String mapKey : mapKeys) {
				// 建立字节数组输出流
				ByteArrayOutputStream o = new ByteArrayOutputStream();
				T object = map.get(mapKey);
				// 建立对象序列化输出流
				ObjectOutputStream out = new ObjectOutputStream(o);
				out.writeObject(object);
				out.flush();
				out.close();
				// 返回压缩字节流
				data_ = o.toByteArray();
				o.close();
				byteMap.put(mapKey.getBytes(), data_);
			}
			commonJedis.hmset(key.getBytes(), byteMap);
			if (seconds > 0) {
				commonJedis.expire(key, getRealCacheTime(seconds));
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("hsetObjectListNoGzip cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return true;
	}

	public long ttl_rest(String key) {
		ShardedJedis commonJedis = null;
		long len = 0;
		try {
			commonJedis = jedisPool.getResource();
			len = commonJedis.ttl(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("get ttl cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return len;
	}

	/***
	 * 获取String结构的value
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		ShardedJedis commonJedis = null;
		String value = null;
		try {
			commonJedis = jedisPool.getResource();
			value = commonJedis.get(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("getString cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return value;
	}

	/***
	 * 设置String结构的value, 默认过期时间一天
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void setString(String key, String value) {
		setString(key, value, DEFAULT_CACHE_SEC);
	}

	/***
	 * 设置String结构的value
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public void setString(String key, String value, int seconds) {
		ShardedJedis commonJedis = null;
		try {
			commonJedis = jedisPool.getResource();
			commonJedis.set(key, value);
			commonJedis.expire(key, getRealCacheTime(seconds));
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("setString cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
	}

	public String sRandMember(String key) {
		ShardedJedis commonJedis = null;
		String result = null;
		try {
			commonJedis = jedisPool.getResource();
			result = commonJedis.srandmember(key);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(commonJedis);
			commonJedis = null;
			logger.error("sRandMember cache error:", e);
		} finally {
			if (commonJedis != null) {
				jedisPool.returnResource(commonJedis);
			}
		}
		return result;
	}

    @Override
    public boolean setnx(String key, String value) {
        ShardedJedis commonJedis = null;
        Long result = null;
        try {
            commonJedis = jedisPool.getResource();
            result = commonJedis.setnx(key, value);
            
        } catch (Exception e) {
            jedisPool.returnBrokenResource(commonJedis);
            commonJedis = null;
            logger.error("setnx cache error:", e);
        } finally {
            if (commonJedis != null) {
                jedisPool.returnResource(commonJedis);
            }
        }
        if(result != null && result == 1)
            return true;
        else
            return false;
    }
}
