package com.framework.pipeline.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.framework.pipeline.PipelineController;
import com.framework.pipeline.PipelineException;

@Component
public class OrderProcessorPipeline {

	@Autowired
	PipelineController<Order, ProcessedOrder> orderProcessorPipeline;

	public ProcessedOrder processOrder(Order orderInfo) throws Exception {
		// Validate Order information

		orderProcessorPipeline.runFirst(OrderValidationPipelineTask.class)
				.thenRun(AddressValidationTask.class)
				.thenRun(FeasibilityCheckPipelineTask.class)
				.thenRun(PersistOrderPipelineTask.class)
				.thenRun(SendNotificationPipelineTask.class)
				.thenRun(TransformResponse.class);

		try {
			return orderProcessorPipeline.process(orderInfo);
		} catch (PipelineException e) {
			throw e.getMainException();
		}

	}

}
