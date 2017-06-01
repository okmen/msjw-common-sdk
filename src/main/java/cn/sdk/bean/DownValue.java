package cn.sdk.bean;

import java.io.Serializable;

public class DownValue implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8945500954614362444L;

	private Integer value;
     
    private String text;
    
     
	public DownValue() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DownValue(Integer value, String text) {
		super();
		this.value = value;
		this.text = text;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
     
}
