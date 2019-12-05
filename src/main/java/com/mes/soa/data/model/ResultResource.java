package com.mes.soa.data.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class ResultResource extends ResourceSupport {

	List<ResourceIdentifier> _result = new ArrayList<ResourceIdentifier>();

	public List<ResourceIdentifier> get_result() {
		return _result;
	}

	public void set_result(List<ResourceIdentifier> _result) {
		this._result = _result;
	}


	
	
}
