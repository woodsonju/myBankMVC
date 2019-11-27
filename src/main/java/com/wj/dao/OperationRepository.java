package com.wj.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wj.dao.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>{
	
	/* Avec une Spring data si on déclare une page 
	 * il faut retourner un objet de type Pageable
	 * */
	@Query("select op from Operation op where op.compte.numCompte like :x order by op.dateOperation desc")
	//Avec @Param on specifie que numCpte correspond au paramètre x
	Page<Operation> listOperation(@Param("x")String numCpte, Pageable pageable);
	
}
