package com.bakshidwarak.pipeline.java8;

import java.lang.reflect.Field;

public interface PipelineTask {
	public NoOp noOp = new NoOp();

	public abstract Object handleRequest(ExecutionContext context)
			throws Exception;

	public default Object execute(ExecutionContext context) throws Exception {
		// Get all the fields whose annotation is Depends

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
					if (PipelineTask.class.equals(depends.task())) {
						throw new RuntimeException(
								"Task required for TaskOutcome type");
					}
					Object taskOutcome = context.getTaskOutcome(depends.task());
					field.set(this, taskOutcome);
					break;
				default:
					break;

				}

			}
		}

		// Get the type of the depends

		// Get the outcome using field type from context
		return this.handleRequest(context);
	}

}
