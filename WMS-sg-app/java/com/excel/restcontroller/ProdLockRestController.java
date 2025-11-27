package com.excel.restcontroller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.exception.MedicoException;
import com.excel.service.ProdLockService;
import com.excel.utility.CodifyErrors;

@RestController
@RequestMapping("/rest")
public class ProdLockRestController {
	
	@Autowired ProdLockService prodLockService;
	 
	@GetMapping("/set-prod-lock-for-location")
	public boolean setProductLockForProductByLocationId(@RequestParam Long prodId, @RequestParam Long locId, @RequestParam String empId, HttpSession session) {
		try {
			return this.prodLockService.setProdLock(prodId, locId, empId);
		} catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return false;
	}
	
	@GetMapping("/release-prod-loc-by-empId")
	public boolean relaeseProdLockByEmpId(@RequestParam Long prodId, @RequestParam Long locId, @RequestParam String empId, HttpSession session) {
		try {
			return this.prodLockService.deleteByProdIdLocIdUserId(prodId, locId, empId);
		} catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return false;
	}

	@PostMapping("/set-prod-lock-for-loc")
	public boolean setProductLockForProdByLocId(@RequestParam Long prodId, @RequestParam Long locId, @RequestParam String empId, HttpSession session) {
		try {
			return this.prodLockService.setProdLockForLoc(prodId, locId, empId);
		} catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return false;
	}
	
	@PostMapping("/release-prod-loc-for-loc")
	public boolean releaseProdLockByEmpId(@RequestParam Long prodId, @RequestParam Long locId, @RequestParam String empId, HttpSession session) {
		try {
			return this.prodLockService.deleteByProdIdLocIdUserId(prodId, locId, empId);
		} catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return false;
	}
}
