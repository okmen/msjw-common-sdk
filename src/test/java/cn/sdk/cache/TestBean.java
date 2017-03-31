package cn.sdk.cache;

import java.io.Serializable;
import java.util.List;

public class TestBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5378667582389594607L;
	private String name;
	private List<String> nameList;
	private SexEnum sex;

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

}
