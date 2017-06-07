package cn.sdk.bean;

import java.io.Serializable;

public class DownValue<T> implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8945500954614362444L;

	private T value;
     
    private String text;
    
     
	public DownValue() {
		super();
	}
	public DownValue(T value, String text) {
		super();
		this.value = value;
		this.text = text;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
     
}
