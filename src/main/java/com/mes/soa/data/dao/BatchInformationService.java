package com.mes.soa.data.dao;

import java.util.List;
import com.mes.soa.data.model.BatchPayload;
import com.mes.soa.data.model.BatchResEntity;
import com.mes.soa.data.model.BatchResponse;
import com.mes.soa.data.model.BatchResponseEntity;
import com.mes.soa.data.model.ExecutionResponse;
import com.mes.soa.data.model.LinkResource;
import com.mes.soa.data.model.ResultResource;


public interface BatchInformationService {

	BatchResponse getBatchInfo(String processCode) throws Exception;
	
	ResultResource getFilteredBatchDetails(String source,String stage,String process,Integer offset,Integer size) throws Exception;
	
	BatchResponseEntity findByBatchId(Integer batchId,String code)throws NumberFormatException, Exception;
	
	BatchResEntity  saveBatch(List<BatchPayload> batchList)  throws Exception;
	
	Boolean deleteBatch(Integer batchId) throws Exception;
	
	ExecutionResponse getBatchProcessInfo(int batchId, String code) throws Exception;

	LinkResource setBatchData(int batchId, String code, int executionId);

}
