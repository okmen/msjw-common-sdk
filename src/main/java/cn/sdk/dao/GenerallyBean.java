package cn.sdk.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库对象基类类
 * @author gaoxigang
 *
 */
public class GenerallyBean implements Serializable{
	private static final long serialVersionUID = 1L;
	protected Integer id;
	protected Integer createUserId;
	protected Date createTime;
	protected Integer isDeleted;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public GenerallyBean(Integer createUserId, Date createTime,
			Integer isDeleted) {
		super();
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.isDeleted = isDeleted;
	}
	public GenerallyBean(){}
}
