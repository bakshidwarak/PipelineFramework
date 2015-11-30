package com.framework.pipeline.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.framework.pipeline.Depends;
import com.framework.pipeline.ExecutionContext;
import com.framework.pipeline.PipelineTask;

@Component
public class FeasibilityCheckPipelineTask extends PipelineTask {

	@Autowired
	FeasibilityService feasibilityService;

	@Depends(task = AddressValidationTask.class)
	private Long addressId;

	@Override
	protected Object handleRequest(ExecutionContext context) throws Exception {

		FeasibilityInfo feasibiliyInfo = null;
		try {
			feasibiliyInfo = feasibilityService.checkFeasibility(addressId);
		} catch (FeasibilityException e) {
			// Log the error
			throw new ProcessingException("Order cannot be fulfilled");
		}
		return feasibiliyInfo;

	}

}
