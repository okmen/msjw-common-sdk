package cn.sdk.serialization;


public interface ISerializeManager<T extends Object> {
	/**
	 * 序列号对象
	 * @param obj
	 * @return
	 */
	public byte[] serialize( T obj ) throws Exception;
	
	/**
	 * 反序列化对象
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public T deserialize( byte[] data, Class<?> myClass ) throws Exception ;
}
