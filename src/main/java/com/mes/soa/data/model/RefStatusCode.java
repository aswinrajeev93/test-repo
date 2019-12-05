package com.mes.soa.data.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class RefStatusCode {

	@QuerySqlField(index = true)
	private Integer statusSId;
	
	@QuerySqlField
	private String statusCd;
	
	@QuerySqlField
	private Timestamp createdOn;
	
	@QuerySqlField
	private String createdBy ;
		
	@QuerySqlField
	private Timestamp updatedOn;
	
	@QuerySqlField
	private String updatedBy ;
	
	public RefStatusCode() {
		
	}
	
	public RefStatusCode(Integer statusSId,String statusCd,Timestamp createdOn,String createdBy,Timestamp updatedOn,String updatedBy) {
		super();
		this.statusSId = statusSId;
		this.statusCd = statusCd;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;	
		this.updatedBy = updatedBy;
			
	}
	public RefStatusCode(ResultSet rs)throws SQLException {
        this.statusSId = rs.getInt("status_sid");
        this.statusCd = rs.getString("status_cd");
        this.createdOn = rs.getTimestamp("created_on");
        this.createdBy = rs.getString("created_by");
        this.updatedOn = rs.getTimestamp("updated_on");
        this.updatedBy = rs.getString("updated_by");        
    }

	public Integer getStatusSId() {
		return statusSId;
	}

	public void setStatusSId(Integer statusSId) {
		this.statusSId = statusSId;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
