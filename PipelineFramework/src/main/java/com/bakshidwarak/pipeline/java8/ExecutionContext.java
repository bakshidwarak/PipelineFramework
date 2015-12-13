package com.bakshidwarak.pipeline.java8;

import java.util.HashMap;

public class ExecutionContext {
	private HashMap<Class<? extends PipelineTask>, PipelineTaskResponse> outcomeMap = new HashMap<>();
	private Object lastTaskOuput;

	private Object rootRequest;

	public ExecutionContext(Object requestObj) {
		this.rootRequest = requestObj;
	}

	public void addOutcome(Class<? extends PipelineTask> task,
			PipelineTaskResponse response) {
		if (!(response.getReturnObject() instanceof NoOp)) {
			lastTaskOuput = response.getReturnObject();
		}
		outcomeMap.put(task, response);
	}

	public PipelineTaskResponse getResponse(Class<? extends PipelineTask> task)
			throws PipelineException {
		PipelineTaskResponse chainTaskResponse = outcomeMap.get(task);
		if (chainTaskResponse == null)
			throw new PipelineException(new Exception(
					"The Task has not executed yet"));
		return chainTaskResponse;
	}

	public Object getRootRequest() {
		return rootRequest;
	}

	public void setRootRequest(Object rootRequest) {
		this.rootRequest = rootRequest;
	}

	public Object getTaskOutcome(Class<? extends PipelineTask> task)
			throws PipelineException {
		PipelineTaskResponse response = getResponse(task);
		if (response.getException() != null)
			throw new PipelineException(response.getException());
		else
			return response.getReturnObject();
	}

	public Object getLastTaskOutput() {
		return lastTaskOuput;
	}

}
