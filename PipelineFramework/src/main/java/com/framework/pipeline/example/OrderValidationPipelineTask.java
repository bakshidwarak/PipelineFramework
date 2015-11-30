package com.framework.pipeline.example;

import org.springframework.stereotype.Component;

import com.framework.pipeline.ContextType;
import com.framework.pipeline.Depends;
import com.framework.pipeline.ExecutionContext;
import com.framework.pipeline.PipelineTask;

@Component
public class OrderValidationPipelineTask extends PipelineTask {

	@Depends(type = ContextType.REQUEST_OBJ)
	private Order orderInfo;

	@Override
	protected Object handleRequest(ExecutionContext context) throws Exception {
		if (orderInfo == null || orderInfo.getAddress() == null) {
			throw new ProcessingException("Mandatory info not found");
		}
		if (orderInfo.getAddress() != null) {
			// Our services are currently only in CA
			if (!("CA".equals(orderInfo.getAddress().getState()))) {
				throw new ProcessingException(
						"We currently process only CA orders");
			}
		}
		return noOp;
	}

}
