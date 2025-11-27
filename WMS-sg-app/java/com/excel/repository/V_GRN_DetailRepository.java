package com.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excel.model.V_Grn_Details;

public interface V_GRN_DetailRepository extends JpaRepository<V_Grn_Details, Long> {
	List<V_Grn_Details> findByGrndGrnId(long grnId);
}
