package com.framework.pipeline.example;

import org.springframework.stereotype.Component;

@Component
public class OrderDAO {

	public String insertOrder(OrderDBObject orderDBObject) {
		// TODO Auto-generated method stub
		System.out.println("Inserting Order in DB");
		return "order";
	}

}
