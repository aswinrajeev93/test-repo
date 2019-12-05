package com.mes.soa.data.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mes.soa.data.dao.BatchInformationService;
import com.mes.soa.data.model.BatchPayload;
import com.mes.soa.data.model.BatchResEntity;
import com.mes.soa.data.model.BatchResponse;
import com.mes.soa.data.model.BatchResponseEntity;
import com.mes.soa.data.model.ResultResource;
import com.mes.soa.data.model.SingleBatchSearchResponse;

@RestController
@RequestMapping("/v1/data")
public class BatchInformationController {

	@Autowired(required = false)
	private BatchInformationService batchInformationService;
	
	@PostMapping(value = "/batches/single-unprocessed")
	public ResponseEntity<BatchResponse> getBatchesSingleUnprocessed(@RequestBody JSONObject requestBody) throws Exception
	{
		ResponseEntity<BatchResponse> httpResponse = new ResponseEntity<>(HttpStatus.OK);
		String processCode = (String) requestBody.get("processCode");
		BatchResponse response = batchInformationService.getBatchInfo(processCode);
		if(response!=null)
			httpResponse = new ResponseEntity<BatchResponse>(response, HttpStatus.OK);
		else
			httpResponse = new ResponseEntity<BatchResponse>(response, HttpStatus.NO_CONTENT);
		return httpResponse;
	}

	@GetMapping(value = "/batches")
	public ResponseEntity<ResultResource> getBatches(@RequestParam(required = false, name = "source") String source,
			@RequestParam(required = false,name = "stage") String stage,
			@RequestParam(required = false,name = "process") String process,
			@RequestParam(required = false,name = "offset") Integer offset,
			@RequestParam(required = false,name = "size") Integer size) throws Exception
	{
		ResponseEntity<ResultResource> httpResponse = new ResponseEntity<>(HttpStatus.OK);
		ResultResource response = batchInformationService.getFilteredBatchDetails(source,stage,process,offset,size);
		if(response!=null)
			httpResponse = new ResponseEntity<ResultResource>(response, HttpStatus.OK);
		else
			httpResponse = new ResponseEntity<ResultResource>(response, HttpStatus.NO_CONTENT);
		return httpResponse;
	}
	
	@GetMapping(value = "/batches/{batchId}/process/{code}")
	public ResponseEntity<BatchResponseEntity> getLatestBatch(@PathVariable(name = "batchId") Integer batchId,
	@PathVariable(name = "code") String code) throws Exception {
		return ResponseEntity.ok().body(batchInformationService.findByBatchId(batchId, code));
	}
	
	@PutMapping(value = "/batches")
	public ResponseEntity<BatchResEntity> createNewBatch(@RequestBody List<BatchPayload> batchPayloads,HttpServletRequest httpServletRequest) throws Exception {
		return new ResponseEntity<BatchResEntity>(batchInformationService.saveBatch(batchPayloads), HttpStatus.CREATED);	
	}
	
	@DeleteMapping(value = "/batches/{batchId}")
	public ResponseEntity<?> deleteBatch(@PathVariable(name = "batchId") Integer batchId,HttpServletRequest httpServletRequest) throws Exception {
	      batchInformationService.deleteBatch(batchId);
	return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	
	}
}
