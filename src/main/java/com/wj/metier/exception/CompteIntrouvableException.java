package com.wj.metier.exception;

/*
 * Pour un travail plus professionnel: Création des exceptions métiers
 * qui hérite de la classe Exception
 */
public class CompteIntrouvableException extends Exception {
	
	String compteIntrouvable;

	public CompteIntrouvableException() {
	}

	
	public CompteIntrouvableException(String compteIntrouvable) {
		this.compteIntrouvable = compteIntrouvable;
		System.out.println(this.compteIntrouvable);
	}
	
	

	public String getCompteIntrouvable() {
		return compteIntrouvable;
	}

	public void setCompteIntrouvable(String compteIntrouvable) {
		this.compteIntrouvable = compteIntrouvable;
	}

	/*public CompteIntrouvableException(erreurs) {
		System.out.println("Compte introuvable");
	}
	*/
	
	
}
