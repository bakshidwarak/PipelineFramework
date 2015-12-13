package com.bakshidwarak.pipeline.example;

public interface FeasibilityService {

	FeasibilityInfo checkFeasibility(long addressId) throws FeasibilityException;

}
