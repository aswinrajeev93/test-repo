package com.mes.soa.data.model;

import org.springframework.hateoas.ResourceSupport;

public class BatchResponse extends ResourceSupport{

	SingleBatchSearchResponse batch;

	public SingleBatchSearchResponse getBatch() {
		return batch;
	}

	public void setBatch(SingleBatchSearchResponse batch) {
		this.batch = batch;
	}	
}
