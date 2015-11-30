package com.framework.pipeline.example;

import org.springframework.stereotype.Component;

import com.framework.pipeline.Depends;
import com.framework.pipeline.ExecutionContext;
import com.framework.pipeline.PipelineTask;

@Component
public class TransformResponse extends PipelineTask {

	@Depends(task = AddressValidationTask.class)
	private long addressId;

	@Depends(task = PersistOrderPipelineTask.class)
	private String orderId;

	@Override
	protected Object handleRequest(ExecutionContext context) throws Exception {
		return returnProcessedOrder(addressId, orderId);
	}

	private ProcessedOrder returnProcessedOrder(long addressId, String orderId) {
		// Transformation logic goes here
		return new ProcessedOrder();
	}

}
