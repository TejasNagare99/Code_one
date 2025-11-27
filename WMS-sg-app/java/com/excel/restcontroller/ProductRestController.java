package com.excel.restcontroller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.Hsn_and_Tax_Bean;
import com.excel.bean.ProductBean;
import com.excel.bean.ProductMasterBean;
import com.excel.bean.ProductwiseTaxMasterBean;
import com.excel.model.Company;
import com.excel.model.DivMaster;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SamplProdGroup;
import com.excel.model.SmpItem;
import com.excel.model.Sub_Company;
import com.excel.service.CompanyMasterService;
import com.excel.service.DivisionMasterService;
import com.excel.service.ParameterService;
import com.excel.service.ProductMasterService;
import com.excel.service.ProductwiseTaxMasterService;
import com.excel.service.SamplProdGroupService;
import com.excel.service.TaxService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class ProductRestController implements MedicoConstants{
	@Autowired ParameterService parameterService;
	@Autowired CompanyMasterService companyMasterService;
	@Autowired ProductMasterService productMasterService;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired SamplProdGroupService samplProdGroupService;
	@Autowired TaxService taxService ;
	@Autowired ProductwiseTaxMasterService productwiseTaxMasterService ;

	@Autowired private UserMasterService userMasterService;
	
	
	@GetMapping("/checkMargin")
	public Object checkMargin(HttpSession session) {
		Company company= (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		String ind=company.getMargin_reqd();
		System.out.println("company name is:"+company.company_name);
		String companyName=company.company_name;
		
		Map<String, Object> map=new HashMap<>();
		Boolean margin = null;
		System.out.println(ind);
		if(ind.equals("Y")) {
			margin=true;
		}
		if(ind.equals("N")) {
			margin=false;
		}
		map.put("companyName", companyName);
		map.put("margin", margin);
		return map;
	}
	
//	@GetMapping("/getDataForProductEntry")
//	public Object getDataForProductEntry(HttpSession session,@RequestParam String empId) {
//		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
//		Map<String, Object> map=new HashMap<>();
//		List<SG_d_parameters_details> ProductTypeList = null;
//		List<SG_d_parameters_details> ManufacturingLocationList = null;
//		List<SG_d_parameters_details> theropathyGroupList = null;
//		List<SG_d_parameters_details> theropathySubGroupList = null;
//		List<SG_d_parameters_details> moleGroupList = null;
//		List<SG_d_parameters_details> moleSubGroupList = null;
//		List<SG_d_parameters_details> pmtGroupList = null;
//		List<SG_d_parameters_details> pmtSubGroupList = null;
//		List<SG_d_parameters_details> storageTypeList = null;
//		List<SG_d_parameters_details> formList = null;
//		//List<SG_d_parameters_details> productSubTypeList = null;
//		List<SG_d_parameters_details> sampleSupplierTypeList = null;
//		
//		List<Sub_Company> subCompList = null;
//		List<SamplProdGroup> brandList = null;
//		List<ProductBean> umoList = null;
//		List<ProductMasterBean> packList = null;
//		List<DivMaster> divList = null;
//		List<SG_d_parameters_details> reasonList = null;
//		try {
//			ProductTypeList = parameterService.getProductTypes();
//			ManufacturingLocationList = parameterService.getManufacturingLocation();
//			formList=this.parameterService.getForm();
//			umoList=this.productMasterService.getUmo();
//			packList=this.productMasterService.getPack();
//			subCompList=this.companyMasterService.getSubCompanyList(empId);
//			brandList=this.samplProdGroupService.getAllSamplProducts();
//			
//			theropathyGroupList=this.parameterService.getTheropathyGroup();
//			theropathySubGroupList=this.parameterService.getTheropathySubGroup();
//			moleGroupList=this.parameterService.getMoleGroup();
//			moleSubGroupList=this.parameterService.getMoleSubGroup();
//			pmtGroupList=this.parameterService.getPMTGroup();
//			pmtSubGroupList=this.parameterService.getPMTSubGroup();
//			storageTypeList=this.parameterService.getStorageType();
//			//productSubTypeList=this.parameterService.getProductSubType();
//			sampleSupplierTypeList=this.parameterService.getSampleSupplierType();
//			divList=this.divisionMasterService.getAllStandardDivisionList();
//			reasonList=this.parameterService.getParameterDetailByDispNm("GCMA_NOT_REQD_REASONS");
//		} catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		map.put("compCode", compCode);
//		map.put("subCompList", subCompList);
//		map.put("ProductTypeList", ProductTypeList);
//		map.put("ManufacturingLocationList", ManufacturingLocationList);
//		map.put("packList", packList);
//		map.put("formList", formList);
//		map.put("umoList", umoList);
//		map.put("brandList", brandList);
//		
//		map.put("theropathyGroupList", theropathyGroupList);
//		map.put("theropathySubGroupList", theropathySubGroupList);
//		map.put("moleGroupList", moleGroupList);
//		map.put("moleSubGroupList", moleSubGroupList);
//		map.put("pmtGroupList", pmtGroupList);
//		map.put("pmtSubGroupList", pmtSubGroupList);
//		map.put("storageTypeList", storageTypeList);
//		//map.put("productSubTypeList", productSubTypeList);
//		map.put("sampleSupplierTypeList", sampleSupplierTypeList);
//		map.put("divList", divList);
//		map.put("reasonList", reasonList);
//		return map;
//	}
	
	
	
	@GetMapping("/getDataForProductEntry")
	public Object getDataForProductEntry(HttpSession session,	
			HttpServletRequest request) {
		
		
		String empId = "";
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		Map<String, Object> map=new HashMap<>();
		List<SG_d_parameters_details> ProductTypeList = null;
		List<SG_d_parameters_details> ManufacturingLocationList = null;
		List<SG_d_parameters_details> theropathyGroupList = null;
		List<SG_d_parameters_details> theropathySubGroupList = null;
		List<SG_d_parameters_details> moleGroupList = null;
		List<SG_d_parameters_details> moleSubGroupList = null;
		List<SG_d_parameters_details> pmtGroupList = null;
		List<SG_d_parameters_details> pmtSubGroupList = null;
		List<SG_d_parameters_details> storageTypeList = null;
		List<SG_d_parameters_details> formList = null;
		//List<SG_d_parameters_details> productSubTypeList = null;
		List<SG_d_parameters_details> sampleSupplierTypeList = null;
		
		List<Sub_Company> subCompList = null;
		List<SamplProdGroup> brandList = null;
		List<ProductBean> umoList = null;
		List<ProductMasterBean> packList = null;
		List<DivMaster> divList = null;
		List<SG_d_parameters_details> reasonList = null;
		List<Hsn_and_Tax_Bean> hsn_beans=null;
		try {
			ProductTypeList = parameterService.getProductTypes();
			ManufacturingLocationList = parameterService.getManufacturingLocation();
			formList=this.parameterService.getForm();
			umoList=this.productMasterService.getUmo();
			packList=this.productMasterService.getPack();
			subCompList=this.companyMasterService.getSubCompanyList(empId);
			brandList=this.samplProdGroupService.getAllSamplProducts();
			
			theropathyGroupList=this.parameterService.getTheropathyGroup();
			theropathySubGroupList=this.parameterService.getTheropathySubGroup();
			moleGroupList=this.parameterService.getMoleGroup();
			moleSubGroupList=this.parameterService.getMoleSubGroup();
			pmtGroupList=this.parameterService.getPMTGroup();
			pmtSubGroupList=this.parameterService.getPMTSubGroup();
			storageTypeList=this.parameterService.getStorageType();
			//productSubTypeList=this.parameterService.getProductSubType();
			sampleSupplierTypeList=this.parameterService.getSampleSupplierType();
			divList=this.divisionMasterService.getAllStandardDivisionList();
			reasonList=this.parameterService.getParameterDetailByDispNm("GCMA_NOT_REQD_REASONS");
			 hsn_beans=this.productMasterService.getAllHsnCode();
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("compCode", compCode);
		map.put("subCompList", subCompList);
		map.put("ProductTypeList", ProductTypeList);
		map.put("ManufacturingLocationList", ManufacturingLocationList);
		map.put("packList", packList);
		map.put("formList", formList);
		map.put("umoList", umoList);
		map.put("brandList", brandList);
		
		map.put("theropathyGroupList", theropathyGroupList);
		map.put("theropathySubGroupList", theropathySubGroupList);
		map.put("moleGroupList", moleGroupList);
		map.put("moleSubGroupList", moleSubGroupList);
		map.put("pmtGroupList", pmtGroupList);
		map.put("pmtSubGroupList", pmtSubGroupList);
		map.put("storageTypeList", storageTypeList);
		//map.put("productSubTypeList", productSubTypeList);
		map.put("sampleSupplierTypeList", sampleSupplierTypeList);
		map.put("divList", divList);
		map.put("reasonList", reasonList);
		map.put("hsn_beans", hsn_beans);
		return map;
	}
	
	
	
	@GetMapping("/getProductSubTypes")
	public Map<String,Object> getProductSubTypes(@RequestParam String shortName){
		Map<String, Object> map=new HashMap<>();
		List<SG_d_parameters_details> productSubTypeList = null;
		try {
			productSubTypeList=this.parameterService.getProductSubType(shortName);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("productSubTypeList", productSubTypeList);
		return map;
	}
//	@PostMapping("/saveProduct")
//	public Map<String, Boolean> saveProduct(@RequestBody ProductBean pb,@RequestParam String empId,HttpSession session,HttpServletRequest request) {
//		Map<String,Boolean> map=new HashMap<String, Boolean>();
//		try {
//			productMasterService.saveProduct(pb, empId, session, request);
//			map.put("DataSaved", true);
//		} catch (Exception e) {
//			map.put("DataSaved", false);
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
	
	@PostMapping("/saveProduct")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Boolean> saveProduct(@RequestBody ProductBean pb,@RequestParam String  company ,HttpSession session,HttpServletRequest request) {
		Map<String,Boolean> map=new HashMap<String, Boolean>();
		List<Object> tax_code_list=new ArrayList<>();
		List<Object> state_id_list=new ArrayList<>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			productMasterService.saveProduct(pb, empId, session, request);
			System.err.println("HSN CODE :::::::"+pb.getHsnCode());
			Map<String, List<Object>> map_of_list =taxService.getAll_States_And_Tax_code(pb.getHsnCode());
//			System.err.println("state_id_list:::"+state_id_list);
//			System.err.println("tax_code_list:::"+tax_code_list);
			state_id_list= map_of_list.get("state_list");
			tax_code_list= map_of_list.get("tax_list");
			ProductwiseTaxMasterBean bean=new ProductwiseTaxMasterBean ();
			bean.setCompany(company);
			bean.setEmpId(empId);
			bean.setProdCode(pb.getProductCode());
			productwiseTaxMasterService.saveProduct(bean, session, state_id_list, tax_code_list);		
			map.put("DataSaved", true);
		} catch (Exception e) {
			map.put("DataSaved", false);
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/getActiveProductDetail")
	public Map<String,Object> getActiveProductDetail(){
		Map<String,Object> map=new HashMap<String, Object>();
		List<Object> ActiveProductDetailList=null;
		try {
			ActiveProductDetailList=productMasterService.getAllActiveProductDetailForProductModify();
			
		} catch (Exception e) {
			
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("ActiveProductDetailList", ActiveProductDetailList);
		return map;
	}
	
	@GetMapping("/getProductDetailByTextParameterForProductModify")
	public Map<String,Object> getProductDetailByTextParameterForProductModify(@RequestParam String name,@RequestParam String parameter,@RequestParam String text){
		Map<String,Object> map=new HashMap<String, Object>();
		List<Object> ActiveProductDetailList=null;
		try {
			ActiveProductDetailList=productMasterService.getProductDetailByTextParameterForProductModify(name,parameter,text);
			
		} catch (Exception e) {
			
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("ActiveProductDetailList", ActiveProductDetailList);
		return map;
	}
	@GetMapping("/getProductDetailById")
	public Map<String, Object> getProductDetailById(@RequestParam String id){
		Map<String,Object> map=new HashMap<String, Object>();
		List<SmpItem> selectedProductDetail=null;
		try {
			selectedProductDetail=productMasterService.getProductDetailById(id);
			
		} catch (Exception e) {
			
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("selectedProductDetail", selectedProductDetail);
		return map;
	}
//	@PostMapping("/updateProduct")
//	public Map<String, Boolean> updateProduct(@RequestParam String ProdId,@RequestBody ProductBean pb,@RequestParam String empId,HttpSession session,HttpServletRequest request) {
//		Map<String,Boolean> map=new HashMap<String, Boolean>();
//		try {
//			productMasterService.updateProduct(ProdId,pb, empId, session, request);
//			map.put("DataSaved", true);
//		} catch (Exception e) {
//			map.put("DataSaved", false);
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
	
	
	
	@PostMapping("/updateProduct")
	public Map<String, Boolean> updateProduct(@RequestParam String ProdId,@RequestBody ProductBean pb,HttpSession session,HttpServletRequest request) {
		Map<String,Boolean> map=new HashMap<String, Boolean>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			
			System.err.println(pb.getProductType());
			productMasterService.updateProduct(ProdId,pb, empId, session, request);
			map.put("DataSaved", true);
		} catch (Exception e) {
			map.put("DataSaved", false);
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/getProd_nameBy_div_prodtype")
	public Map<String, Object> getProd_name_ListByDivPrdType(@RequestParam String prod_type_id,@RequestParam String divId,HttpSession session){
		Map<String,Object> map=new HashMap<String, Object>();
		List<SmpItem> list= null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		
		try {
			if(!divId.equals("") && !prod_type_id.equals("")) {
				System.out.println("Controller prodtypeid ::"+prod_type_id + "  divid ::"+divId);
			list = productMasterService.getProdListByDivPrdType(companyCode,Long.parseLong(prod_type_id),Long.parseLong(divId));
			SmpItem spm=new SmpItem();
			spm.setSmp_prod_id(0l);
			spm.setSmp_prod_name("All");
			list.add(0, spm);
			
			map.put("prod_list", list);
			System.out.println("success");
			}
		}catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
		
	}
	
	@GetMapping("/generateProductCode")
	public Map<String, Object> generateProductCode(@RequestParam("subCompany") String subcompany,@RequestParam("productId")String productId){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			String productCode=this.productMasterService.genereateProductCode(subcompany,productId);
			map.put("productCode",productCode);
		} catch (Exception e) {
			
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/unlockProductByEmpId")
	public Map<String, Object> generateProductCode(HttpServletRequest request){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			this.productMasterService.deleteLockProduct(empId);		
			} catch (Exception e) {
			
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/checkUniqueErpProductCode")
	public Object checkUniqueErpProductCode(@RequestParam String erpprodCode,@RequestParam String subCompany) {
		Map<String, Object> map=new HashMap<>();
		List<SmpItem> list = null;
		try {
			list=productMasterService.checkUniqueErpProductCode(erpprodCode,subCompany);
			
			if(list!=null && list.size()>0) {
				map.put("erpprodAvailable", true);
				map.put("erpprodlist", list);
			}
			else {
				map.put("erpprodAvailable", false);
			}
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		
		return map;
		
	}
	
	@GetMapping("/checkUniqueProductCode")
	public Object checkUniqueProductCode(@RequestParam String prodCode,@RequestParam String subCompany) {
		Map<String, Object> map=new HashMap<>();
		//Boolean prodAvailable = null;
		List<SmpItem> list = null;
		try {
			list=productMasterService.checkUniqueProductCode(prodCode,subCompany);
			if(list!=null && list.size()>0) {
				map.put("prodAvailable", true);
				map.put("prodlist", list);
			}else {
				map.put("prodAvailable", false);
			}
		}
		catch (Exception e) {
			
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
		
	}
	
}
