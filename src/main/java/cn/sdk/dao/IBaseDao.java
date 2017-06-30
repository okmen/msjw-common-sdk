package cn.sdk.dao;

/**
 * dao 接口基类
 * @author gaoxigang
 *
 * @param <T>
 */
public interface IBaseDao<T extends GenerallyBean>{
	/**
	 * 根据id逻辑删除t
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新增t
	 * @param t
	 * @return
	 */
	int insert(T t);

	/**
	 * 插入t中有值的数据
	 * @param t
	 * @return
	 */
	int insertSelective(T t);

	/**
	 * 根据主键查询数据
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(Integer id);

	/**
	 * 修改t中有值的数据
	 * @param t
	 * @return
	 */
	int updateByPrimaryKeySelective(T t);

	/**
	 * 修改t
	 * @param t
	 * @return
	 */
	int updateByPrimaryKey(T t);
}
