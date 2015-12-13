package com.bakshidwarak.pipeline.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bakshidwarak.pipeline.Depends;
import com.bakshidwarak.pipeline.ExecutionContext;
import com.bakshidwarak.pipeline.PipelineTask;

@Component
public class SendNotificationPipelineTask extends PipelineTask {


	@Autowired
	NotificationService notificationService;
	
	@Depends(task = PersistOrderPipelineTask.class)
	String orderId;
	@Override
	protected Object handleRequest(ExecutionContext context) throws Exception {
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setMessageType("EMAIL");
		notificationVO.setMessageBody("Order ID" + orderId
				+ "Submitted for processing ");
		notificationService.sendNotification(notificationVO);
		return noOp;
	}

}
