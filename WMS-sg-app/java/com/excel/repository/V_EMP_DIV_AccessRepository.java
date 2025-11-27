package com.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excel.model.V_Emp_Div_Access;

public interface V_EMP_DIV_AccessRepository extends JpaRepository<V_Emp_Div_Access, Long>{
	
	List<V_Emp_Div_Access> findByempId(String empId);

}
