package com.bakshidwarak.pipeline;

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PipelineController<RequestType, ResponseType> implements
		PipelineControllerTaskAppender {
	private LinkedHashSet<Class<? extends PipelineTask>> tasksToExecute = new LinkedHashSet<>();
	private ExecutionContext context;

	@Autowired
	private BeanFactory beanFactory;

	public PipelineControllerTaskAppender runFirst(Class<? extends PipelineTask> task) {
		tasksToExecute.add(task);
		return this;
	}

	public PipelineControllerTaskAppender thenRun(Class<? extends PipelineTask> task) {
		tasksToExecute.add(task);
		return this;
	}

	@SuppressWarnings("unchecked")
	public ResponseType process(RequestType request) throws PipelineException {
		ResponseType returnObject = null;
		this.context = new ExecutionContext(request);
		Iterator<Class<? extends PipelineTask>> itr = tasksToExecute.iterator();
		PipelineTask task = null;
		while (itr.hasNext()) {
			PipelineTaskResponse response = new PipelineTaskResponse();
			task = beanFactory.getBean(itr.next());
			Object returnedObj;
			try {
				returnedObj = task.execute(context);
				response.setReturnObject(returnedObj);
			} catch (Exception e) {
				e.printStackTrace();
				response.setException(e);
				throw new PipelineException(e);
			}

			context.addOutcome(task.getClass(), response);
		}
		
			returnObject = (ResponseType) context.getLastTaskOutput();
			tasksToExecute.clear();

		return returnObject;
	}
}
