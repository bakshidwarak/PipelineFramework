package com.framework.pipeline.java8;

@SuppressWarnings("serial")
public class PipelineException extends Exception {
	
	
	public PipelineException(String message){
		super(message);
	}

	public PipelineException(Exception exception) {
		super(exception);
	}
	public Exception getMainException() {
		return (Exception) getCause();
	}
	

}
