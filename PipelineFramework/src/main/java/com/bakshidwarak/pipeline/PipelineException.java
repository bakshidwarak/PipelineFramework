package com.bakshidwarak.pipeline;

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
