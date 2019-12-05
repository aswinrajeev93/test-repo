package com.mes.soa.data.model;

import org.springframework.hateoas.ResourceSupport;

public class ExecutionResponse extends ResourceSupport{

	BatchProcessInfoResponse executionInstances;

	public BatchProcessInfoResponse getExecutionInstances() {
		return executionInstances;
	}

	public void setExecutionInstances(BatchProcessInfoResponse executionInstances) {
		this.executionInstances = executionInstances;
	}
	
	
}
