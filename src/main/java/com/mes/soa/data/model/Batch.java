package com.mes.soa.data.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Batch{

	@QuerySqlField(index = true)
	private Integer batchSId;

	@QuerySqlField
	private Integer batchId;
	
	@QuerySqlField
	private Integer batchProcessSId;
	
	@QuerySqlField
	private Integer batchSourceSId;
	
	@QuerySqlField
	private Integer batchNbr;
	
	@QuerySqlField
	private Integer loadFileId;
	
	@QuerySqlField
	private String loadFileNm;
	
	@QuerySqlField
	private Timestamp createdOn;
	
	@QuerySqlField
	private String createdBy;
	
	@QuerySqlField
	private String updatedBy;
	
	@QuerySqlField
	private Timestamp updatedOn;
	
	@QuerySqlField
	private Integer merchantNbr;
	
	@QuerySqlField
	private String terminalId;
	
	@QuerySqlField
	private Timestamp merchantBatchDt;
	
	@QuerySqlField
	private Long transactionCnt;
	
	
	public Batch() {		
	}
	
	public Batch(ResultSet rs) throws SQLException {
        this.batchSId = rs.getInt("batch_sid");
        this.batchId = rs.getInt("batch_id");
        this.batchProcessSId = rs.getInt("batch_process_sid");
        this.batchSourceSId = rs.getInt("batch_source_sid");
        this.batchNbr = rs.getInt("batch_nbr");
        this.loadFileId = rs.getInt("load_file_id");
        this.loadFileNm = rs.getString("load_file_nm");
        this.createdOn = rs.getTimestamp("created_on");
        this.createdBy = rs.getString("created_by");
        this.updatedBy = rs.getString("updated_by");
        this.updatedOn = rs.getTimestamp("updated_on");
        this.merchantNbr = rs.getInt("merchant_nbr");
        this.terminalId = rs.getString("terminal_id");
        this.merchantBatchDt = rs.getTimestamp("merchant_batch_dt");
        this.transactionCnt = rs.getLong("transaction_cnt");
    }
	
	public Integer getBatchSId() {
		return batchSId;
	}

	public void setBatchSId(Integer batchSId) {
		this.batchSId = batchSId;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getBatchNbr() {
		return batchNbr;
	}

	public void setBatchNbr(Integer batchNbr) {
		this.batchNbr = batchNbr;
	}

	public Integer getLoadFileId() {
		return loadFileId;
	}

	public void setLoadFileId(Integer loadFileId) {
		this.loadFileId = loadFileId;
	}

	public String getLoadFileNm() {
		return loadFileNm;
	}

	public void setLoadFileNm(String loadFileNm) {
		this.loadFileNm = loadFileNm;
	}

	public Integer getBatchProcessSId() {
		return batchProcessSId;
	}

	public void setBatchProcessSId(Integer batchProcessSId) {
		this.batchProcessSId = batchProcessSId;
	}

	public Integer getBatchSourceSId() {
		return batchSourceSId;
	}

	public void setBatchSourceSId(Integer batchSourceSId) {
		this.batchSourceSId = batchSourceSId;
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

	public Integer getMerchantNbr() {
		return merchantNbr;
	}

	public void setMerchantNbr(Integer merchantNbr) {
		this.merchantNbr = merchantNbr;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public Timestamp getMerchantBatchDt() {
		return merchantBatchDt;
	}

	public void setMerchantBatchDt(Timestamp merchantBatchDt) {
		this.merchantBatchDt = merchantBatchDt;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Long getTransactionCnt() {
		return transactionCnt;
	}
	public void setTransactionCnt(Long transactionCnt) {
		this.transactionCnt = transactionCnt;
	}
}
