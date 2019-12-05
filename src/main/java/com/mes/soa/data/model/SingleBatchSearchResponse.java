package com.mes.soa.data.model;

import java.sql.Timestamp;
import org.json.simple.JSONObject;
import org.springframework.hateoas.ResourceSupport;

public class SingleBatchSearchResponse extends  ResourceSupport{
	
	
	Integer batchNumber;
	Integer loadFileId;
	String loadFileName;
	Timestamp createdOn;
	String createdBy; 

	JSONObject source = new JSONObject();
	JSONObject process = new JSONObject();
	ResourceIdentifier batchId;
	ResourceIdentifier merchantId;
	ResourceIdentifier terminalId;
	
	public Integer getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Integer getLoadFileId() {
		return loadFileId;
	}
	public void setLoadFileId(Integer loadFileId) {
		this.loadFileId = loadFileId;
	}
	public String getLoadFileName() {
		return loadFileName;
	}
	public void setLoadFileName(String loadFileName) {
		this.loadFileName = loadFileName;
	}
	public JSONObject getSource() {
		return source;
	}
	public void setSource(JSONObject source) {
		this.source = source;
	}
	public JSONObject getProcess() {
		return process;
	}
	public void setProcess(JSONObject process) {
		this.process = process;
	}
	public ResourceIdentifier getBatchId() {
		return batchId;
	}
	public void setBatchId(ResourceIdentifier batchId) {
		this.batchId = batchId;
	}
	public ResourceIdentifier getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(ResourceIdentifier merchantId) {
		this.merchantId = merchantId;
	}

	public ResourceIdentifier getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(ResourceIdentifier terminalId) {
		this.terminalId = terminalId;
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
	
}
