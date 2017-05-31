package cn.sdk.bean;

public class DownValue {
    private Integer value;
     
    private String text;
     
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
