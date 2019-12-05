package com.mes.soa.data.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class BatchStatus{

	@QuerySqlField(index = true)
	private Integer batchStatusSId;

	@QuerySqlField
	private Integer statusSId;
	
	@QuerySqlField
	private Integer batchSId;
		
	@QuerySqlField
	private Timestamp createdOn;
	
	@QuerySqlField
	private String createdBy;
	
	@QuerySqlField
	private Timestamp updatedOn;
	
	@QuerySqlField
	private String updatedBy;
	
	public BatchStatus() {		
	}
	public BatchStatus(Integer batchStatusSId,Integer statusSId,Integer batchSId,Timestamp createdOn,String createdBy,Timestamp updatedOn,String updatedBy) {		
		this.batchStatusSId = batchStatusSId;
		this.statusSId = statusSId;
		this.batchSId = batchSId;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}
	
	public BatchStatus(ResultSet rs) throws SQLException {
        this.batchStatusSId = rs.getInt("batch_status_sid");
        this.statusSId = rs.getInt("status_sid");
        this.batchSId = rs.getInt("batch_sid");
        this.createdOn = rs.getTimestamp("created_on");
        this.createdBy = rs.getString("created_by");
        this.updatedBy = rs.getString("updated_by");
        this.updatedOn = rs.getTimestamp("updated_on");
    }

	public Integer getBatchStatusSId() {
		return batchStatusSId;
	}

	public void setBatchStatusSId(Integer batchStatusSId) {
		this.batchStatusSId = batchStatusSId;
	}

	public Integer getStatusSId() {
		return statusSId;
	}

	public void setStatusSId(Integer statusSId) {
		this.statusSId = statusSId;
	}

	public Integer getBatchSId() {
		return batchSId;
	}

	public void setBatchSId(Integer batchSId) {
		this.batchSId = batchSId;
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
