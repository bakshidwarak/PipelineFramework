package com.framework.pipeline.example;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bakshidwarak.pipeline.example.Address;
import com.bakshidwarak.pipeline.example.InvalidAddressException;
import com.bakshidwarak.pipeline.example.Order;
import com.bakshidwarak.pipeline.example.OrderProcesor;
import com.bakshidwarak.pipeline.example.OrderProcessorPipeline;
import com.bakshidwarak.pipeline.example.ProcessingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { OrderProcessorConfig.class })
public class OrderProcesorTest {

	@Autowired
	OrderProcesor processor;
	
	@Autowired 
	OrderProcessorPipeline pipeline;
	@Test
	public void testProcessOrder() {
		try {
			Order orderInfo = new Order();
			Address address= new Address();
			address.setState("CA");
			orderInfo.setAddress(address);
			processor.processOrder(orderInfo);
		} catch (ProcessingException | InvalidAddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testProcessOrderPipeline() {
		try {
			Order orderInfo = new Order();
			Address address= new Address();
			address.setState("CA");
			orderInfo.setAddress(address);
			pipeline.processOrder(orderInfo);
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
