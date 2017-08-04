package cn.sdk.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sdk.util.FiledUtil;
import cn.sdk.util.PropertyUtils;
/**
 * 数据分组工具
 * @author gaoxigang
 *
 * @param <K> 分组key值类型 也就是返回map类型
 * @param <V> 返回值map:value 中  list 的类型
 */
public class CollectionUtil<K,V>{
	/**
	 * 根据propertyName 分组
	 * @param <A>
	 * @param list 数据源
	 * @param propertyName 属性名
	 * @param clazz 返回值list 的 class
	 * @return 
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public Map<K,List<V>> groupByProperty(List list,String propertyName,Class<V> clazz) throws Exception{
		Map<K, List<V>> map = new HashMap<K, List<V>>();
		if(null == list || 0 == list.size()) return map; 
		for (Object t : list) {
			V v = (V)newClazz(clazz);
			PropertyUtils.copyProperties(v, t);
			//分组依据值
			K k = (K)FiledUtil.getProperty(t, propertyName);
			
			//如果map包含单位id 
			if(map.containsKey(k)){
				map.get(k).add(v);
				continue;
			}
			
			//第一次添加到map中  创建新list 并将用户对象存入
			List<V> tempList = new ArrayList<V>();
			tempList.add(v);
			map.put(k, tempList);
		}
		return map;
	}
	
	/**
	 * list 转换成map
	 * @param list
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public Map<K, V> listToMap(List<V> list,String propertyName) throws Exception{
		Map<K, V> map = new HashMap<>();
		if(null == list || 0 == list.size()) return map; 
		for (V v : list) {
			K k = (K)FiledUtil.getProperty(v, propertyName);
			if(null != k && !"null".equals(k)){
				map.put(k, v);
			}
		}
		return map;
	}
	
	
	/**
	 * 创建对象实例
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static <V> V newClazz(Class<V> clazz)
			throws InstantiationException, IllegalAccessException {
		V v = clazz.newInstance();
		return v;
	}
}
