package com.framework.pipeline.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses={OrderProcesor.class,OrderDAO.class},basePackages={"com.framework.pipeline"})
public class OrderProcessorConfig {

	@Bean
	public AddressValidatorService getAddressValidator()
	{
		return new AddressValidatorService() {
			
			@Override
			public long validateAddress(Address address) {
				System.out.println("Validating Address");
				return 1000;
			}
		};
	}
	
	@Bean
	public FeasibilityService getFeasibilityService(){
		
		return new FeasibilityService() {
			
			@Override
			public FeasibilityInfo checkFeasibility(long addressId)
					throws FeasibilityException {
				System.out.println("Checking feasibility for the line");
				return new FeasibilityInfo();
			}
		};
	}
	
	@Bean 
	public NotificationService getNotificationService(){
		return new NotificationService() {
			
			@Override
			public void sendNotification(NotificationVO notificationVO) {
				System.out.println(" Sending a message");
				
			}
		};
	}
}
