package com.mes.soa.data.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mes.soa.data.dao.BatchInformationService;
import com.mes.soa.data.model.BatchProcessInfoResponse;
import com.mes.soa.data.model.ExecutionResponse;
import com.mes.soa.data.model.LinkResource;
import com.mes.soa.data.model.ResourceIdentifier;

@RestController
@RequestMapping("/v1/data")
public class BatchProcessExecutionController {

	@Autowired(required = false)
	private BatchInformationService batchInformationService;

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/batches/{batchId}/process{code}/executions")
	public ResponseEntity<ExecutionResponse> getProcessingStatus(@PathVariable(name = "batchId") int batchId,
			@MatrixVariable(name = "code", pathVar = "code") String code) throws Exception {
		ExecutionResponse response = batchInformationService.getBatchProcessInfo(batchId, code);
		if (response != null) {
			JSONObject respObj = new JSONObject();
			respObj.put("batchId", response);
			return new ResponseEntity<ExecutionResponse>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<ExecutionResponse>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/batches/{batchId}/process{code}/executions/{executionId}")
	public ResponseEntity<LinkResource> addBatchEntry(@PathVariable(name = "batchId") int batchId,
			@MatrixVariable(name = "code", pathVar = "code") String code,
			@PathVariable(name = "executionId") int executionId) throws Exception {

		LinkResource response = batchInformationService.setBatchData(batchId, code, executionId);
		if (response != null) {
			return new ResponseEntity<LinkResource>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<LinkResource>(HttpStatus.BAD_REQUEST);

	}

}