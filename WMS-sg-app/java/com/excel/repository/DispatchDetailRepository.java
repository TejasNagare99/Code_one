package com.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excel.model.Dispatch_Detail;

public interface DispatchDetailRepository extends JpaRepository<Dispatch_Detail, Long> {
	//List<Dispatch_Detail> findBydspdtlId(long id);
	List<Dispatch_Detail> findBydspdtlDspId(long id);
}
