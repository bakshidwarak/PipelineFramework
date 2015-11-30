package com.framework.pipeline.example;

public interface FeasibilityService {

	FeasibilityInfo checkFeasibility(long addressId) throws FeasibilityException;

}
