package cn.sdk.dao;

import cn.sdk.dao.mapper.BaseMapper;

/**
 * dao层基类
 * @author gaoxigang
 *
 * @param <T>
 */
public class BaseDao<T extends GenerallyBean> implements IBaseDao<T>{
	private BaseMapper<T> baseMapper;
	public void setBaseMapper(BaseMapper<T> baseMapper) {
		this.baseMapper = baseMapper;
	}
	/**
	 * 根据id逻辑删除t
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(Integer id){
		return baseMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 新增t
	 * @param t
	 * @return
	 */
	public int insert(T t){
		return baseMapper.insert(t);
	}

	/**
	 * 插入t中有值的数据
	 * @param t
	 * @return
	 */
	public int insertSelective(T t){
		return baseMapper.insertSelective(t);
	}

	/**
	 * 根据主键查询数据
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(Integer id){
		return baseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 修改t中有值的数据
	 * @param t
	 * @return
	 */
	public int updateByPrimaryKeySelective(T t){
		return baseMapper.updateByPrimaryKeySelective(t);
	}

	/**
	 * 修改t
	 * @param t
	 * @return
	 */
	public int updateByPrimaryKey(T t){
		return baseMapper.updateByPrimaryKey(t);
	}
}
