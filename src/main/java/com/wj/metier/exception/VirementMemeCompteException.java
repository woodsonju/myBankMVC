package com.wj.metier.exception;

public class VirementMemeCompteException extends Exception{
	
	String virementMemeCompteImpossible;
	

	public VirementMemeCompteException() {
	}


	public VirementMemeCompteException(String virementMemeCompteImpossible) {
		this.virementMemeCompteImpossible = virementMemeCompteImpossible;
		System.out.println(virementMemeCompteImpossible);
	}


	public String getVirementMemeCompteImpossible() {
		return virementMemeCompteImpossible;
	}


	public void setVirementMemeCompteImpossible(String virementMemeCompteImpossible) {
		this.virementMemeCompteImpossible = virementMemeCompteImpossible;
	}
	

}
