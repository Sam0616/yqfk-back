package com.ly.bigdata.utils;


public class ResponseObj{
	
	private Integer status; //状态码
	private Object obj;   // 存储数据

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public ResponseObj(Integer status, Object obj) {
		super();
		this.status = status;
		this.obj = obj;
	}

	public ResponseObj() {
		super();
	}

	@Override
	public String toString() {
		return "ResponseObj [status=" + status + ", obj=" + obj + "]";
	}
	
	

}
