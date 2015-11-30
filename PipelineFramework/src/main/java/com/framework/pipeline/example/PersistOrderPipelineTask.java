package com.framework.pipeline.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.framework.pipeline.ContextType;
import com.framework.pipeline.Depends;
import com.framework.pipeline.ExecutionContext;
import com.framework.pipeline.PipelineTask;

@Component
public class PersistOrderPipelineTask extends PipelineTask {

	@Autowired
	OrderDAO orderDao;

	@Depends(type = ContextType.REQUEST_OBJ)
	private Order orderInfo;

	@Depends(task = FeasibilityCheckPipelineTask.class)
	FeasibilityInfo feasibiliyInfo;

	@Override
	protected Object handleRequest(ExecutionContext context) throws Exception {
		OrderDBObject orderDBObject = transformOrderInfoToOrderDAO(orderInfo,
				feasibiliyInfo);
		// Process the order ( persist db entries)
		String orderId;
		try {
			orderId = orderDao.insertOrder(orderDBObject);
		} catch (Exception e) {
			throw new ProcessingException("Error submitting order");
		}
		return orderId;
	}

	private OrderDBObject transformOrderInfoToOrderDAO(Order orderInfo,
			FeasibilityInfo feasibiliyInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
