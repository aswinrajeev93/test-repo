package com.mes.soa.data.model;

import org.springframework.hateoas.ResourceSupport;

public class BatchResponseEntity extends ResourceSupport {
	
	SingleBatchSearchResponse _result;

	public SingleBatchSearchResponse get_result() {
		return _result;
	}

	public void set_result(SingleBatchSearchResponse _result) {
		this._result = _result;
	}

}
