package com.bakshidwarak.pipeline.example;

import org.springframework.stereotype.Component;

import com.bakshidwarak.pipeline.ContextType;
import com.bakshidwarak.pipeline.Depends;
import com.bakshidwarak.pipeline.ExecutionContext;
import com.bakshidwarak.pipeline.PipelineTask;

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
