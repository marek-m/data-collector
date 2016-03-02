package org.datacollector.db.model;

public class ResponseModel<M> {

	private boolean success;
	private M content;
	private String error;
	
	public ResponseModel(boolean success, M content, String error) {
		this.content = content;
		this.success = success;
		this.error = error;
	}
	public ResponseModel() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public M getContent() {
		return content;
	}

	public void setContent(M content) {
		this.content = content;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	
}
