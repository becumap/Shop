package shop.dev.common;

import java.io.Serializable;

public class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object data;
	private String code;
	private String message;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
