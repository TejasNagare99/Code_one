package com.excel.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.excel.model.V_GRN_Header;

public interface V_GRN_HeaderRepository extends JpaRepository<V_GRN_Header,Long>{
	
	List<V_GRN_Header> findBygrnInsUsrId(String empid);
}
