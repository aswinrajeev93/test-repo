package com.mes.soa.data.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class BatchResEntity extends ResourceSupport{
	
	private List<ResourceIdentifier> batchId;

	public List<ResourceIdentifier> getBatchId() {
		return batchId;
	}

	public void setBatchId(List<ResourceIdentifier> batchId) {
		this.batchId = batchId;
	}

	

	
	

}
