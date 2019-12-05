package com.mes.soa.data.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mes.soa.data.controller.BatchInformationController;
import com.mes.soa.data.controller.BatchProcessExecutionController;
import com.mes.soa.data.model.Batch;
import com.mes.soa.data.model.BatchExecution;
import com.mes.soa.data.model.BatchList;
import com.mes.soa.data.model.BatchProcessInfoResponse;
import com.mes.soa.data.model.BatchResEntity;
import com.mes.soa.data.model.BatchResponse;
import com.mes.soa.data.model.BatchResponseEntity;
import com.mes.soa.data.model.BatchStatus;
import com.mes.soa.data.model.ExecutionDetails;
import com.mes.soa.data.model.ExecutionResponse;
import com.mes.soa.data.model.LinkResource;
import com.mes.soa.data.model.RefBatchProcessCode;
import com.mes.soa.data.model.RefBatchSourceCode;
import com.mes.soa.data.model.RefStatusCode;
import com.mes.soa.data.model.ResourceIdentifier;
import com.mes.soa.data.model.ResultResource;
import com.mes.soa.data.model.SingleBatchSearchResponse;
import com.mes.soa.data.utility.Constants;
import com.mes.soa.frwk.exception.MeSBadRequestException;
import com.mes.soa.frwk.exception.MeSDbConnectionException;
import com.mes.soa.frwk.exception.MeSNotFoundException;
import com.mes.soa.frwk.logger.MesLogger;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class BatchInformationDAO {

	@Autowired
	Ignite ignite;
	
	private static final MesLogger logger = MesLogger.getLogger(BatchInformationDAO.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BatchResponse getBatchInformation(String processCode) throws Exception{
		BatchResponse batchResponse =null;
		SingleBatchSearchResponse response = null;
		IgniteCache<Integer, Batch> batchCache = ignite.getOrCreateCache("batch");
		batchCache.loadCache(null);
		
		IgniteCache<Integer, RefBatchProcessCode> refBatchProcessCodeCache = ignite.getOrCreateCache("refBatchProcessCode");
		refBatchProcessCodeCache.loadCache(null);
		
		IgniteCache<Integer, BatchStatus> batchStatusCache = ignite.getOrCreateCache("batchStatus");
		batchStatusCache.loadCache(null);
		
		IgniteCache<Integer, RefStatusCode> refStatusCodeCache = ignite.getOrCreateCache("refStatusCode");
		refStatusCodeCache.loadCache(null);
		
		IgniteCache<Integer, RefBatchSourceCode> refBatchSourceCodeCache = ignite.getOrCreateCache("refBatchSourceCode");
		refBatchSourceCodeCache.loadCache(null);
		
		String sql = "	select b.batchNbr,b.loadFileId,b.loadFileNm,b.createdBy,b.createdOn,b.updatedBy, b.updatedOn,b.batchId, " + 
				"	bsc.batchSourceCd,bsc.batchSourceNm,bpc.batchProcessCd, bpc.batchProcessNm, bpc.batchProcessDesc, b.merchantNbr, " + 
				"	b.terminalId,b.batchSId,rfc.statusCd,rfc.createdOn,b.createdBy,b.createdOn " + 
				"	from \"refBatchProcessCode\".RefBatchProcessCode bpc " + 
				"	join batch  b on b.batchProcessSId=bpc.batchProcessSId " + 
				"	left join \"refBatchSourceCode\".RefBatchSourceCode bsc on bsc.batchSourceSId = b.batchSourceSId " + 
				"	left join \"batchStatus\".BatchStatus bs on bs.batchSId = b.batchSId  " + 
				"	inner join \"refStatusCode\".RefStatusCode rfc on rfc.statusSId = bs.statusSId " + 
				"	where bpc.batchProcessCd = ? and b.batchId not in " + 
				"	(select batchId from \"batchStatus\".BatchStatus bs "+
				"     inner join (select max(batchStatusSId) as batchStatusSId,batchId	from" + 
				"	    (select bs.batchStatusSId,b.batchId,sc.statusCd from batch b" + 
				"		 left join \"batchStatus\".BatchStatus bs on b.batchSId = bs.batchSId" + 
				"		 inner join \"refStatusCode\".RefStatusCode sc on sc.statusSId = bs.statusSId" + 
				"		 order by batchId,batchStatusSId desc )m1 group by batchId" + 
				"		)m2 on bs.batchStatusSId = m2.batchStatusSId" + 
				"		inner join \"refStatusCode\".RefStatusCode sc on sc.statusSId = bs.statusSId" + 
				"	where sc.statusCd = 'PICKED') order by bs.batchStatusSId desc limit 1";
		
		ArrayList batch = (ArrayList) batchCache.query(new SqlFieldsQuery(sql).setArgs(processCode)).getAll();
		System.out.println("batch : "+batch);
		String uriString="";
		boolean emptyLink = false;
		try {
			uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
			}
			catch(Exception e) {
				emptyLink = true;
			}
		
		JSONObject sourceObj = new JSONObject(),processObj = new JSONObject();
		if(batch.size()>0) {
			ArrayList info = (ArrayList) batch.get(0);System.out.println("info : "+info);
			if(info.size()>0) {
				
				sourceObj.put("code", info.get(8));
				sourceObj.put("name", info.get(9));			
				processObj.put("code", info.get(10));
				processObj.put("name", info.get(11));
				processObj.put("desc", info.get(12));
				
				batchResponse = new BatchResponse();
				response = new SingleBatchSearchResponse();
				response.setSource(sourceObj);
				response.setProcess(processObj);
				response.setBatchNumber((Integer)info.get(0));
				response.setLoadFileId((Integer)info.get(1));
				response.setLoadFileName((String)info.get(2));
				response.setCreatedBy((String)info.get(18));	
				response.setCreatedOn((Timestamp)info.get(19));
				String statusIdSql = "select max(batchStatusSId) from \"batchStatus\".BatchStatus";		
				ArrayList idInfo = (ArrayList) batchStatusCache.query(new SqlFieldsQuery(statusIdSql)).getAll();	
				Date currentDate = new Date();
				Integer batchStatusSId = (Integer) ((ArrayList)idInfo.get(0)).get(0) + 1;
				Integer batchId = (Integer) info.get(7);
				Integer batchSId = (Integer) info.get(15);
				ResourceIdentifier batchResp = new ResourceIdentifier();
				batchResp.set_id(batchId);
				if(!emptyLink) {
					batchResponse.add(new Link(uriString,"_self"));
					Link batchLink = linkTo(methodOn(BatchInformationController.class).getLatestBatch(batchId,processCode))
				               .withRel("_idRef");
					batchResp.add(batchLink);
				}			
				response.setBatchId(batchResp);
				
				Integer merchantNbr = (Integer) info.get(13);				
				ResourceIdentifier merchantResponse = new ResourceIdentifier();
				String merchantUrl = Constants.MERCHANT_SERVICE+merchantNbr;
				merchantResponse.set_id(merchantNbr);
				merchantResponse.add(new Link(merchantUrl,"_idRef"));
				response.setMerchantId(merchantResponse);		
				
				String terminalId = (String) info.get(14);
				ResourceIdentifier terminal = new ResourceIdentifier();
				terminal.set_id(Integer.parseInt(terminalId));
				String terminalUrl = Constants.TERMINAL_SERVICE;
				terminalUrl = terminalUrl.replace("{merchantId}", merchantNbr+"")+terminalId;
				terminal.add(new Link(terminalUrl,"_idRef"));
				response.setTerminalId(terminal);	
				
				batchResponse.setBatch(response);
				System.out.println("response : "+response);
				System.out.println("batchResponse : "+batchResponse);
				
				String statusSIdSql = "select statusSId from \"refStatusCode\".RefStatusCode where statusCd='PICKED'";		
				ArrayList  refStatusCodeData= (ArrayList) refStatusCodeCache.query(new SqlFieldsQuery(statusSIdSql)).getAll();	
				Integer statusSId = (Integer) ((ArrayList)refStatusCodeData.get(0)).get(0);
				
				Timestamp createdOn=new Timestamp(currentDate.getTime());
				String createdBy = (String) info.get(3);
				Timestamp updatedOn=new Timestamp(currentDate.getTime());
				String updatedBy =(String) info.get(5);

				BatchStatus batchStatus = new BatchStatus(batchStatusSId,statusSId,batchSId,createdOn,createdBy,updatedOn,updatedBy);
				batchStatusCache.put(batchStatusSId, batchStatus);	
			}
		}
		System.out.println("batchResponse : "+batchResponse);
		return batchResponse;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResultResource getFilteredBachInformation(String source, String stage, String process,Integer offset,Integer size) throws Exception{
		ResultResource resultResource= null;
		ArrayList sourceList = new ArrayList();
		ArrayList stageList = new ArrayList();
		sourceList.add("PG");
		sourceList.add("TD");
		sourceList.add("TSYS");
		stageList.add("NEW");
		stageList.add("PICKED");
		if((source!=null && !sourceList.contains(source)) || (stage!=null && !stageList.contains(stage))    ) {
			logger.info("Invalid input for method getFilteredBachInformation()");
			throw new MeSBadRequestException();
		}

		IgniteCache<Integer, Batch> batchCache = ignite.getOrCreateCache("batch");
		batchCache.loadCache(null);

		IgniteCache<Integer, RefBatchProcessCode> refBatchProcessCodeCache = ignite.getOrCreateCache("refBatchProcessCode");
		refBatchProcessCodeCache.loadCache(null);

		IgniteCache<Integer, BatchStatus> batchStatusCache = ignite.getOrCreateCache("batchStatus");
		batchStatusCache.loadCache(null);
		
		IgniteCache<Integer, RefStatusCode> refStatusCodeCache = ignite.getOrCreateCache("refStatusCode");
		refStatusCodeCache.loadCache(null);
		
		IgniteCache<Integer, RefBatchSourceCode> refBatchSourceCodeCache = ignite.getOrCreateCache("refBatchSourceCode");
		refBatchSourceCodeCache.loadCache(null);

		StringBuffer searchBuffer = new StringBuffer(),pageBuffer = new StringBuffer();
		String selectSql= "",pageSelectSql="",tablesSql="",filterSql="",orderBy="",processUrl="",sourceUrl="",stageUrl="";
		selectSql = "select distinct b.batchId,bpc.batchProcessCd ";
		pageSelectSql = " select count(*) ";
		
		tablesSql=" from \"refBatchProcessCode\".RefBatchProcessCode bpc " + 
				"	join batch  b on b.batchProcessSId=bpc.batchProcessSId " + 
				"	left join \"refBatchSourceCode\".RefBatchSourceCode bsc on bsc.batchSourceSId = b.batchSourceSId " + 
				"	left join \"batchStatus\".BatchStatus bs on bs.batchSId = b.batchSId  " + 
				"	inner join \"refStatusCode\".RefStatusCode rfc on rfc.statusSId = bs.statusSId where ";
		
		pageBuffer.append(pageSelectSql);
		pageBuffer.append(tablesSql);
			
		searchBuffer.append(selectSql);
		searchBuffer.append(tablesSql);
		Boolean param = false;
		if(source!=null) {
			searchBuffer.append(" bsc.batchSourceCd= '"+source+"' and" );
			pageBuffer.append(" bsc.batchSourceCd= '"+source+"' and" );
			sourceUrl = "?source="+source;
			param = true;
		}
		
		if(stage!=null) {
			searchBuffer.append(" rfc.statusCd= '"+stage+"' and" );
			pageBuffer.append(" rfc.statusCd= '"+stage+"' and" );
			stageUrl = param ? "&stage="+stage : "?stage="+stage ;
			
		}
		
		if(process!=null) {
			searchBuffer.append(" bpc.batchProcessCd= '"+process+"' and " );
			pageBuffer.append(" bpc.batchProcessCd= '"+process+"' and " );
			processUrl = param ? "&process="+process : "?process="+process;
		}
		
		filterSql=" b.batchId not in " + 
				"	(select batchId from \"batchStatus\".BatchStatus bs "+
				"     inner join (select max(batchStatusSId) as batchStatusSId,batchId	from" + 
				"	    (select bs.batchStatusSId,b.batchId,sc.statusCd from batch b" + 
				"		 left join \"batchStatus\".BatchStatus bs on b.batchSId = bs.batchSId" + 
				"		 inner join \"refStatusCode\".RefStatusCode sc on sc.statusSId = bs.statusSId" + 
				"		 order by batchId,batchStatusSId desc )m1 group by batchId" + 
				"		)m2 on bs.batchStatusSId = m2.batchStatusSId" + 
				"		inner join \"refStatusCode\".RefStatusCode sc on sc.statusSId = bs.statusSId" + 
				"	where sc.statusCd = 'DELETE') ";
		
		searchBuffer.append(filterSql);
		pageBuffer.append(filterSql);
		
		orderBy = "order by batchId asc "  ;	
		searchBuffer.append(orderBy);
				
		if(size!=null)
			searchBuffer.append(" LIMIT "+size);
		else 
			size = 10;
		
		if(offset!=null)
			searchBuffer.append(" OFFSET "+offset);
		else
			offset = 0;
		
		Long rowCount=0L;
		ArrayList batchCount = (ArrayList) batchCache.query(new SqlFieldsQuery(pageBuffer.toString())).getAll();
		if(batchCount.size()>0) {
			rowCount = (Long) ((ArrayList)batchCount.get(0)).get(0);
		}
		ArrayList batchIds = (ArrayList) batchCache.query(new SqlFieldsQuery(searchBuffer.toString())).getAll();
		
		ArrayList singleBatchId = new ArrayList();
		JSONObject nextObj=new JSONObject();
		JSONObject prevObj=new JSONObject();
		String uriString = "";
		Link formattedLink = null;
		String uri ="";
		boolean linkExist = true,batchExist = false;
		try {
			uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
			uri = uriString.substring(0, uriString.indexOf("?"));
			formattedLink = new Link(uri);     
		}
		catch(Exception e) {
			linkExist = false;
		}
		if(batchIds.size()>0) {
			List<ResourceIdentifier> resultResourceList = new ArrayList<ResourceIdentifier>();
			for(int i=0; i<batchIds.size(); i++) {
				ResourceIdentifier batch = new ResourceIdentifier();
				singleBatchId = (ArrayList) batchIds.get(i);
				batch.set_id((Integer)singleBatchId.get(0));
				batch.add(linkTo(methodOn(BatchInformationController.class)
								.getLatestBatch((Integer)singleBatchId.get(0),(String)singleBatchId.get(1)))
			               		.withRel("_idRef"));
				resultResourceList.add(batch);
				batchExist = true;
				
			}
			if(batchExist) {
				resultResource = new ResultResource();
				resultResource.add(new Link(uriString,"_self"));

				if(rowCount - (offset+size) > 0) {	
					String url = linkExist ? formattedLink.getHref()+sourceUrl+stageUrl+processUrl : "";
					if(param)
						url = url+"&offset="+(offset+1)+"&size="+size;
					else
						url = url+"?offset="+(offset+1)+"&size="+size;
					nextObj.put("href", url);
					resultResource.add(new Link(url,"_next"));
				}
				if(offset>0) {
					String url = linkExist ? formattedLink.getHref()+sourceUrl+stageUrl+processUrl : "";
					if(param)
						url = url+"&offset="+(offset-1)+"&size="+size;
					else
						url = url+"?offset="+(offset-1)+"&size="+size;
					prevObj.put("href", url);
					resultResource.add(new Link(url,"_prev"));
				}
				resultResource.set_result(resultResourceList);
			}
		}
		return resultResource;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BatchResponseEntity  findBatchId(Integer batchId, String code) throws Exception {
		SingleBatchSearchResponse resEntity =new SingleBatchSearchResponse();
		BatchResponseEntity batchResponseEntity=new BatchResponseEntity();
		try {
			IgniteCache<Integer, Batch> cache = ignite.getOrCreateCache("batch");
			cache.loadCache(null);
			IgniteCache<Integer, RefBatchProcessCode> refBatchProcessCodecache = ignite.getOrCreateCache("refBatchProcessCode");
			refBatchProcessCodecache.loadCache(null);
			String sql = " select * from batch  where batchId=? " +
					" and batchProcessSId = (select batchProcessSId from \"refBatchProcessCode\".RefBatchProcessCode  where batchProcessCd=?) ";  
			ArrayList batch = (ArrayList) cache.query(new SqlFieldsQuery(sql).setArgs(batchId,code)).getAll();
			if(batch.size()>0) {
				ArrayList info = (ArrayList) batch.get(0);
				if(info.size()>0) {

					IgniteCache<Integer, RefBatchSourceCode> sourceCache = ignite.getOrCreateCache("refBatchSourceCode");
					sourceCache.loadCache(null);  
					JSONObject sourceObject=new JSONObject(); 
					JSONObject processObject=new JSONObject(); 
					
					try {
						String sqlSource = " select * from refBatchSourceCode  where batchSourceSId=?  ";  
						ArrayList source = (ArrayList) sourceCache.query(new SqlFieldsQuery(sqlSource).setArgs(Integer.parseInt(info.get(3).toString()))).getAll();
						if(source.size()>0) {                
							ArrayList infos = (ArrayList) source.get(0);
							if(infos.size()>0) {
								sourceObject.put("name",infos.get(1).toString());
								sourceObject.put("code",infos.get(2).toString());
							}
						}
					}
					catch (Exception e) {

					}
					
					
					IgniteCache<Integer, RefBatchProcessCode> processCache = ignite.getOrCreateCache("refBatchProcessCode");
					processCache.loadCache(null);
					try {
						String processSql = " select * from refBatchProcessCode  where batchProcessCd=?  ";		
						@SuppressWarnings("rawtypes")
						ArrayList process = (ArrayList) processCache.query(new SqlFieldsQuery(processSql).setArgs(code)).getAll();
						if(process.size()>0) {
							@SuppressWarnings("rawtypes")
							
							ArrayList processInfo = (ArrayList) process.get(0);
							if(processInfo.size()>0) {
								processObject.put("code",processInfo.get(1).toString());
								processObject.put("name",processInfo.get(2).toString());
								processObject.put("desc",processInfo.get(3).toString());


							}
						}
					}
					catch (Exception e) {
						logger.info(" Invalid batchProcessCd");
						throw new MeSBadRequestException();
					}
					
					ResourceIdentifier batchResourceIdentifier=new ResourceIdentifier();
					batchResourceIdentifier.set_id(batchId);
					batchResourceIdentifier.add(new Link( ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString()).withRel("_self"));
					ResourceIdentifier merchantResourceIdentifier=new ResourceIdentifier();
					merchantResourceIdentifier.set_id(Integer.parseInt(info.get(11).toString()));
					merchantResourceIdentifier.add(new Link(Constants.MERCHANT_SERVICE+info.get(11).toString()).withRel("_self"));
					ResourceIdentifier terminalResourceIdentifier=new ResourceIdentifier();
					terminalResourceIdentifier.set_id(Integer.parseInt(info.get(12).toString()));
					terminalResourceIdentifier.add(new Link(Constants.TERMINAL_SERVICE+ info.get(11).toString()+"/terminals/"+info.get(12).toString()).withRel("_self"));
					resEntity.setCreatedBy(info.get(7).toString());
					resEntity.setCreatedOn(Timestamp.valueOf(info.get(8).toString()));
					resEntity.setLoadFileName(info.get(6).toString());
					resEntity.setLoadFileId(Integer.parseInt(info.get(5).toString()));
					resEntity.setBatchNumber(Integer.parseInt(info.get(4).toString()));
					resEntity.setBatchId(batchResourceIdentifier);
					resEntity.setMerchantId(merchantResourceIdentifier);
					resEntity.setTerminalId(terminalResourceIdentifier);
					resEntity.setSource(sourceObject);
					resEntity.setProcess(processObject);
					batchResponseEntity.add(new Link( ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString()).withRel("_self"));
					batchResponseEntity.set_result(resEntity);
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
			return batchResponseEntity ;
		}
		return  batchResponseEntity ;
	}
	
	@SuppressWarnings("rawtypes")
	public Integer getSourceCode(String sourceCode) {
		Integer batchSourceSId = null;
		IgniteCache<Integer, RefBatchSourceCode> sourceCache = ignite.getOrCreateCache("refBatchSourceCode");
		sourceCache.loadCache(null);
		try {
			String sql = " select * from refBatchSourceCode  where batchSourceCd=?  ";		
			ArrayList batch = (ArrayList) sourceCache.query(new SqlFieldsQuery(sql).setArgs(sourceCode)).getAll();
			if(batch.size()>0) {
				ArrayList info = (ArrayList) batch.get(0);
				if(info.size()>0) {
					batchSourceSId=Integer.valueOf(info.get(0).toString());		
				}
			}
		}
		catch (Exception e) {
			logger.info(" Invalid batchSourceCd");
			throw new MeSBadRequestException();
		}
		return batchSourceSId;
	}
	
	public Integer getProcessCode(String processCode) {
		Integer batchProcessSId = null;
		IgniteCache<Integer, RefBatchProcessCode> processCache = ignite.getOrCreateCache("refBatchProcessCode");
		processCache.loadCache(null);
		try {
			String sql = " select * from refBatchProcessCode  where batchProcessCd=?  ";		
			@SuppressWarnings("rawtypes")
			ArrayList batch = (ArrayList) processCache.query(new SqlFieldsQuery(sql).setArgs(processCode)).getAll();
			if(batch.size()>0) {
				@SuppressWarnings("rawtypes")
				ArrayList info = (ArrayList) batch.get(0);
				if(info.size()>0) {
					batchProcessSId=Integer.valueOf(info.get(0).toString());
				}
			}
		}
		catch (Exception e) {
			logger.info(" Invalid batchProcessCd");
			throw new MeSBadRequestException();
		}
		return batchProcessSId;
	}
	
	@SuppressWarnings("rawtypes")
	public BatchResEntity  save(BatchList batches) throws Exception {
		BatchResEntity batchResEntity=new BatchResEntity();
		try {
			IgniteCache<Integer, Batch> cache = ignite.getOrCreateCache("batch");
			cache.loadCache(null);
			Map<Integer, Batch> batchMap = new HashMap<Integer, Batch>();
			List<ResourceIdentifier> batchIdentifier =new ArrayList<ResourceIdentifier>();  
			for (Batch batch : batches.getBatchList()) {
				IgniteCache<Integer, RefBatchProcessCode> processCache = ignite.getOrCreateCache("refBatchProcessCode");
				processCache.loadCache(null);
				String processCode=null;
				try {
					String sqlProcess = " select * from refBatchProcessCode  where batchProcessSId=?";    
					ArrayList process = (ArrayList) processCache.query(new SqlFieldsQuery(sqlProcess).setArgs(batch.getBatchProcessSId())).getAll();
					if(process.size()>0) {
						ArrayList infos = (ArrayList) process.get(0);
						if(infos.size()>0) {
							processCode=infos.get(1).toString();
						}
					}
				}
				catch (Exception e) {  
					logger.info(" Invalid batchProcessSId");
					throw new MeSBadRequestException();
				}
				ResourceIdentifier resourceIdentifier=new ResourceIdentifier();
				resourceIdentifier.add(new Link(Constants.BATCH_SERVICE+batch.getBatchId()+"/process/"+processCode).withRel("_self"));
				if (batch != null) {
					IgniteCache<Integer, Batch> cacheb = ignite.getOrCreateCache("batch");
					cacheb.loadCache(null);
					Integer batchSId=1;
					String sqlQueryBatch = "select max(bs.batchSId) from batch bs";
					List batchlist = (ArrayList) cacheb.query(new SqlFieldsQuery(sqlQueryBatch)).getAll();
					if (batchlist  != null && batchlist .size() > 0) {
						List list  = (ArrayList) batchlist.get(0);
						if (list  != null && list .size() > 0) {
							try {      
								batchSId+=Integer.valueOf(list.get(0).toString());
							}
							catch(Exception e){
								logger.info(" Number format exception");
								throw new MeSDbConnectionException("Number format exception");
							}
						}
					}
					Date currentDate = new Date();
					Timestamp createdOn=new Timestamp(currentDate.getTime());
					Timestamp updatedOn=new Timestamp(currentDate.getTime());
					batch.setCreatedBy("MeS");
					batch.setCreatedOn(createdOn);
					batch.setBatchSId(batchSId);
					batch.setUpdatedBy("Mes");
					batch.setUpdatedOn(updatedOn);
					resourceIdentifier.set_id(batch.getBatchId());
					batchMap.put(batchSId, batch);
					cache.put(batchSId, batch);
					batchIdentifier.add(resourceIdentifier);
					cache.get(batchSId);
					IgniteCache<Integer, BatchStatus> batchStatus = ignite.getOrCreateCache("batchStatus");
					batchStatus.loadCache(null);
					Integer batchStatusSId=null;
					String sqlQuery = "select max(bs.batchStatusSId) from batchStatus bs";
					List batchStatuslist = (ArrayList) batchStatus.query(new SqlFieldsQuery(sqlQuery)).getAll();
					if (batchStatuslist  != null && batchStatuslist .size() > 0) {
						List Statuslist  = (ArrayList) batchStatuslist.get(0);
						if (Statuslist  != null && Statuslist .size() > 0) {
							try {
								batchStatusSId=Integer.valueOf(Statuslist.get(0).toString());
							}
							catch(Exception e){
								logger.info(" Number format exception");
								throw new MeSDbConnectionException("Number format exception");
							}
						}
					}

					BatchStatus batchStatusObj=new BatchStatus();
					batchStatusObj.setBatchStatusSId(batchStatusSId+1);
					batchStatusObj.setBatchSId(batchSId);
					batchStatusObj.setStatusSId(1001);
					batchStatusObj.setCreatedBy("Mes");
					batchStatusObj.setUpdatedBy("Mes");
					batchStatusObj.setCreatedOn(createdOn);
					batchStatusObj.setUpdatedOn(updatedOn);
					IgniteCache<Integer, BatchStatus> batchStatusInsert = ignite.getOrCreateCache("batchStatus");
					batchStatus.loadCache(null);
					batchStatusInsert.put(batchStatusSId+1,batchStatusObj);
				}
			}
			 batchResEntity.setBatchId(batchIdentifier);
			 batchResEntity.add(new Link( ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString()).withRel("_self"));
		}
		catch(Exception e)
		{
			return batchResEntity;
		}
		return  batchResEntity ;
	}

	@SuppressWarnings("rawtypes")
	public Boolean deleteBatch(int batchId) {
		Boolean status = false;
		try {
			IgniteCache<Integer, Batch> cacheb = ignite.getOrCreateCache("batch");
			cacheb.loadCache(null);

			Integer batchSId=null;
			String sqlQueryBatch = "select * from batch  where batchId=? ";
			List batchlist = (ArrayList) cacheb.query(new SqlFieldsQuery(sqlQueryBatch).setArgs(batchId)).getAll();
			if (batchlist  != null && batchlist .size() > 0) {
				List list  = (ArrayList) batchlist.get(0);
				if (list  != null && list .size() > 0) {
					try {
						batchSId=Integer.valueOf(list.get(0).toString());
					}
					catch(Exception e){
						logger.info(" Number format exception");
						throw new MeSDbConnectionException("Number format exception");
					}
				}
			}

			IgniteCache<Integer, BatchStatus> batchStatus = ignite.getOrCreateCache("batchStatus");
			batchStatus.loadCache(null);

			Integer batchStatusSId=null;
			String sqlQuery = "select max(bs.batchStatusSId) from batchStatus bs";
			List batchStatuslist = (ArrayList) batchStatus.query(new SqlFieldsQuery(sqlQuery)).getAll();

			if (batchStatuslist  != null && batchStatuslist .size() > 0) {
				List Statuslist  = (ArrayList) batchStatuslist.get(0);
				if (Statuslist  != null && Statuslist .size() > 0) {
					try {
						batchStatusSId=Integer.valueOf(Statuslist.get(0).toString());
					}
					catch(Exception e){
						logger.info(" Number format exception");
						throw new MeSDbConnectionException("Number format exception");
					}
				}
			}

			BatchStatus batchStatusObj=new BatchStatus();
			batchStatusObj.setBatchStatusSId(batchStatusSId+1);
			batchStatusObj.setBatchSId(batchSId);
			batchStatusObj.setStatusSId(1004);
			batchStatusObj.setCreatedBy("Mes");
			batchStatusObj.setUpdatedBy("Mes");
			Date currentDate = new Date();
			Timestamp createdOn=new Timestamp(currentDate.getTime());
			Timestamp updatedOn=new Timestamp(currentDate.getTime());
			batchStatusObj.setCreatedOn(createdOn);
			batchStatusObj.setUpdatedOn(updatedOn);

			IgniteCache<Integer, BatchStatus> batchStatusInsert = ignite.getOrCreateCache("batchStatus");
			batchStatus.loadCache(null);
			batchStatusInsert.put(batchStatusSId+1,batchStatusObj);
			status=true;

		} catch (Exception e) {
			return status;
		}
		return status;
	}

	@SuppressWarnings("rawtypes")
	public ExecutionResponse getBatchProcessInformation(int batchId, String code) throws Exception {

		IgniteCache<Integer, Batch> batchCache = ignite.getOrCreateCache("batch");
		batchCache.loadCache(null);

		IgniteCache<Integer, RefBatchProcessCode> refBatchProcesscache = ignite.getOrCreateCache("refBatchProcessCode");
		refBatchProcesscache.loadCache(null);

		IgniteCache<Integer, ExecutionDetails> executionDetailscache = ignite.getOrCreateCache("executionDetails");
		executionDetailscache.loadCache(null);

		IgniteCache<Integer, BatchExecution> batchExecution = ignite.getOrCreateCache("batchExecution");
		batchExecution.loadCache(null);

		String sql = "select b.batchId,ed.executionId from batch as b left join \"refBatchProcessCode\".RefBatchProcessCode  bpc "
				+ " on b.batchProcessSid = bpc.batchProcessSid left join \"batchExecution\".BatchExecution rbe "
				+ " on b.batchSid = rbe.batchSid left join \"executionDetails\".ExecutionDetails ed  "
				+ " on rbe.executionSId = ed.executionSId where b.batchId = ? and bpc.batchProcessCd = ? ";

		List batch = (ArrayList) batchCache.query(new SqlFieldsQuery(sql).setArgs(batchId, code)).getAll();

		BatchProcessInfoResponse response = new BatchProcessInfoResponse();
		ExecutionResponse executionResponse = new ExecutionResponse();
		List<ResourceIdentifier> executionList = new ArrayList<ResourceIdentifier>();

		final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
		final String executionUriString = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

		if (batch != null && batch.size() > 0) {
			List batchIdList = (ArrayList) batch.get(0);
			int bId = (int) batchIdList.get(0);

			ResourceIdentifier batchIds = new ResourceIdentifier();
			batchIds.set_id(bId);
			Link batchLink = linkTo(methodOn(BatchInformationController.class).getLatestBatch(batchId,code))
		               .withRel("_idRef");
			batchIds.add(batchLink);
			//batchIds.add(new Link(executionUriString+"/v1/data/batches/1003/process/FL01").withRel("_idRef"));
			response.setBatchId(batchIds);

			for (Object batchItem : batch) {
				List executionIdList = (ArrayList) batchItem;
				if (executionIdList != null && executionIdList.size() > 0) {
					ResourceIdentifier executionId = new ResourceIdentifier();

					if (executionIdList.get(1) != null)
						executionId.set_id((int) executionIdList.get(1));
					executionId.add(new Link(executionUriString + "/v1/data/execution/" + executionId.get_id())
							.withRel("_idRef"));
					executionList.add(executionId);

				}
			}
		}

		response.setExecutions(executionList);
		executionResponse.setExecutionInstances(response);
		executionResponse.add(new Link(uriString).withRel("_self"));

		return executionResponse;
	}

	public LinkResource addBatchData(int batchId, String code, int executionId) {

		LinkResource linkResource = new LinkResource();
		IgniteCache<Integer, Batch> batchCache = ignite.getOrCreateCache("batch");
		batchCache.loadCache(null);

		IgniteCache<Integer, RefBatchProcessCode> refBatchProcesscache = ignite.getOrCreateCache("refBatchProcessCode");
		refBatchProcesscache.loadCache(null);

		IgniteCache<Integer, ExecutionDetails> executionDetailscache = ignite.getOrCreateCache("executionDetails");
		executionDetailscache.loadCache(null);

		IgniteCache<Integer, BatchExecution> refBatchExecutioncache = ignite.getOrCreateCache("batchExecution");
		refBatchExecutioncache.loadCache(null);

		String sql = "select b.batchSId from batch as b left join \"refBatchProcessCode\".RefBatchProcessCode  bpc "
				+ " on b.batchProcessSid = bpc.batchProcessSid  where b.batchId = ? and bpc.batchProcessCd = ? ";
		List<?> batchSId = (ArrayList<?>) batchCache.query(new SqlFieldsQuery(sql).setArgs(batchId, code)).getAll();

		try 
		{
			if (batchSId != null && batchSId.size() > 0) {
				List<?> batchIdListItem = (ArrayList<?>) batchSId.get(0);
				int bId = (int) batchIdListItem.get(0);

				String sql1 = "select ed.executionSId,ed.createdBy,ed.createdOn,ed.updatedBy,ed.updatedOn from \"executionDetails\".ExecutionDetails ed where ed.executionId = ?";
				List<?> executionDetails = (ArrayList<?>) executionDetailscache
						.query(new SqlFieldsQuery(sql1).setArgs(executionId)).getAll();

				if (executionDetails != null && executionDetails.size() > 0) {
					String createdBy = null;
					Timestamp createdOn = null;
					List<?> executionDataListItem = (ArrayList<?>) executionDetails.get(0);
					int eId = (int) executionDataListItem.get(0);
					if (executionDataListItem.get(1) != null)
						createdBy = executionDataListItem.get(1).toString();
					if (executionDataListItem.get(2) != null)
						createdOn = (Timestamp) executionDataListItem.get(2);

					int batcExecutionSid = generateBatchExecutionId();

					BatchExecution refBatchExecution = new BatchExecution(batcExecutionSid, eId, bId, createdBy, createdOn);

					refBatchExecutioncache.put(batcExecutionSid, refBatchExecution);
					refBatchExecutioncache.clear(batcExecutionSid);

					if(refBatchExecutioncache.get(batcExecutionSid) == null) {
						throw new MeSDbConnectionException("Insertion failed");
					}

					linkResource.add(linkTo(methodOn(BatchInformationController.class).getLatestBatch(batchId,code))
							.withRel("batch"));
					String executionTrackerUrl = Constants.PROCESS_EXECUTION_SERVICE+executionId;
					linkResource.add(new Link(executionTrackerUrl,"executionTracker"));
					String executionHistoryUrl = Constants.BATCH_SERVICE+batchId+"/process;code="+code+"/executions";
					linkResource.add(new Link(executionHistoryUrl,"executionHistory"));
				} 
			}
			else
			{
				throw new MeSNotFoundException("Execution Id not found");	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return linkResource;
	}

	private int generateBatchExecutionId() {
		// TODO Auto-generated method stub
		IgniteCache<Integer, BatchExecution> executionDetailsache = ignite.getOrCreateCache("batchExecution");
		executionDetailsache.loadCache(null);

		Integer id = 1;

		String sql = "select max(ex.batchExecutionSId) from BatchExecution ex";
		List<?> executionDetailsList = (ArrayList<?>) executionDetailsache.query(new SqlFieldsQuery(sql)).getAll();
		if (executionDetailsList != null && executionDetailsList.size() > 0) {
			List<?> executionDetils = (ArrayList<?>) executionDetailsList.get(0);
			if (executionDetils != null && executionDetils.size() > 0) {
				if (executionDetils.get(0) != null)
					id = Integer.valueOf(executionDetils.get(0).toString()) + 1;

			}
		}
		return id;
	}
}
