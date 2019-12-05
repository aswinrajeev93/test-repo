package com.mes.soa.data;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mes.soa.data.config.IgniteConfig;
import com.mes.soa.data.dao.BatchInformationDAO;
import com.mes.soa.data.dao.BatchInformationService;
import com.mes.soa.data.dao.BatchInformationServiceImpl;
import com.mes.soa.data.model.BatchPayload;
import com.mes.soa.data.model.BatchResEntity;
import com.mes.soa.data.model.BatchResponse;
import com.mes.soa.data.model.BatchResponseEntity;
import com.mes.soa.data.model.ExecutionResponse;
import com.mes.soa.data.model.LinkResource;
import com.mes.soa.data.model.ResultResource;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BatchInformationServiceImpl.class, BatchInformationDAO.class,IgniteConfig.class}, loader = AnnotationConfigContextLoader.class)
public class SoaDataBatchInformationApplicationTest {

	@Autowired
	BatchInformationService batchInformationService;

	@Test 
	void getBatchInfo() {

		String processCode ="FL01";
		String message = "Failed";	
		try {
			BatchResponse response =batchInformationService.getBatchInfo(processCode);
			if(response instanceof BatchResponse  ){
				message = "Success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(message, "Success");

	}

	@Test
	void getFilteredBatchDetails(){
		String source="PG" ;
		String stage="A" ;
		String process="FLO1";
		Integer offset=5;
		Integer size=10;
		String message = "Failed";	
		try {
			ResultResource response =batchInformationService.getFilteredBatchDetails(source, stage, process, offset, size);
			if(response instanceof ResultResource  ){
				message = "Success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(message, "Success");

	}

	@Test 
	void findByBatchId() {

		String message = "Failed";	
		Integer batchId= 1001;
		String code ="FL01";
		try {
			BatchResponseEntity response =batchInformationService.findByBatchId(batchId, code);
			if(response instanceof BatchResponseEntity  ){
				message = "Success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(message, "Success");

	}

	@Test 
	void saveBatch() {

		BatchPayload batchPayload = new BatchPayload();
		batchPayload.setBatchId(1);
		batchPayload.setBatchNumber("123");
		batchPayload.setLoadFileId(10);
		batchPayload.setLoadFileName("MeSFile");
		batchPayload.setTerminalId("1");
		batchPayload.setMerchantNumber(1);
		batchPayload.setCreateOn(new Timestamp(new Date().getTime()));
		batchPayload.setUpdatedBy("");
		batchPayload.setUpdateOn(new Timestamp(new Date().getTime()));
		List<BatchPayload> batchListData = new ArrayList();
		batchListData.add(batchPayload);
		String message = "Failed";	
		try {
			BatchResEntity response =batchInformationService.saveBatch(batchListData);
			if(response instanceof BatchResEntity  ){
				message = "Success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(message, "Success");

	}
	@Test 
	void deleteBatch() {
		Integer batchId =1001;
		String message = "Failed";	
		try {
			Boolean response =batchInformationService.deleteBatch(batchId);
			if(response instanceof Boolean  ){
				message = "Success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(message, "Success");

	}
	@Test 
	void getBatchProcessInfo()
	{
		int batchId=1001;
		String code="FL01";
		String message = "Failed";	

		try {
			ExecutionResponse response =batchInformationService.getBatchProcessInfo(batchId, code);
			if(response instanceof ExecutionResponse  ){
				message = "Success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(message, "Success");
	}
	@Test 
	void setBatchData()
	{
		int batchId=1001;
		String code="FL01";
		int executionId=1001;
		String message = "Failed";	
		try {
			LinkResource response =batchInformationService.setBatchData(batchId, code, executionId);
			if(response instanceof LinkResource  ){
				message = "Success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(message, "Success");

	}


}
