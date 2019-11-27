package com.wj.metier.exception;

public class SoldeInsuffisantException extends Exception{
	
	String soldeInsuffisant;
	
	public SoldeInsuffisantException() {
		System.out.println("Solde insuffisant");
	}

	public SoldeInsuffisantException(String soldeInsuffisant) {
		this.soldeInsuffisant = soldeInsuffisant;
		System.out.println(this.soldeInsuffisant);
	}

	public String getSoldeInsuffisant() {
		return soldeInsuffisant;
	}

	public void setSoldeInsuffisant(String soldeInsuffisant) {
		this.soldeInsuffisant = soldeInsuffisant;
	}
	
}
