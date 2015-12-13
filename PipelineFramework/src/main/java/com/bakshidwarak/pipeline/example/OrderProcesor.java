package com.bakshidwarak.pipeline.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class OrderProcesor {

	@Autowired
	AddressValidatorService addressValidator;

	@Autowired
	FeasibilityService feasibilityService;

	@Autowired
	OrderDAO orderDao;

	@Autowired
	NotificationService notificationService;

	public void processOrder(Order orderInfo) throws ProcessingException,
			InvalidAddressException {
		// Validate Order information

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

		// Validate Address Info get address id call third party address
		// validation service
		long addressId = addressValidator.validateAddress(orderInfo
				.getAddress());
		if (addressId == -1) {
			throw new InvalidAddressException();
		}

		// Call Feasibility service to check if line is feasible at this address
		// ( pass the address id)
		FeasibilityInfo feasibiliyInfo = null;
		try {
			feasibiliyInfo = feasibilityService.checkFeasibility(addressId);
		} catch (FeasibilityException e) {
			// Log the error
			throw new ProcessingException("Order cannot be fulfilled");
		}

		// Check for promotions for the order

		// Apply Promotions if any

		OrderDBObject orderDBObject = transformOrderInfoToOrderDAO(orderInfo,feasibiliyInfo);
		// Process the order ( persist db entries)
		String orderId;
		try {
			orderId = orderDao.insertOrder(orderDBObject);
		} catch (Exception e) {
			throw new ProcessingException("Error submitting order");
		}
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setMessageType("EMAIL");
		notificationVO.setMessageBody("Order ID" + orderId
				+ "Submitted for processing ");
		notificationService.sendNotification(notificationVO);

	}

	private OrderDBObject transformOrderInfoToOrderDAO(Order orderInfo, FeasibilityInfo feasibiliyInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
