package com.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excel.model.ManualDispatchList;

public interface ManualDispatchListRepository extends JpaRepository<ManualDispatchList, Long>{
	List<ManualDispatchList> findBydspChallanNo(String ChallanNo);
}
