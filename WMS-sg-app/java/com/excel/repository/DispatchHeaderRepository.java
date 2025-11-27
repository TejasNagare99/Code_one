package com.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excel.model.Dispatch_Header;

public interface DispatchHeaderRepository extends JpaRepository<Dispatch_Header, Long>{
	List<Dispatch_Header> findBydspSumChallanNo(String ChallanNo);
	List<Dispatch_Header> findBydspChallanNo(String ChallanNo);
	
}
