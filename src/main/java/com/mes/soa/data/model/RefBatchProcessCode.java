package com.mes.soa.data.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class RefBatchProcessCode {
	
	@QuerySqlField(index = true)
	private Integer batchProcessSId;
	
	@QuerySqlField
	private String batchProcessCd;
	
	@QuerySqlField
	private String batchProcessNm;
	
	@QuerySqlField
	private String batchProcessDesc;
	
	@QuerySqlField
	private String createdBy;
	
	@QuerySqlField
	private Timestamp createdOn;
	
	@QuerySqlField
	private String updatedBy;
	
	@QuerySqlField
	private Timestamp updatedOn;
	
	public RefBatchProcessCode() {
		
	}
	public RefBatchProcessCode(ResultSet rs) throws SQLException {
        this.batchProcessSId = rs.getInt("batch_process_sid");
        this.batchProcessCd = rs.getString("batch_process_cd");
        this.batchProcessNm = rs.getString("batch_process_nm");
        this.batchProcessDesc = rs.getString("batch_process_desc");
        this.createdBy = rs.getString("created_by");
        this.createdOn = rs.getTimestamp("created_on");
        this.updatedBy = rs.getString("updated_by");
        this.updatedOn = rs.getTimestamp("updated_on");
    
    }
	public Integer getBatchProcessSId() {
		return batchProcessSId;
	}

	public void setBatchProcessSId(Integer batchProcessSId) {
		this.batchProcessSId = batchProcessSId;
	}

	public String getBatchProcessCd() {
		return batchProcessCd;
	}

	public void setBatchProcessCd(String batchProcessCd) {
		this.batchProcessCd = batchProcessCd;
	}

	public String getBatchProcessNm() {
		return batchProcessNm;
	}

	public void setBatchProcessNm(String batchProcessNm) {
		this.batchProcessNm = batchProcessNm;
	}

	public String getBatchProcessDesc() {
		return batchProcessDesc;
	}

	public void setBatchProcessDesc(String batchProcessDesc) {
		this.batchProcessDesc = batchProcessDesc;
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
