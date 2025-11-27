package com.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excel.model.GrnDetails;

public interface GRNDetailRepository extends JpaRepository<GrnDetails, Long>{
	List<GrnDetails> findBygrndGrnId(long grndGrnId);
	//List<Grn_Details> findBygrndGrnId(long id);
}
