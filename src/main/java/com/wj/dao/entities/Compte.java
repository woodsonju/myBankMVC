package com.wj.dao.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
/* Annotation @Inheritance : Car il y a des classes qui vont heriter de cet objet
 * Il existe 3 strategies: 
 * SINGLE_TABLE : une seule table compte dans laquelle on va stoker tous les types 
 * de comptes.Le problème avec SINGLE_TABLE, c'est que pour chaque enregistrement
 * il y a des colonnes qui vont rester null. 
 * TABLE_PER_CLASS: création d'une table pour chaque classe fille : 
 * Il va créer une table pour le CompteCourant et une table pour le CompteEpargne.
 * On peut utiliser cette strategie, quand il y a une grande différence entre les 
 * classes filles
 * JOINED : Il va créer une table pour representer la  classe Compte
 * et une table pour chaque classe fille CompteCourant, CompteEpargne; et les lient
 * par une jointure.
 * On utilisant la strategie SINGLE_TABLE, il y a une colonne qui va s'ajouter
 * à la table compte. 
 * Cette colonne on l'a décrit avec la notation DiscriminatorColumn.
 * Par défaut cette colonne s'appelle dType(discriminatorType).
 * Nous on l'appelle TYPE_CPTE; par défaut son type est VARCHAR et son length : 255
 * Sinon on peut le specifier.
 * Il faut que la classe soit abstraite 
 */
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name="TYPE_CPTE", 
discriminatorType=DiscriminatorType.STRING, length=2)
public abstract class Compte implements Serializable{
	
	@Id
	private String numCompte;
	private Date dateCreation;
	private double solde;
	
	@ManyToOne
	@JoinColumn(name="CODE_CLI")
	private Client client;
	
	@OneToMany(mappedBy="compte") //Par défaut le mode utiliser est le mode LAZY
	private Collection<Operation> operations;
	
	
	public Compte() {
		//super();
	}


	public Compte(String numCompte, Date dateCreation, double solde, Client client) {
		super();
		this.numCompte = numCompte;
		this.dateCreation = dateCreation;
		this.solde = solde;
		this.client = client;
	}


	public String getNumCompte() {
		return numCompte;
	}


	public void setNumCompte(String numCompte) {
		this.numCompte = numCompte;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public double getSolde() {
		return solde;
	}


	public void setSolde(double solde) {
		this.solde = solde;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	public Collection<Operation> getOperations() {
		return operations;
	}


	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}
	

}
