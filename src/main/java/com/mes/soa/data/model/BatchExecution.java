package com.mes.soa.data.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class BatchExecution {

	@QuerySqlField(index = true)
	private Integer batchExecutionSId;

	@QuerySqlField
	private Integer executionSId;

	@QuerySqlField
	private Integer batchSId;

	@QuerySqlField
	private String createdBy;

	@QuerySqlField
	private Timestamp createdOn;

	@QuerySqlField
	private String updatedBy;

	@QuerySqlField
	private Timestamp updatedOn;

	public BatchExecution() {
	}
	public BatchExecution(int batchExecutionSId,int executionSId, int batchSId,  String createdBy, Timestamp createdOn, String updatedBy, Timestamp updatedOn) {

		super();
		this.batchExecutionSId=batchExecutionSId;
		this.executionSId = executionSId;
		this.batchSId = batchSId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;

	}

	public BatchExecution(ResultSet rs) throws SQLException {
		this.batchExecutionSId = rs.getInt("batch_execution_sid");
		this.executionSId = rs.getInt("execution_sid");
		this.batchSId = rs.getInt("batch_sid");
		this.createdBy = rs.getString("created_by");
		this.createdOn = rs.getTimestamp("created_on");
		this.updatedBy = rs.getString("updated_by");
		this.updatedOn = rs.getTimestamp("updated_on");
	}

	public BatchExecution(Integer batchExecutionSId, Integer executionSId, Integer batchSId, String createdBy,Timestamp createdOn) {
		super();
		this.batchExecutionSId = batchExecutionSId;
		this.executionSId = executionSId;
		this.batchSId = batchSId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}

	public Integer getBatchExecutionSId() {
		return batchExecutionSId;
	}
	public void setBatchExecutionSId(Integer batchExecutionSId) {
		this.batchExecutionSId = batchExecutionSId;
	}

	public Integer getExecutionSId() {
		return executionSId;
	}
	public void setExecutionSId(Integer executionSId) {
		this.executionSId = executionSId;
	}
	public Integer getBatchSId() {
		return batchSId;
	}
	public void setBatchSId(Integer batchSId) {
		this.batchSId = batchSId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}


}
