package com.mes.soa.data.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class RefBatchSourceCode {
	
	@QuerySqlField(index = true)
	private Integer batchSourceSId; 
	
	@QuerySqlField
	private String batchSourceCd;     
	
	@QuerySqlField
	private String batchSourceNm;     
	
	@QuerySqlField
	private String batchSourceDesc;   
		
	@QuerySqlField
	private String createdBy;  
	
	@QuerySqlField
	private Timestamp createdOn;
	
	@QuerySqlField
	private String updatedBy;
	
	@QuerySqlField
	private Timestamp updatedOn;
	
	public RefBatchSourceCode() {
		
	}
	public RefBatchSourceCode(ResultSet rs) throws SQLException {
        this.batchSourceSId = rs.getInt("batch_source_sid");
        this.batchSourceCd = rs.getString("batch_source_cd");
        this.batchSourceNm = rs.getString("batch_source_nm");
        this.batchSourceDesc = rs.getString("batch_source_desc");
        this.createdBy = rs.getString("created_by");
        this.createdOn = rs.getTimestamp("created_on");
        this.updatedBy = rs.getString("updated_by");
        this.updatedOn = rs.getTimestamp("updated_on");
    }
	public Integer getBatchSourceSId() {
		return batchSourceSId;
	}

	public void setBatchSourceSId(Integer batchSourceSId) {
		this.batchSourceSId = batchSourceSId;
	}

	public String getBatchSourceCd() {
		return batchSourceCd;
	}

	public void setBatchSourceCd(String batchSourceCd) {
		this.batchSourceCd = batchSourceCd;
	}

	public String getBatchSourceNm() {
		return batchSourceNm;
	}

	public void setBatchSourceNm(String batchSourceNm) {
		this.batchSourceNm = batchSourceNm;
	}

	public String getBatchSourceDesc() {
		return batchSourceDesc;
	}

	public void setBatchSourceDesc(String batchSourceDesc) {
		this.batchSourceDesc = batchSourceDesc;
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
