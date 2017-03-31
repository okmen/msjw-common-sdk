package cn.sdk.serialization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.UnsafeInput;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

public class KryoUtil<T extends Object> implements ISerializeManager<T>{
	private static final int OUTPUT_BUFFER_SIZE = 1024 * 8; // 8k 
	private final static Log logger = LogFactory.getLog( KryoUtil.class );
	private KryoFactory factory = new KryoFactory() {
		@Override
		public Kryo create() {
			// TODO Auto-generated method stub
			return new Kryo();
		}
	};
	
	private KryoPool pool;
	
	public KryoUtil() {
		pool = new KryoPool.Builder( factory ).softReferences().build();
		logger.debug("pool=" + pool + ",factory=" + factory );
	}
	
	public KryoUtil( KryoFactory kryoFactory ) {
		if( kryoFactory != null ){
			this.factory = kryoFactory;
		}
		pool = new KryoPool.Builder( factory ).softReferences().build();
		logger.debug("pool=" + pool + ",factory=" + factory );
	}
	
	@Override
	public byte[] serialize( T obj ) throws Exception {
		Kryo kryo = null;
		byte[] data = null; 
		try {
			kryo = pool.borrow();
			byte[] out = new byte[ OUTPUT_BUFFER_SIZE ];
			Output kryoOut = new UnsafeOutput(out);
			kryo.writeObject(kryoOut, obj);
			kryoOut.flush();
			data = kryoOut.toBytes();
			kryoOut.close();
		} catch (Exception e) {
			logger.error("serialize, error, obj=" + obj , e);
			throw e;
		} finally{
			if( kryo != null ){
				pool.release( kryo );
			}
		}
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T deserialize( byte[] data, Class<?> myClass ) throws Exception{
		Kryo kryo = null;
		T obj = null;
		try {
			kryo = pool.borrow();
			Input kryoIn =  new UnsafeInput( data );
			obj = (T) kryo.readObject(kryoIn, myClass);
			kryoIn.close();	
		} catch (Exception e) {
			logger.error("deserialize, error, obj=" + obj , e);
			throw e;
		} finally{
			if( kryo != null ){
				pool.release( kryo );
			}
		}
		return obj;
	}

}