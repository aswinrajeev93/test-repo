package com.mes.soa.data.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class BatchProcessInfoResponse extends ResourceSupport {

	ResourceIdentifier batchId;
	List<ResourceIdentifier> executions;

	public ResourceIdentifier getBatchId() {
		return batchId;
	}

	public void setBatchId(ResourceIdentifier batchId) {
		this.batchId = batchId;
	}

	public List<ResourceIdentifier> getExecutions() {
		return executions;
	}

	public void setExecutions(List<ResourceIdentifier> executions) {
		this.executions = executions;
	}

}
