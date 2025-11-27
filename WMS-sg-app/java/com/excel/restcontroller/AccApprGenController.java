package com.excel.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.CustomAccTranGeneration;
import com.excel.service.AccTranService;

@RestController
@RequestMapping("/rest")
public class AccApprGenController {
	
	@Autowired private AccTranService accTranService;

	@GetMapping("/auto-gen-approvals")
	public void saveTranRecords(@RequestParam Long loc_id,@RequestParam String approver_id,@RequestParam String comp_cd) {
		try {
//			for(CustomAccTranGeneration cs :this.accTranService.getAccTranTypeByLocId(loc_id)) {
//				System.out.println("cs"+cs.toString());
//			}
			this.accTranService.saveApprTransForLocation(loc_id, approver_id,comp_cd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
