package com.framework.pipeline;

import java.lang.reflect.Field;

public abstract class PipelineTask {
	public NoOp noOp = new NoOp();

	protected abstract Object handleRequest(ExecutionContext context)
			throws Exception;

	public Object execute(ExecutionContext context) throws Exception {
		// Get all the fields whose annotation is Depends
		injectDependentOutcomes(context);

		// Get the type of the depends

		// Get the outcome using field type from context
		return this.handleRequest(context);
	}

	private void injectDependentOutcomes(ExecutionContext context)
			throws PipelineException, IllegalAccessException {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Depends depends = field.getAnnotation(Depends.class);
			if (depends != null) {
				field.setAccessible(true);
				switch (depends.type()) {
				case REQUEST_OBJ:
					field.set(this, context.getRootRequest());
					break;
				case TASK_OUTCOME:
					checkForValidTask(depends);
					Object taskOutcome=context.getTaskOutcome(depends.task());
					field.set(this, taskOutcome);
					break;
				default:
					break;

				}

			}
		}
	}

	private void checkForValidTask(Depends depends) {
		if(PipelineTask.class.equals(depends.task())){
			throw new RuntimeException("Task required for TaskOutcome type");
		}
	}

}
