package com.bakshidwarak.pipeline;


public interface PipelineControllerTaskAppender {
	public PipelineControllerTaskAppender runFirst(Class<? extends PipelineTask> task);
	public PipelineControllerTaskAppender thenRun(Class<? extends PipelineTask> task);
	
}
