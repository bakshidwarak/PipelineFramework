package com.bakshidwarak.pipeline;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Depends {

	Class<? extends PipelineTask> task() default PipelineTask.class;

	ContextType type() default ContextType.TASK_OUTCOME;

}
