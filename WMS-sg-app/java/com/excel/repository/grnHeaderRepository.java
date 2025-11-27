package com.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excel.model.GrnHeader;

public interface grnHeaderRepository extends JpaRepository<GrnHeader, Long>{
	List<GrnHeader> findByGrnId(long grnId);
}
