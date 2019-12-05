package com.mes.soa.data.model;

import org.springframework.hateoas.ResourceSupport;

public class ResourceIdentifier extends ResourceSupport{
	private Integer _id;

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}

}
