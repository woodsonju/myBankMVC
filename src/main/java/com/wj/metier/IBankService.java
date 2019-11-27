package com.wj.metier;

import org.springframework.data.domain.Page;

import com.wj.dao.entities.Compte;
import com.wj.dao.entities.Operation;
import com.wj.metier.exception.CompteIntrouvableException;
import com.wj.metier.exception.SoldeInsuffisantException;
import com.wj.metier.exception.VirementMemeCompteException;

public interface IBankService {
	
	public Compte consulterCompte(String numCpte) throws CompteIntrouvableException;
	public void verser(String numCpte, double montant) throws CompteIntrouvableException; 
	public void retirer(String numCpte, double montant) throws CompteIntrouvableException, SoldeInsuffisantException;
	public void virement(String numCpte1, String numCpte2, double montant) throws CompteIntrouvableException, SoldeInsuffisantException, VirementMemeCompteException;
	public Page<Operation> listOperation(String numCpte, int page, int size);
}
