package com.mes.soa.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mes.soa.data.model.Batch;
import com.mes.soa.data.model.BatchList;
import com.mes.soa.data.model.BatchPayload;
import com.mes.soa.data.model.BatchPayloadList;
import com.mes.soa.data.model.BatchProcessInfoResponse;
import com.mes.soa.data.model.BatchResEntity;
import com.mes.soa.data.model.BatchResponse;
import com.mes.soa.data.model.BatchResponseEntity;
import com.mes.soa.data.model.ExecutionResponse;
import com.mes.soa.data.model.LinkResource;
import com.mes.soa.data.model.ResultResource;
import com.mes.soa.data.model.SingleBatchSearchResponse;


@Service
public class BatchInformationServiceImpl implements BatchInformationService{

	@Autowired
	private BatchInformationDAO batchInformationDAO;

	public BatchResponse getBatchInfo(String processCode) throws Exception {
		BatchResponse response = batchInformationDAO.getBatchInformation(processCode);
		return response;
	}
	
	public ResultResource getFilteredBatchDetails(String source, String stage, String process,Integer offset,Integer size) throws Exception {
		ResultResource response = batchInformationDAO.getFilteredBachInformation(source,stage,process,offset,size);
		return response;
	}
	
	public BatchResponseEntity findByBatchId(Integer batchId, String code) throws NumberFormatException, Exception {
		BatchResponseEntity batch = batchInformationDAO.findBatchId(batchId, code);
		return batch;
	}

	public Boolean deleteBatch(Integer batchId) throws Exception {
		return batchInformationDAO.deleteBatch(batchId);
	}

	@Override
	public BatchResEntity  saveBatch(List<BatchPayload> batchPayloads) throws Exception {

		BatchPayloadList batchPayloadList=new BatchPayloadList();
		batchPayloadList.setList(batchPayloads);
		BatchList batchList=new BatchList();
		List<Batch> batchLists=new ArrayList<Batch>(); 

		for(BatchPayload batchPayload: batchPayloadList.getList())
		{
			Batch batch=new Batch();
			batch.setBatchId(batchPayload.getBatchId());
			batch.setBatchProcessSId(batchInformationDAO.getProcessCode(batchPayload.getProcess()));
			batch.setBatchSourceSId(batchInformationDAO.getSourceCode(batchPayload.getSource()));
			batch.setBatchNbr(Integer.parseInt(batchPayload.getBatchNumber()));
			batch.setLoadFileId(batchPayload.getLoadFileId());
			batch.setLoadFileNm(batchPayload.getLoadFileName());
			batch.setUpdatedBy(batchPayload.getUpdatedBy());
			batch.setTerminalId(batchPayload.getTerminalId());
			batch.setCreatedOn(batchPayload.getCreateOn());
			batch.setUpdatedOn(batchPayload.getUpdateOn());
			batch.setMerchantNbr(batchPayload.getMerchantNumber());

			batchLists.add(batch);
		}
		batchList.setBatchList(batchLists);

		return batchInformationDAO.save(batchList);
	}

	public ExecutionResponse getBatchProcessInfo(int batchId, String code) throws Exception {
		ExecutionResponse refBatchProcessCode = batchInformationDAO.getBatchProcessInformation(batchId, code);
		return refBatchProcessCode;
	}

	@Override
	public LinkResource setBatchData(int batchId, String code, int executionId) {

		LinkResource res = batchInformationDAO.addBatchData(batchId, code, executionId);
		return res;
	}
}
