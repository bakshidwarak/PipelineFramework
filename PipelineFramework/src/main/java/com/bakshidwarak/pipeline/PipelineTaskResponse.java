package com.bakshidwarak.pipeline;


public class PipelineTaskResponse {
		private Object returnObject;
	private Exception exception;

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public boolean isFailed() {
		return exception != null;
	}

}
