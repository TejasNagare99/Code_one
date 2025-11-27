package com.excel.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.excel.bean.ErpBatchBean;
import com.excel.bean.ErpBatch_stk_recobean;
import com.excel.bean.ErpDispatchBean;
import com.excel.bean.ErpDispatchCancel;
import com.excel.bean.ErpGrnBean;
import com.excel.bean.ErpGrnConfirmationBean;
import com.excel.bean.ErpIaaBean;
import com.excel.bean.ErpIaaGrn_bean;
import com.excel.bean.ErpNormal_Iaabean;
import com.excel.bean.ErpProductBean;
import com.excel.bean.ErpStockAdjBean;
import com.excel.bean.ErpStockwithdrawalBean;
import com.excel.bean.Erp_Batch_stk_reco_quarantinebean;
import com.excel.bean.Erp_Wms_Iaa_confirmationbean;
import com.excel.bean.Erp_tran_stock_Quarantine_recobean;
import com.excel.bean.Erp_tran_stock_recobean;
import com.excel.bean.IAABean;
import com.excel.bean.ProductBean;
import com.excel.exception.MedicoException;
import com.excel.model.Am_m_System_Parameter;
import com.excel.model.BatchWithholdReleaseDTL;
import com.excel.model.BatchWithholdReleaseHDR;
import com.excel.model.Erp_Batch_stk_reco;
import com.excel.model.Erp_Batch_stk_reco_quarantine;
import com.excel.model.Erp_tran_wise_Quarantine_reco;
import com.excel.model.Erp_Batch_stk_reco;
import com.excel.model.Erp_tran_wise_reco;
import com.excel.model.Period;
import com.excel.model.SmpBatch;
import com.excel.model.SmpBatchNS;
import com.excel.model.SmpItem;
import com.excel.model.Stock_Adj_Details;
import com.excel.model.Stock_Adj_Header;
import com.excel.model.Trans_Key_Master;
import com.excel.model.Trans_key_Master_batch_wthrel;
import com.excel.repository.BatchMasterRepository;
import com.excel.repository.ErpRepository;
import com.excel.repository.IAARepository;
import com.excel.repository.ProductMasterRepository;
import com.excel.repository.SystemParameterRepository;
import com.excel.repository.TransKeyMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ErpServiceImpl implements ErpService,MedicoConstants{
	
	@Autowired ErpRepository erpRepository;
	@Autowired IAARepository iAARepository;
	@Autowired IAAService iAAService;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired SystemParameterRepository systemParameterRepository;
	@Autowired TransKeyMasterRepository transKeyMasterRepository;
	@Autowired TaskMasterService taskMasterService;
	@Autowired ProductMasterRepository productMasterRepository;
	@Autowired BatchMasterRepository batchMasterRepository;
	@Autowired RestTemplate restTemplate;
	
	public static String  key="bWVkaWNvLndlYnNlcnZpY2U6cGFzcw==";
	public static String url="https://erp.pispl.in";
	SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sdf2  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	SimpleDateFormat sdf3  = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getproductDetails(String year) {
		Map<String, Object> map=null;
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
		    	String divid="9";
				List<ErpProductBean> bean = this.erpRepository.getProducts(divid);
				count=bean.size();
				JSONArray data = new JSONArray();
				JSONObject object=null;
				if(bean!=null && bean.size()>0) {
					for(ErpProductBean product:bean) {
						object=new JSONObject();
						object.put("org",product.getOrg());//PIL
						object.put("productcode",product.getProductcode().trim());
						object.put("productname",product.getProductname());
						object.put("UOM",product.getUOM());
						if(product.getOrg().equals("PIL"))
							object.put("productcategory","PIL Medico");
						else
							object.put("productcategory","PPIPL Medico");
						object.put("description",product.getDescription());
						object.put("batchno",product.getBatchno());
						object.put("expirydate",product.getExpirydate());
						object.put("brand",product.getBrand());
						object.put("weight",String.valueOf(product.getWeight()));
						object.put("uomforWeight",product.getUomforWeight());
						object.put("shelfHeight",String.valueOf(product.getShelfHeight()));
						object.put("shelfWidth",String.valueOf(product.getShelfWidth()));
						object.put("shelfDepth",String.valueOf(product.getShelfDepth()));
						object.put("producttype",product.getProducttype());
						object.put("storageCondition",product.getStorageCondition());
						data.put(object);
					}
					finalObject.put("SKUMaster", data);
					HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
					ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
					interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
					interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
					restTemplate.setInterceptors(interceptors);
					String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.SKUMaster",request,String.class);
					System.out.println("result" +result);
					JSONObject resultJson = new JSONObject(result);
					//Insert into Erp_product
					String failedSku=resultJson.get("FailedSKU").toString().replace(" ", "");
					String[] failed=failedSku.split(",");
					List<String> failedList=new ArrayList<>();
					failedList=Arrays.asList(failed);
					this.erpRepository.updateErpProduct(divid,failedList);
				}
			
		}
		catch (Exception e) {
			count=0;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getbatchDetails(String year) {
		Map<String, Object> map=null;
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
		
			List<ErpBatchBean> bean = this.erpRepository.getBatch();
			count=bean.size();
			JSONArray data = new JSONArray();
			JSONObject object=null;
			if(bean!=null && bean.size()>0) {
				for(ErpBatchBean batch:bean) {
					object=new JSONObject();
					object.put("org",batch.getOrg());
					object.put("sku", batch.getSku().trim());
					object.put("lot",batch.getLot().trim());
					object.put("manufacturingDate",batch.getManufacturingDate());
					object.put("expiryDate",batch.getExpiryDate());
					data.put(object);
				}
				finalObject.put("batches", data);
				HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
				ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
				interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
				interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
				restTemplate.setInterceptors(interceptors);
				String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.BatchMaster",request,String.class);
				System.out.println("result" +result);
				JSONObject resultJson = new JSONObject(result);
				//insert into Erp_batch
				String failedSku=resultJson.get("FailedBatch").toString();
				String[] failed=failedSku.split(",");
				List<String> failedList=new ArrayList<>();
				failedList=Arrays.asList(failed);
				this.erpRepository.updateErpBatch(failedList);
			}
			
		}
		catch (Exception e) {
			count=0;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getGrnDetails(String year) {
		Map<String, Object> map=null;
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
			List<ErpGrnBean> bean = this.erpRepository.getGrn(year);
			List<String> selectedGrnIds=new ArrayList<>();
			count=bean.size();
			JSONArray data = new JSONArray();
			JSONObject object=null;
			JSONObject lines=null;
			JSONArray linesArray = null;
			String refNo="new";
			String oldRefNo="old";
			boolean setObject=false;
			int i=1;
			if(bean!=null && bean.size()!=0) {
				for(ErpGrnBean grn:bean) {
					refNo=grn.getDocument_no();
	
						if(!oldRefNo.equals(refNo) && setObject==true) {
							object.put("lines", linesArray);
							data.put(object);
						}
						
						if(!oldRefNo.equals(refNo)) {
							object=new JSONObject();
							linesArray=new JSONArray();
							object.put("Organization",grn.getOrganization());
							object.put("prodtype",grn.getProdtype());
							object.put("storageCondition", grn.getStorageCondition());
							object.put("documentType",grn.getDocumentType());
							object.put("warehouse","Quarantine");
							object.put("vendor", grn.getVendor_name());//vendor name
							object.put("refNo", grn.getRefNo());
							object.put("GRNId", grn.getDocument_no());
							selectedGrnIds.add(grn.getDocument_no());
						}
						lines=new JSONObject();
						lines.put("lineNo",grn.getLineNo());
						lines.put("sku",grn.getSku().trim());
						lines.put("qty",grn.getQty().toString());
						lines.put("lotNo",grn.getLotNo());
						lines.put("expiryDate", grn.getExpiryDate());
						lines.put("stockType",grn.getWarehouse());
						linesArray.put(lines);
			
						oldRefNo=grn.getDocument_no();
						setObject=true;
						i++;
					}
					object.put("lines", linesArray);
					data.put(object);
					
					JSONObject obj=new JSONObject();
					obj.put("ibd", data);
					JSONArray array=new JSONArray();
					array.put(obj);
					finalObject.put("ibds", array);
					HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
					System.out.println("Final Object "+finalObject);
					System.out.println("Grn count "+bean.size());
					ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
					interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
					interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
					restTemplate.setInterceptors(interceptors);
					String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.IBD",request,String.class);
					System.out.println("result" +result);
					JSONObject resultJson = new JSONObject(result);
					//Insert into GRN Confirmation
					if(!resultJson.get("Status").toString().equalsIgnoreCase("Error")) {
						String failedGrn=resultJson.get("FailedReferenceNo").toString();
						System.out.println("FailedReferenceNo" +failedGrn);
						String[] failed=failedGrn.split(",");
						List<String> failedList=new ArrayList<>();
						failedList=Arrays.asList(failed);
						selectedGrnIds.removeAll(failedList);
						if(selectedGrnIds.size()>0) {
							this.update_erpgrn(selectedGrnIds,year);
						}
					}
				}
			
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getGrnconfirmationDetails(String year) {
		Map<String, Object> map=null;
		List<String> selectedGrnIds=new ArrayList<>();
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
			List<ErpGrnConfirmationBean> bean = this.erpRepository.getGrnConfirmation(year);
			count=bean.size();
			JSONArray data = new JSONArray();
			JSONObject object=null;
			JSONObject lines=null;
			JSONArray linesArray = null;
			String refNo="new";
			String oldRefNo="old";
			boolean setObject=false;
			if(bean!=null && bean.size()>0) {
				for(ErpGrnConfirmationBean grn:bean) {
					refNo=grn.getIbd_no();

					if(!oldRefNo.equals(refNo) && setObject==true) {
						object.put("lines", linesArray);
						data.put(object);
					}
					
					if(!oldRefNo.equals(refNo)) {
						object=new JSONObject();
						linesArray=new JSONArray();
						object.put("Organization",grn.getOrganization());
						object.put("confirmationFlag","GRN");
						object.put("documentNo",grn.getReference_no());
						object.put("wmsReferenceNo",grn.getReference_no());
						object.put("storageCondition",grn.getStorage_condition());
						object.put("description",grn.getDescription());
						object.put("GRNId", grn.getIbd_no());
						selectedGrnIds.add(grn.getIbd_no());
					}
					lines=new JSONObject();
					lines.put("sku",grn.getProduct().trim());
					lines.put("qty",String.valueOf(grn.getQuantity().setScale(0)));
					lines.put("lotNo",grn.getBatch());
					lines.put("toStockType",grn.getStockType());
					lines.put("reasonCode",grn.getReason_code());
					linesArray.put(lines);
		
					oldRefNo=grn.getIbd_no();
					setObject=true;
					
					}
					object.put("lines", linesArray);
					data.put(object);
					
					JSONObject obj=new JSONObject();
					obj.put("grn", data);
					JSONArray array=new JSONArray();
					array.put(obj);
					finalObject.put("grns", array);
					HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
					System.out.println("Final Object "+finalObject);
					System.out.println("Grn count "+bean.size());
					ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
					interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
					interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
					restTemplate.setInterceptors(interceptors);
					String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.GRN_IAAConfirmation",request,String.class);
					System.out.println("result" +result);
					JSONObject resultJson = new JSONObject(result);
					//Insert into GRN Confirmation
					if(!resultJson.get("Status").toString().equalsIgnoreCase("Error")) {
						String failedGrn=resultJson.get("FailedGRNs").toString();
						System.out.println("failedGrn" +failedGrn);
						String[] failed=failedGrn.split(",");
						List<String> failedList=new ArrayList<>();
						failedList=Arrays.asList(failed);
						selectedGrnIds.removeAll(failedList);
						if(selectedGrnIds.size()>0) {
							this.update_erpgrnconfirmation(selectedGrnIds,year);
						}
					}
				}
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getIaaGrnDetails(String year) {
		Map<String, Object> map=null;
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
			List<ErpIaaGrn_bean> bean = this.erpRepository.getErp_IaaGrndata(year);
			count=bean.size();
			JSONArray data = new JSONArray();
			JSONObject object=null;
			JSONObject lines=null;
			JSONArray linesArray = null;
			String refNo="new";
			String oldRefNo="old";
			boolean setObject=false;
			if(bean!=null && bean.size()>0) {
				for(ErpIaaGrn_bean iaa:bean) {
					refNo=iaa.getAdjustment_No().toString();

					if(!oldRefNo.equals(refNo) && setObject==true) {
						object.put("lines", linesArray);
						data.put(object);
					}
					
					if(!oldRefNo.equals(refNo)) {
						object=new JSONObject();
						linesArray=new JSONArray();
						object.put("Organization",iaa.getOrganization());
						object.put("confirmationFlag","IAA");
						object.put("documentNo",iaa.getStk_adj_no());
						object.put("wmsReferenceNo",iaa.getWms_Reference_No());
						object.put("storageCondition",iaa.getIn_storage_condition());
						object.put("description",iaa.getDescription());
						object.put("reasonCode",iaa.getReasonCode());
						object.put("GRNId",String.valueOf(iaa.getAdjustment_No()));
						}
					lines=new JSONObject();
					lines.put("sku",iaa.getIn_Product().trim());
					lines.put("qty",String.valueOf(iaa.getIn_Qty().setScale(0)));
					lines.put("lotNo",iaa.getIn_Batch());
					lines.put("toStockType",iaa.getIn_Stock_type());
					linesArray.put(lines);
		
					oldRefNo=iaa.getAdjustment_No().toString();
					setObject=true;
				}
				object.put("lines", linesArray);
				data.put(object);
				
				JSONObject obj=new JSONObject();
				obj.put("grn", data);
				JSONArray array=new JSONArray();
				array.put(obj);
				finalObject.put("grns", array);
				HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
				System.out.println("Final Object "+finalObject);
				System.out.println("count "+bean.size());
				ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
				interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
				interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
				restTemplate.setInterceptors(interceptors);
				String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.GRN_IAAConfirmation",request,String.class);
				System.out.println("result" +result);
				JSONObject resultJson = new JSONObject(result);
				//Insert into GRN Confirmation
				if(!resultJson.get("Status").toString().equals("Error")) {
					String failedGrn=resultJson.get("FailedGRNs").toString();
					System.out.println("failedGrn" +failedGrn);
					String[] failed=failedGrn.split(",");
					List<String> failedList=new ArrayList<>();
					failedList=Arrays.asList(failed);
					this.erpRepository.update_erpIaaGrndata(failedList,year);
				}
			}
			
		}
		catch (Exception e) {
			count=0;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getDispatchDetails(String finYear) {
		Map<String, Object> map=null;
		List<String> selectedDspIds=new ArrayList<>();
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
			List<ErpDispatchBean> bean = this.erpRepository.getDispatch(finYear);
			count=bean.size();
			JSONArray data = new JSONArray();
			JSONObject object=null;
			JSONObject lines=null;
			JSONArray linesArray = null;
			String refNo="new";
			String oldRefNo="old";
			boolean setObject=false;
			if(bean!=null && bean.size()>0) {
				for(ErpDispatchBean dispatch:bean) {
					refNo=dispatch.getObd_header_num();

					if(!oldRefNo.equals(refNo) && setObject==true) {
						object.put("lines", linesArray);
						data.put(object);
					}
					
					if(!oldRefNo.equals(refNo)) {
						object=new JSONObject();
						linesArray=new JSONArray();
						object.put("Organization",dispatch.getOrganization());
						object.put("prodtype", dispatch.getProduct_type());
						object.put("storageCondition",dispatch.getStorage_condition());
						object.put("documentType",dispatch.getDocument_type());
						object.put("bpartner",dispatch.getBusiness_partner());
						object.put("warehouse",dispatch.getWarehouse());//stock type id
						object.put("orderRefNo",dispatch.getReference_no_po());
						object.put("scheduleDelDate",dispatch.getExpected_delivery_datetime());
						object.put("description",dispatch.getDescription());
						object.put("billTo",dispatch.getBill_to());
						object.put("shipTo",dispatch.getShip_to_());
						object.put("complete", "FALSE");
						object.put("dispatchID",dispatch.getObd_header_num());
						selectedDspIds.add(dispatch.getObd_header_num());
					}
					lines=new JSONObject();
					lines.put("sku",dispatch.getProduct().trim());
					lines.put("qty",dispatch.getSales_order_qty().toString().trim());
					lines.put("lotNo",dispatch.getLot_no().trim());
					lines.put("expiryDate",dispatch.getExpirydate().trim());
					linesArray.put(lines);
		
					oldRefNo=dispatch.getObd_header_num();
					setObject=true;
				}
				object.put("lines", linesArray);
				data.put(object);
				JSONObject obj=new JSONObject();
				obj.put("obd", data);
				JSONArray array=new JSONArray();
				array.put(obj);
				finalObject.put("obds", array);
				HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
				System.out.println("Final Object "+finalObject);
				System.out.println("Dispatch count "+bean.size());
				ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
				interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
				interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
				restTemplate.setInterceptors(interceptors);
				String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.OBD",request,String.class);
				System.out.println("result" +result);
				JSONObject resultJson = new JSONObject(result);
				if(!resultJson.get("Status").toString().equals("Error")) {
					//insert into Erp_batch
					System.out.println("Failed "+resultJson.get("FailedOBD").toString());
					String failedDispatch=resultJson.get("FailedOBD").toString();
					System.out.println("failedDispatch "+failedDispatch);
//					String[] failed=failedDispatch.split(",");
//					List<String> failedList=new ArrayList<>();
//					failedList=Arrays.asList(failed);
//					selectedDspIds.removeAll(failedList);
					if(selectedDspIds.size()>0) {
						this.erpRepository.updateErpDispatch(finYear,selectedDspIds);
					}
				}
				
			}
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getdispatchCancelDetails(String finYear) {
		Map<String, Object> map=null;
		List<String> selectedDspIds=new ArrayList<>();
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
			List<ErpDispatchCancel> bean = this.erpRepository.getDispatchCancel(finYear);
			count=bean.size();
			JSONArray data = new JSONArray();
			JSONObject object=null;
			if(bean!=null && bean.size()>0) {
				for(ErpDispatchCancel dspCancel:bean) {
					object=new JSONObject();
					object.put("Organization",dspCancel.getOrganization());
					object.put("stroageCondition",dspCancel.getStorage_condition());
					object.put("documentType",dspCancel.getDocument_type());
					object.put("businessPartner",dspCancel.getBusiness_partner());
					object.put("OrderNo",dspCancel.getReference_no_po());
					object.put("dispatchID",dspCancel.getObd_header_num());
					object.put("description",dspCancel.getDescription().trim());
					data.put(object);
					selectedDspIds.add(dspCancel.getObd_header_num());
				}
				
				JSONObject obj=new JSONObject();
				obj.put("obd", data);
				JSONArray array=new JSONArray();
				array.put(obj);
				finalObject.put("obds", array);
				HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
				System.out.println("Final Object "+finalObject);
				System.out.println("Dispatch count "+bean.size());
				ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
				interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
				interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
				restTemplate.setInterceptors(interceptors);
				String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.OBDCancellation",request,String.class);
				System.out.println("result" +result);
				JSONObject resultJson = new JSONObject(result);
				if(!resultJson.get("Status").toString().equalsIgnoreCase("Error")) {
					//insert into ERP_DISPATCH_CANCEL
					System.out.println("Failed "+resultJson.get("FailedRef").toString());
					String failedDispatch=resultJson.get("FailedRef").toString();
					System.out.println("failedDispatch "+failedDispatch);
					String[] failed=failedDispatch.split(",");
					List<String> failedList=new ArrayList<>();
					failedList=Arrays.asList(failed);
					selectedDspIds.removeAll(failedList);
					if(selectedDspIds.size()>0) {
						this.erpRepository.updateErpDispatchCancel(selectedDspIds,finYear);
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, "error");
		}
		return count;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ErpStockAdjBean> getstockAdjbeanDetails(String year) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		List<ErpStockAdjBean> erpStockAdjBean=null;
		try {
		erpStockAdjBean=this.erpRepository.getStockAdj(year);
		map.put("stockAdj", erpStockAdjBean);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return erpStockAdjBean;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ErpGrnBean> getErpGrnDetails(String year) throws Exception {
		List<ErpGrnBean> erpGrnBean=null;
		try {
			erpGrnBean=this.erpRepository.getGrn(year);
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return erpGrnBean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ErpGrnConfirmationBean> getErpGrnconfirmationDetails(String year) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		List<ErpGrnConfirmationBean> erpGrnConfirmationBean=null;
		try {
			erpGrnConfirmationBean=this.erpRepository.getGrnConfirmation(year);
			map.put("cancelledGrn", erpGrnConfirmationBean);
			
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return erpGrnConfirmationBean;
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update_erpproduct(String divid,List<String> failedProducts) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			this.erpRepository.updateErpProduct(divid,failedProducts);
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update_erpbatch(String divid,List<String> failedBatch) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.erpRepository.updateErpBatch(failedBatch);
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update_erpgrn(List<String> failed,String year) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.erpRepository.updateErpGrn(failed,year);
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update_erpgrnconfirmation(List<String> list,String year) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.erpRepository.updateErpGrnComfirm(list,year);
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update_erpstockadj(String year) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.erpRepository.updateErpStockAdj(year);
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	
	}
	
	
	
	
	//from here
	@Override
	public Map<String, Object> getErpstockwithdrawaldata(String year) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		List<ErpStockwithdrawalBean> erpstkwthbean=null;
		try {
			erpstkwthbean = this.erpRepository.getErpstockwithdrawaldata(year);
			

		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return map;
		
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveIaaEntry(ErpIaaBean erpIaa) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Stock_Adj_Header res_obj = null;
		String appr_req = "";
		List<Am_m_System_Parameter> sysParaList = this.systemParameterRepository.getSpValueBySpName("STKADJ_APPR_REQ");
		if (sysParaList != null) {
			appr_req = sysParaList.get(0).getSp_value();
			erpIaa.setAppr_req(appr_req);
		}
		//To get Stock Adjustment Number
		Trans_Key_Master Stk_trans_key_master_ = this.transKeyMasterRepository.getTransObjByPrefix(Long.valueOf(erpIaa.getLocation()),erpIaa.getFinYearId(),erpIaa.getCompany(),STKADJ);
		if (Stk_trans_key_master_ == null)
			throw new MedicoException("Trans Key Master not mapped");
		long currentNo = Long.parseLong(Stk_trans_key_master_.getLast_num());
		String  number= String.format("%015d", currentNo);
		Stk_trans_key_master_.setLast_num(String.format("%015d", currentNo + 1l));
		transactionalRepository.update(Stk_trans_key_master_);
		erpIaa.setStkAdjNo(number);
		//set stk header
		res_obj = this.saveStockAdjHeader(erpIaa);
		erpIaa.setHeaderId(res_obj.getStkadj_id());
		
		//To get Batch with hold number
		Trans_key_Master_batch_wthrel trans_key_master = this.transKeyMasterRepository.getTranLastNo(Long.valueOf(erpIaa.getLocation()),Long.valueOf(erpIaa.getLocation()), STK_TRF_NS,erpIaa.getFinYearId(), BWT);
		if (trans_key_master == null)
			throw new MedicoException("Trans Key Master not mapped");
		long currno = Long.parseLong(trans_key_master.getLast_num());
		String trans_no = trans_key_master.getPrefix().trim() + erpIaa.getFinYearId().substring(2)
				+ String.format("%06d", currno);
		erpIaa.setTrans_no(trans_no);
		trans_key_master.setLast_num(String.format("%015d", currno + 1l));
		transactionalRepository.update(trans_key_master);
		erpIaa.setWh_tran_id(this.saveBatchWithHoleHeader(erpIaa));
		erpIaa.setStock_cfa(true);
		erpIaa.setAction("");
		erpIaa.setBatchInd("N");
		erpIaa.setExpInd("N");
		for(int i=0;i<erpIaa.getDetails().size();i++) {
			//Stock_Adj_Details det = this.saveStockAdjDetails(erpIaa,i);
			//erpIaa.setAdjDtlId(det.getAdjdtl_id());
			Long stkDtlId=this.saveStockAdjDetailsProceure(erpIaa, i);
			erpIaa.setAdjDtlId(stkDtlId);
			this.saveBatchWithHodlDtl(erpIaa,i);
		}
		//send for approval
		IAABean bean=new IAABean();
		bean.setStkAdjHeaderId(res_obj.getStkadj_id());
		bean.setCompanyCode(erpIaa.getCompany());
		bean.setCurrFinYear(erpIaa.getFinYearId());
		bean.setEmpId(erpIaa.getUserName());
		bean.setStatus("F");
		this.iAAService.iaaSelfApproval(bean);
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Stock_Adj_Header saveStockAdjHeader(ErpIaaBean bean) throws Exception {
		 Stock_Adj_Header header=null;
		 header= new Stock_Adj_Header();
		 header.setStkadj_appr_status("E");
		 header.setStkadj_company(bean.getCompany());
		 header.setStkadj_date(new Date());
		 header.setStkadj_erp_code("");
		 header.setStkadj_fin_year(bean.getFinYearId());
		 header.setStkadj_ins_dt(new Date());
		 header.setStkadj_ins_ip_add("0");
		 header.setStkadj_ins_user_id(bean.getUserName());
		 header.setStkadj_loc_id(Long.valueOf(bean.getLocation()));
		 header.setStkadj_status("A");
		 header.setStkadj_remarks("");
		 header.setStkadj_no(bean.getStkAdjNo());
		 header.setStkadj_period_code(bean.getPeriodCode());
		 header.setStkadj_remarks("");
		 header.setErp_iaa_no(bean.getWms_iaa_number());
		 header.setErp_created("Y");
		 header.setStkadj_appr_req(1L);
		 header.setStkadj_appr_acq(0L);
		 this.transactionalRepository.persist(header);
		 return header;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Stock_Adj_Details saveStockAdjDetails(ErpIaaBean bean,int i) throws Exception {
		 Stock_Adj_Details details=null;
		 details= new Stock_Adj_Details();
		 details.setAdjdtl_company(bean.getCompany());
		 details.setAdjdtl_ins_dt(new Date());
		 details.setStkadj_appr_acq(0);
		 details.setStkadj_appr_req(1);
		 details.setStkadj_appr_status("E");
		 details.setAdjdtl_fin_year(bean.getFinYearId());
		 details.setAdjdtl_reason_id(Long.valueOf(bean.getDetails().get(i).getReasonCode()));
		 //in
		 SmpItem inItem=this.productMasterRepository.getProductMasterObjByCode(bean.getDetails().get(i).getIn_prod_id());
		 details.setAdjdtl_in_item_id(inItem.getSmp_prod_id());
		 SmpBatch inbatch=this.batchMasterRepository.batchByBatchNoProdId(bean.getDetails().get(i).getIn_batch_id(),inItem.getSmp_prod_id());
		 details.setAdjdtl_in_batch_id(inbatch.getBatch_id());
		 details.setAdjdtl_in_qty(Long.valueOf(bean.getDetails().get(i).getIn_prod_qty()));
		 details.setAdjdtl_in_stk_type(bean.getDetails().get(i).getIn_stock_type());
		 details.setAdjdtl_in_rate(inbatch.getBatch_rate());
		 //out
		 SmpItem outItem=this.productMasterRepository.getProductMasterObjByCode(bean.getDetails().get(i).getOut_prod_id());
		 details.setAdjdtl_out_item_id(outItem.getSmp_prod_id());
		 SmpBatch outbatch=this.batchMasterRepository.batchByBatchNoProdId(bean.getDetails().get(i).getOut_batch_id(),outItem.getSmp_prod_id());
		 details.setAdjdtl_out_batch_id(outbatch.getBatch_id());
		 details.setAdjdtl_out_qty(Long.valueOf(bean.getDetails().get(i).getOut_prod_qty()));
		 details.setAdjdtl_out_stk_type(bean.getDetails().get(i).getOut_stock_type());
		 details.setAdjdtl_out_rate(outbatch.getBatch_rate());
			 
		 details.setAdjdtl_loc_id(Long.valueOf(bean.getLocation()));
		 details.setAdjdtl_period_code(bean.getPeriodCode());
		 details.setAdjdtl_stkadj_id(bean.getHeaderId());
		 details.setAdjdtl_status("A");
		 details.setAdjdtl_ins_ip_add("0");
		 details.setAdjdtl_ins_usr_id(bean.getUserName());
		 details.setAdjdtl_mod_ip_add("0");
		 details.setAdjdtl_mod_usr_id(bean.getUserName());
		 this.transactionalRepository.persist(details);
		 
		 return details;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long saveBatchWithHoleHeader(ErpIaaBean bean) throws Exception {
		BatchWithholdReleaseHDR withholdHdr = new BatchWithholdReleaseHDR();
		withholdHdr.setLoc_id(Long.valueOf(bean.getLocation()));
		withholdHdr.setFin_year_id(bean.getFinYearId());
		withholdHdr.setCompany_cd(bean.getCompany());
		withholdHdr.setTran_no(bean.getTrans_no());
		withholdHdr.setTran_date(new Date());
		withholdHdr.setTran_type(STK_TRF_NS);
		withholdHdr.setStock_point_id(Long.valueOf(bean.getLocation()));
		withholdHdr.setGrn_quarantine("N");
		withholdHdr.setRef_tran_id(bean.getHeaderId());
		withholdHdr.setRef_tran_no(bean.getStkAdjNo());
		withholdHdr.setRef_tran_type(IAA);
		withholdHdr.setBwthrel_hdr_ins_usr_id(bean.getUserName());
		withholdHdr.setBwthrel_hdr_ins_dt(new Date());
		withholdHdr.setBwthrel_hdr_ins_ip_add("0");
		withholdHdr.setBwthrel_hdr_status("A");
		this.transactionalRepository.persist(withholdHdr);
		return withholdHdr.getTran_id();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveBatchWithHodlDtl(ErpIaaBean bean,int i) throws Exception {
		BatchWithholdReleaseDTL withholdDetail = new BatchWithholdReleaseDTL();
		withholdDetail.setTran_id(bean.getWh_tran_id());
		withholdDetail.setLoc_id(Long.valueOf(bean.getLocation()));
		withholdDetail.setFin_year_id(Long.valueOf(bean.getFinYearId()));
		withholdDetail.setCompany_cd(bean.getCompany());
		withholdDetail.setTran_date(new Date());
		SmpItem item=this.productMasterRepository.getProductMasterObjByCode(bean.getDetails().get(i).getOut_prod_id());
		withholdDetail.setProd_id(item.getSmp_prod_id());
		SmpBatch batch=this.batchMasterRepository.batchByBatchNoProdId(bean.getDetails().get(i).getOut_batch_id(),item.getSmp_prod_id());
		withholdDetail.setBatch_id(batch.getBatch_id());
		withholdDetail.setRate_nr(batch.getBatch_rate());
		withholdDetail.setQty(BigDecimal.valueOf(Long.valueOf(bean.getDetails().get(i).getOut_prod_qty())));
		withholdDetail.setReason_id(0L);
		withholdDetail.setTran_type(STK_TRF_NS);
		withholdDetail.setTran_no(bean.getTrans_no());
		withholdDetail.setStock_point_id(Long.valueOf(bean.getLocation()));
		withholdDetail.setRef_tran_dtl_id(bean.getAdjDtlId());
		withholdDetail.setBwthrel_dtl_ins_usr_id(bean.getUserName());
		withholdDetail.setBwthrel_dtl_ins_dt(new Date());
		withholdDetail.setBwthrel_dtl_status("A");
		withholdDetail.setBwthrel_dtl_ins_ip_add("0");
		this.transactionalRepository.persist(withholdDetail);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long saveStockAdjDetailsProceure(ErpIaaBean erp,int i) throws Exception {
		 IAABean bean=null;
		 bean= new IAABean();
		 bean.setAction("");
		 bean.setAdjDtlId(0L);
		 bean.setHeaderId(erp.getHeaderId());
		 bean.setCompanyCode(erp.getCompany());
		 bean.setCurrFinYear(erp.getFinYearId());
		 bean.setCurrPeriodCode(erp.getPeriodCode());
		 bean.setSendLocId(Long.valueOf(erp.getLocation()));
		 bean.setReasonId(Long.valueOf(erp.getDetails().get(i).getReasonCode()));
		 //out
		 SmpItem outItem=this.productMasterRepository.getProductMasterObjByCode(erp.getDetails().get(i).getOut_prod_id());
		 SmpBatch outbatch=this.batchMasterRepository.batchByBatchNoProdId(erp.getDetails().get(i).getOut_batch_id(),outItem.getSmp_prod_id());
		 bean.setSetRemoveProdId(outItem.getSmp_prod_id());
		 bean.setSetRemoveBatchId(outbatch.getBatch_id());
		 bean.setRemoveQty(BigDecimal.valueOf(Long.valueOf(erp.getDetails().get(i).getOut_prod_qty())));
		 bean.setOut_stock_type(erp.getDetails().get(i).getOut_stock_type());
		 bean.setRemoveRate(outbatch.getBatch_rate());
		 //in
		 SmpItem inItem=this.productMasterRepository.getProductMasterObjByCode(erp.getDetails().get(i).getIn_prod_id());
		 SmpBatch inbatch=this.batchMasterRepository.batchByBatchNoProdId(erp.getDetails().get(i).getIn_batch_id(),inItem.getSmp_prod_id());
		 bean.setSetAddProdId(inItem.getSmp_prod_id());
		 bean.setSetAddBatchId(inbatch.getBatch_id());
		 bean.setAddQty(BigDecimal.valueOf(Long.valueOf(erp.getDetails().get(i).getIn_prod_qty())));
		 bean.setIn_stock_type(erp.getDetails().get(i).getIn_stock_type());
		 bean.setAddRate(inbatch.getBatch_rate());
		 
		 bean.setAddBatchId(erp.getDetails().get(i).getIn_batch_id());
		 bean.setOut_tran_type("");
		 bean.setIn_tran_type("");
		 bean.setTrnDate(erp.getTranDate());
		 bean.setBatchInd("N");
		 bean.setExpInd("N");
		 bean.setAddRemark("");
		 bean.setEmpId(erp.getUserName());
		 bean.setIpAddress("0");
		 bean.setAppr_req(erp.getAppr_req());
		 bean.setStock_cfa(true);
		 Long detailId=this.iAARepository.saveStockAdjDetail(bean);
		 
		 return detailId;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getErp_NormalIaadata(String year) throws Exception {
		Map<String, Object> map=null;
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
			List<ErpNormal_Iaabean> bean = this.erpRepository.getErp_NormalIaadata(year);
			count=bean.size();
			JSONArray data = new JSONArray();
			JSONObject object=null;
			JSONObject lines=null;
			JSONArray linesArray = null;
			String refNo="new";
			String oldRefNo="old";
			boolean setObject=false;
			if(bean!=null && bean.size()>0) {
				for(ErpNormal_Iaabean iaa:bean) {
					refNo=iaa.getAdjustment_No().toString();

					if(!oldRefNo.equals(refNo) && setObject==true) {
						object.put("lines", linesArray);
						data.put(object);
					}
					
					if(!oldRefNo.equals(refNo)) {
						object=new JSONObject();
						linesArray=new JSONArray();
						object.put("Organization",iaa.getOrganization());
						object.put("storageCondition",iaa.getStorage_condition());
						object.put("adjustmentNo","0");//this is 0 for normal IAA
						object.put("medicoIAANo",iaa.getDocument_no());
						object.put("confirmationFlag", iaa.getIaa_Confirmation_flag());
						object.put("location",iaa.getLocation());
						object.put("description", "Normal Iaa");
						object.put("IAAID",iaa.getAdjustment_No().toString());
					}
					lines=new JSONObject();
					lines.put("outProduct",iaa.getOut_Product().trim());
					lines.put("outBatch",iaa.getOut_Batch().trim());
					lines.put("outQty",String.valueOf(iaa.getOut_Qty().setScale(0)));
					lines.put("outStockType",iaa.getOut_Stock_type().trim());
					lines.put("inStockType",iaa.getIn_Stock_type().trim());
					lines.put("reasonCode", iaa.getReasonCode());
					linesArray.put(lines);
					oldRefNo=iaa.getAdjustment_No().toString();
					setObject=true;
					
				}
				object.put("lines", linesArray);
				data.put(object);
				
				JSONObject obj=new JSONObject();
				obj.put("grn", data);
				JSONArray array=new JSONArray();
				array.put(obj);
				finalObject.put("grns", array);
				HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
				System.out.println("Final Object "+finalObject);
				System.out.println("Grn count "+bean.size());
				ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
				interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
				interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
				restTemplate.setInterceptors(interceptors);
				String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.WMS_IAAConfirmation",request,String.class);
				System.out.println("result" +result);
				JSONObject resultJson = new JSONObject(result);
				//Insert into ERP NOrmal
				if(!resultJson.get("Status").toString().equals("Error")) {
					String failedGrn=resultJson.get("FailedGRNs").toString();
					System.out.println("failedGrn" +failedGrn);
					String[] failed=failedGrn.split(",");
					List<String> failedList=new ArrayList<>();
					failedList=Arrays.asList(failed);
					this.erpRepository.update_erpNormalIaadata(failedList,year);
				}
			}
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}

	

	@Override
	public List<ErpIaaGrn_bean> getErp_IaaGrndata(String year) throws Exception {
		// TODO Auto-generated method stub
		return this.erpRepository.getErp_IaaGrndata(year);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getErp_Wms_Iaa_Confirmationdata(String year) throws Exception {
		Map<String, Object> map=null;
		JSONObject finalObject=new JSONObject();
		int count=0;
		try {
			List<Erp_Wms_Iaa_confirmationbean> bean = this.erpRepository.getErp_Wms_Iaa_Confirmationdata(year);
			count=bean.size();
			JSONArray data = new JSONArray();
			JSONObject object=null;
			JSONObject lines=null;
			JSONArray linesArray = null;
			String refNo="new";
			String oldRefNo="old";
			boolean setObject=false;
			if(bean!=null && bean.size()>0) {
				for(Erp_Wms_Iaa_confirmationbean iaa:bean) {
					refNo=iaa.getAdjustment_No().toString();

					if(!oldRefNo.equals(refNo) && setObject==true) {
						object.put("lines", linesArray);
						data.put(object);
					}
					
					if(!oldRefNo.equals(refNo)) {
						object=new JSONObject();
						linesArray=new JSONArray();
						object.put("Organization",iaa.getOrganization());
						object.put("storageCondition",iaa.getOut_storage_condition());
						object.put("adjustmentNo",iaa.getWms_Reference_No().toString());
						object.put("medicoIAANo",iaa.getMedico_iaa_no());
						object.put("confirmationFlag", iaa.getIaa_Confirmation_flag());//vendor name
						if(iaa.getOrganization().trim().equalsIgnoreCase("PIL"))
							object.put("location", "1");
						else
							object.put("location","9");
						object.put("reasonCode", iaa.getReasonCode());
						object.put("description", "");
						object.put("IAAID",iaa.getAdjustment_No().toString());
					}
					lines=new JSONObject();
					lines.put("outProduct",iaa.getOut_Product().trim());
					lines.put("outBatch",iaa.getOut_Batch().trim());
					lines.put("outQty",String.valueOf(iaa.getOut_Qty().setScale(0)));
					lines.put("outStockType",iaa.getOut_Stock_type().trim());
					lines.put("inStockType",iaa.getIn_Stock_type().trim());
					linesArray.put(lines);
					oldRefNo=iaa.getAdjustment_No().toString();
					setObject=true;
				}
				object.put("lines", linesArray);
				data.put(object);
				
				JSONObject obj=new JSONObject();
				obj.put("grn", data);
				JSONArray array=new JSONArray();
				array.put(obj);
				finalObject.put("grns", array);
				HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
				System.out.println("Final Object "+finalObject);
				System.out.println("Grn count "+bean.size());
				ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
				interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
				interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
				restTemplate.setInterceptors(interceptors);
				String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.WMS_IAAConfirmation",request,String.class);
				System.out.println("result" +result);
				JSONObject resultJson = new JSONObject(result);
				//Insert into ERP NOrmal
				if(!resultJson.get("Status").toString().equals("Error")) {
					String failedGrn=resultJson.get("FailedGRNs").toString();
					System.out.println("failedGrn" +failedGrn);
					String[] failed=failedGrn.split(",");
					List<String> failedList=new ArrayList<>();
					failedList=Arrays.asList(failed);
					this.erpRepository.update_erpWms_Iaa_Confirmationdata(failedList,year);
				}
			}
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}

	@Override
	public void update_erpWms_Iaa_Confirmationdata(String year) throws Exception {
		// TODO Auto-generated method stub
		
	}


		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public Map<String, Object> getErp_Batch_stk_recodata(List<ErpBatch_stk_recobean> stk_reco_list) throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<>();
			Erp_Batch_stk_reco batchReco=null;
			try {
				SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat sdf2  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String reco_dt = null;
				this.erpRepository.Push_Batch_stk_reco_data_to_arc();///select all data from batch_stk_reco Save data to batch_stk_reco_arc
				this.erpRepository.Truncate_BatchstkReco();///Truncate Batch_stk_reco
				if(stk_reco_list!=null && stk_reco_list.size()>0) {
					for(ErpBatch_stk_recobean list:stk_reco_list) {
						reco_dt = sdf2.format(sdf.parse(list.getReco_date()));
						
						//reco_dt=list.getReco_date();
						batchReco=new Erp_Batch_stk_reco();
						batchReco.setStock_type(list.getStock_type());
						batchReco.setOrganization(list.getOrganization());
						batchReco.setProduct_code(list.getProduct_code());
						batchReco.setProduct_name(list.getProduct_name());
						batchReco.setQty(list.getQty());
						batchReco.setReco_date(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_dt));
						batchReco.setBatch_no(list.getBatch_no());
						//from Medico
						batchReco.setFin_year(stk_reco_list.get(0).getFin_year());
						this.transactionalRepository.persist(batchReco);
					}
				}
				System.out.println("recdt : "+sdf3.format(batchReco.getReco_date()));
				this.erpRepository.call_batch_stk_reco_pfz_proc(sdf3.format(batchReco.getReco_date()));						///Procedure
			//	this.erpRepository.call_batch_stk_reco_pfz_proc(MedicoResources.convertUtilDateToString(new Date()));						///Procedure
				map.put("status","success");
			}catch (Exception e) {
				map.put("status","error");
				throw e;
			}
			return  map;
		}
	

		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public Map<String, Object> getErp_Batch_stk_reco_quarantine_data(List<Erp_Batch_stk_reco_quarantinebean> stk_reco_list) throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<>();
			Erp_Batch_stk_reco_quarantine batchquar = null;
			try {
				SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat sdf2  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String reco_dt;
				this.erpRepository.push_Batch_stk_reco_quarantine_data_to_arc();	///Data inserted to Arc Table
				this.erpRepository.Truncate_Batch_stk_reco_Quarantine();			///New Inserted In Batch_stk_reco_Quarantine
				if(stk_reco_list!=null && stk_reco_list.size()>0) {
					for(Erp_Batch_stk_reco_quarantinebean list:stk_reco_list) {
						reco_dt = sdf2.format(sdf.parse(list.getReco_date()));
						
						batchquar = new Erp_Batch_stk_reco_quarantine();
						batchquar.setReco_date(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_dt));
						batchquar.setFin_year(list.getFin_year());
						batchquar.setOrganization(list.getOrganization());
						batchquar.setProduct_code(list.getProduct_code());
						batchquar.setProduct_name(list.getProduct_name());
						batchquar.setBatch_no(list.getBatch_no());
						batchquar.setStock_type(list.getStock_type());
						batchquar.setQty(list.getQty());
						
						this.transactionalRepository.persist(batchquar);
					}
				}
			//	this.erpRepository.call_batch_stk_reco_quarantine_proc(MedicoResources.convertUtilDateToString(new Date()));		///Procedure Called
				this.erpRepository.call_batch_stk_reco_quarantine_proc(sdf3.format(sdf.parse(stk_reco_list.get(0).getReco_date())));		///Procedure Called
				map.put("status","success");
			}
			catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			
			return map;
		}

		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public Map<String, Object> getErp_Tran_wise_data( List<Erp_tran_stock_recobean> tran_reco_list,String curr_year) {
			// TODO Auto-generated method stub
			Map <String,Object> map=new HashMap<>();
			Erp_tran_wise_reco tran=null;
			try
			{
				SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat sdf2  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String reco_from,reco_to,tran_date;
				
				this.erpRepository.push_Tran_wise_data_to_arc();	///Data inserted to Arc Table
				this.erpRepository.Truncate_Tran_wise_reco();			/// Tran_wise_reco Truncated
				if(tran_reco_list!=null && tran_reco_list.size()>0) {
						for(Erp_tran_stock_recobean list:tran_reco_list) {
							reco_from = sdf2.format(sdf.parse(list.getReco_from()));
							reco_to= sdf2.format(sdf.parse(list.getReco_to()));
							tran_date=sdf2.format(sdf.parse(list.getTran_date()));
						System.out.println("reco from " +MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_from));
						System.out.println("reco to " +MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_to));
						System.out.println("Organization " +list.getOrganization());
						System.out.println("Tran id " +list.getTran_id());
						System.out.println("Tran Type " +list.getTran_type());
						System.out.println("TRan No " +list.getTransaction_no());
						System.out.println("Tran Date " +MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(tran_date));
						System.out.println("In pro " +list.getIn_prod_code());
						System.out.println("In prod Name " +list.getIn_prod_name());
						System.out.println("In Batch No " +list.getIn_batch_no());
						System.out.println("in stock Type " +list.getIn_stock_type());
						System.out.println("In qty " +list.getIn_qty());
						System.out.println("Out pro " +list.getOut_prod_code());
						System.out.println("Out prod Name " +list.getOut_prod_name());
						System.out.println("Out Batch No " +list.getOut_batch_no());
						System.out.println("Out stock Type " +list.getOut_stock_type());
						System.out.println("Out qty" +list.getOut_qty());
							tran=new Erp_tran_wise_reco();
							tran.setReco_from(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_from));
							tran.setReco_to(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_to));
							tran.setOrganization(list.getOrganization());
							tran.setTran_id(list.getTran_id());
							tran.setTran_type(list.getTran_type());
							tran.setTransaction_no(list.getTransaction_no());
							tran.setTran_date(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(tran_date));
							tran.setIn_prod_code(list.getIn_prod_code());
							tran.setIn_prod_name(list.getIn_prod_name());
							tran.setIn_batch_no(list.getIn_batch_no());
							tran.setIn_stock_type(list.getIn_stock_type());
							tran.setIn_qty(new BigDecimal(list.getIn_qty()).longValue());
							tran.setOut_prod_code(list.getOut_prod_code());
							tran.setOut_product_name(list.getOut_prod_name());
							tran.setOut_batch_no(list.getOut_batch_no());
							tran.setOut_stk_type(list.getOut_stock_type());
							tran.setOut_qty(new BigDecimal(list.getOut_qty()).longValue());
							this.transactionalRepository.persist(tran);
						}
					}
			//	this.erpRepository.call_Erp_Tran_wise_data(MedicoResources.convertUtilDateToString(new Date()),MedicoResources.convertUtilDateToString(new Date()),curr_year);		///Procedure Called
				this.erpRepository.call_Erp_Tran_wise_data(sdf3.format(sdf.parse(tran_reco_list.get(0).getReco_from())),sdf3.format(sdf.parse(tran_reco_list.get(0).getReco_to())),curr_year);		///Procedure Called	
				map.put("status","success");
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public Map<String, Object> getErp_Tran_wise_Quarantine_data(List<Erp_tran_stock_Quarantine_recobean> tran_reco_list, String curr_year)
				throws Exception {
			// TODO Auto-generated method stub
			Map <String,Object> map=new HashMap<>();
			Erp_tran_wise_Quarantine_reco tran=null;
			try
			{
				SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat sdf2  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String reco_from,reco_to,tran_date;
				
				this.erpRepository.push_Tran_wise_Quarantine_data_to_arc();	///Data inserted to Arc Table
				this.erpRepository.Truncate_Tran_wise_Quarantine_reco();			/// Tran_wise_reco Truncated
				if(tran_reco_list!=null && tran_reco_list.size()>0) {
					for(Erp_tran_stock_Quarantine_recobean list:tran_reco_list) {
						reco_from = sdf2.format(sdf.parse(list.getReco_from()));
						reco_to= sdf2.format(sdf.parse(list.getReco_to()));
					    tran_date=sdf2.format(sdf.parse(list.getTran_date()));
					    

						System.out.println("reco from " +MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_from));
						System.out.println("reco to " +MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_to));
						System.out.println("Organization " +list.getOrganization());
						System.out.println("Tran id " +list.getTran_id());
						System.out.println("Tran Type " +list.getTran_type());
						System.out.println("TRan No " +list.getTransaction_no());
						System.out.println("Tran Date " +MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(tran_date));
						System.out.println("In pro " +list.getIn_prod_code());
						System.out.println("In prod Name " +list.getIn_prod_name());
						System.out.println("In Batch No " +list.getIn_batch_no());
						System.out.println("in stock Type " +list.getIn_stock_type());
						System.out.println("In qty " +list.getIn_qty());
						System.out.println("Out pro " +list.getOut_prod_code());
						System.out.println("Out prod Name " +list.getOut_prod_name());
						System.out.println("Out Batch No " +list.getOut_batch_no());
						System.out.println("Out stock Type " +list.getOut_stock_type());
						System.out.println("Out qty" +list.getOut_qty());
						tran=new Erp_tran_wise_Quarantine_reco();
						tran.setReco_from(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_from));
						tran.setReco_to(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(reco_to));
						tran.setOrganization(list.getOrganization());
						tran.setTran_id(list.getTran_id());
						tran.setTran_type(list.getTran_type());
						tran.setTransaction_no(list.getTransaction_no());
						tran.setTran_date(MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(tran_date));
						tran.setIn_prod_code(list.getIn_prod_code());
						tran.setIn_prod_name(list.getIn_prod_name());
						tran.setIn_batch_no(list.getIn_batch_no());
						tran.setIn_stock_type(list.getIn_stock_type());
						tran.setQty(Long.valueOf(list.getIn_qty()));
						tran.setOut_prod_code(list.getOut_prod_code());
						tran.setOut_product_name(list.getOut_prod_name());
						tran.setOut_batch_no(list.getOut_batch_no());
						tran.setOut_stk_type(list.getOut_stock_type());
						tran.setOut_qty(Long.valueOf(list.getOut_qty()));
						this.transactionalRepository.persist(tran);
					}
					}
				System.out.println("list:::"+tran_reco_list.size());
				System.out.println("from ::::::"+tran_reco_list.get(0).getReco_from()+":::::"+tran_reco_list.get(0).getReco_to());
			//	this.erpRepository.call_Erp_Tran_wise_Quarantine_data(MedicoResources.convertUtilDateToString(new Date()),MedicoResources.convertUtilDateToString(new Date()),curr_year);		///Procedure Called
				this.erpRepository.call_Erp_Tran_wise_Quarantine_data(sdf3.format(sdf.parse(tran_reco_list.get(0).getReco_from())),sdf3.format(sdf.parse(tran_reco_list.get(0).getReco_to())),curr_year);		///Procedure Called
				
				map.put("status","success");
			}
			catch(Exception e){
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}

		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public int getStockWithrawal(String finYear) {
			Map<String, Object> map=null;
			JSONObject finalObject=new JSONObject();
			List<String> selectedDspIds=new ArrayList<>();
			int count=0;
			try {
				List<ErpStockwithdrawalBean> bean = this.erpRepository.getErpstockwithdrawaldata(finYear);
				count=bean.size();
				JSONArray data = new JSONArray();
				JSONObject object=null;
				JSONObject lines=null;
				JSONArray linesArray = null;
				String refNo="new";
				String oldRefNo="old";
				boolean setObject=false;
				if(bean!=null && bean.size()>0) {
					for(ErpStockwithdrawalBean dispatch:bean) {
						refNo=dispatch.getHeader_id();

						if(!oldRefNo.equals(refNo) && setObject==true) {
							object.put("lines", linesArray);
							data.put(object);
						}
						
						if(!oldRefNo.equals(refNo)) {
							object=new JSONObject();
							linesArray=new JSONArray();
							object.put("Organization",dispatch.getOrganization());
							object.put("prodtype", dispatch.getProduct_type());
							object.put("storageCondition",dispatch.getStorage_Condition());
							object.put("documentType",dispatch.getDocument_Type().trim());
							object.put("bpartner",dispatch.getBuisness_Partner().trim());
							object.put("warehouse",dispatch.getWarehouse().trim());//stock type id
							object.put("orderRefNo",dispatch.getRef_no_po());
							object.put("scheduleDelDate",dispatch.getExpected_Delivery_dateTime());
							object.put("description",dispatch.getDescription().trim());
							object.put("billTo",dispatch.getBill_To());
							object.put("shipTo",dispatch.getShip_To());
							object.put("complete", "FALSE");
							object.put("dispatchID",dispatch.getHeader_id());
							selectedDspIds.add(dispatch.getHeader_id());
						}
						lines=new JSONObject();
						lines.put("sku",dispatch.getProduct().trim());
						lines.put("qty",dispatch.getSales_Order_Quantity().setScale(0).toString());
						lines.put("lotNo",dispatch.getLot_no());
						lines.put("expiryDate",dispatch.getExp_Date());
						linesArray.put(lines);
			
						oldRefNo=dispatch.getHeader_id();
						setObject=true;
					}
					object.put("lines", linesArray);
					data.put(object);
					JSONObject obj=new JSONObject();
					obj.put("obd", data);
					JSONArray array=new JSONArray();
					array.put(obj);
					finalObject.put("obds", array);
					HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
					System.out.println("Final Object "+finalObject);
					System.out.println("Dispatch count "+bean.size());
					ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
					interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
					interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
					restTemplate.setInterceptors(interceptors);
					String result = restTemplate.postForObject(this.url+"/openbravo/ws/in.mbs.integrationwebservice.OBD",request,String.class);
					System.out.println("result" +result);
					JSONObject resultJson = new JSONObject(result);
					if(!resultJson.get("Status").toString().equals("Error")) {
						//insert into Erp_batch
						System.out.println("Failed "+resultJson.get("FailedOBD").toString());
						String failedDispatch=resultJson.get("FailedOBD").toString();
						System.out.println("failedDispatch "+failedDispatch);
						String[] failed=failedDispatch.split(",");
						List<String> failedList=new ArrayList<>();
						failedList=Arrays.asList(failed);
						selectedDspIds.removeAll(failedList);
						if(selectedDspIds.size()>0) {
							this.erpRepository.update_erpstockwithdrawal(selectedDspIds,finYear);
						}
					}
					
				}
				
			}
			catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return count;
		}
		
		@Override
		public List<Erp_Batch_stk_reco> getdataforstockreco() throws Exception {
			// TODO Auto-generated method stub
			return erpRepository.getdataforstockreco();
		}
		
		@Override
		public List<Erp_Batch_stk_reco_quarantine> getstockRecoQuarantinedata() {
			
			return erpRepository.getstockRecoQuarantinereportdata();
		}
	
//	
}
