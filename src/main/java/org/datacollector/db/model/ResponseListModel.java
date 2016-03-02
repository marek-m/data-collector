package org.datacollector.db.model;

import java.util.List;

public class ResponseListModel<L extends List> {

	private boolean success;
	private String error;
	private L list;
	private int total;
	
	public ResponseListModel(boolean success, L list, String error) {
		this.list = list;
		this.success = success;
		this.error = error;
		if(list != null)
			this.total = list.size();
	}
	public ResponseListModel() {
		super();
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public L getList() {
		return list;
	}
	public void setList(L list) {
		this.list = list;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	
}
