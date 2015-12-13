package com.bakshidwarak.pipeline.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bakshidwarak.pipeline.ContextType;
import com.bakshidwarak.pipeline.Depends;
import com.bakshidwarak.pipeline.ExecutionContext;
import com.bakshidwarak.pipeline.PipelineTask;

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
