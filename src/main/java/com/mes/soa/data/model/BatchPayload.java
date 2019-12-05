package com.mes.soa.data.model;

import java.sql.Timestamp;

public class BatchPayload {
	
	private int batchId;
	private String batchNumber;
	private int loadFileId;
	private String loadFileName;
	private Timestamp createOn;
	private String updatedBy;
	private Timestamp updateOn;
	private int merchantNumber;
	private String terminalId;
	private String source;
	private String process;
	
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public int getLoadFileId() {
		return loadFileId;
	}
	public void setLoadFileId(int loadFileId) {
		this.loadFileId = loadFileId;
	}
	public String getLoadFileName() {
		return loadFileName;
	}
	public void setLoadFileName(String loadFileName) {
		this.loadFileName = loadFileName;
	}
	public Timestamp getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Timestamp createOn) {
		this.createOn = createOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Timestamp updateOn) {
		this.updateOn = updateOn;
	}
	public int getMerchantNumber() {
		return merchantNumber;
	}
	public void setMerchantNumber(int merchantNumber) {
		this.merchantNumber = merchantNumber;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	
	

}
