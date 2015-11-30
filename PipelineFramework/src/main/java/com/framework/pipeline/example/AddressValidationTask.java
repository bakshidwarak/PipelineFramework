package com.framework.pipeline.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.framework.pipeline.ContextType;
import com.framework.pipeline.Depends;
import com.framework.pipeline.ExecutionContext;
import com.framework.pipeline.PipelineTask;

@Component
public class AddressValidationTask extends PipelineTask {

	@Autowired
	AddressValidatorService addressValidator;

	@Depends(type = ContextType.REQUEST_OBJ)
	private Order orderInfo;

	@Override
	protected Object handleRequest(ExecutionContext context) throws Exception {

		long addressId = addressValidator.validateAddress(orderInfo
				.getAddress());
		if (addressId == -1) {
			throw new InvalidAddressException();
		}
		return addressId;
	}

}
