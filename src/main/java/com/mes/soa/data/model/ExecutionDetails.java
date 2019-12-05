package com.mes.soa.data.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class ExecutionDetails {

	@QuerySqlField(index = true)
	private Integer executionSId;
	
	@QuerySqlField
	private Integer executionId;
	
	@QuerySqlField
	private String stageCd;
	
	@QuerySqlField
	private Integer levelCd;
	
	@QuerySqlField
	private Timestamp executionTs;
	
	@QuerySqlField
	private String createdBy;
	
	@QuerySqlField
	private Timestamp createdOn;
	
	@QuerySqlField
	private String updatedBy;
	
	@QuerySqlField
	private Timestamp updatedOn;

	public Integer getExecutionId() {
		return executionId;
	}

	public ExecutionDetails() {
		
	}
		
	public ExecutionDetails(ResultSet rs) throws SQLException {
        this.executionSId = rs.getInt("execution_sId");
        this.executionId = rs.getInt("execution_id");
        this.stageCd = rs.getString("stage_cd");
        this.levelCd = rs.getInt("level_cd");
        this.executionTs = rs.getTimestamp("execution_ts");
        this.createdBy = rs.getString("created_by");
        this.createdOn = rs.getTimestamp("created_on");
        this.updatedBy = rs.getString("updated_by");
        this.updatedOn = rs.getTimestamp("updated_on");
    }
	
	public void setExecutionId(Integer executionId) {
		this.executionId = executionId;
	}

	public String getStageCd() {
		return stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
	}

	public Integer getLevelCd() {
		return levelCd;
	}

	public void setLevelCd(Integer levelCd) {
		this.levelCd = levelCd;
	}

	public Timestamp getExecutionTs() {
		return executionTs;
	}
	
	public void setExecutionTs(Timestamp executionTs) {
		this.executionTs = executionTs;
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

	public Integer getExecutionSId() {
		return executionSId;
	}

	public void setExecutionSId(Integer executionSId) {
		this.executionSId = executionSId;
	}


	
	
}
