package com.wj.metier;

import java.util.Date;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wj.dao.ClientRepository;
import com.wj.dao.CompteRepository;
import com.wj.dao.OperationRepository;
import com.wj.dao.entities.Compte;
import com.wj.dao.entities.CompteCourant;
import com.wj.dao.entities.Operation;
import com.wj.dao.entities.Retrait;
import com.wj.dao.entities.Versement;
import com.wj.metier.exception.CompteIntrouvableException;
import com.wj.metier.exception.SoldeInsuffisantException;
import com.wj.metier.exception.VirementMemeCompteException;

/*
 * Pour que Spring puisse instancier cette classe au démarrage il faut 
 * utiliser l'annotation Service. Cette annotation est utilisée pour les
 * objets de la couche métier.
 * On peut utiliser aussi l'annotation Transactional pour demander à Spring
 * de gerer les transaction. 
 * Donc toutes les méthodes seront transactionnelle. 
 * Ainsi, on assure que les méthode s'éxecute correctement 
 * Sinon on annule tout
 */
@Service
@Transactional  
public class BankServiceImpl implements IBankService{
	
	@Autowired   //Sinon on aura des nullPointerException
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	
	@Override
	public Compte consulterCompte(String numCpte) throws CompteIntrouvableException {
		Compte cp = compteRepository.findOne(numCpte);
		if(cp==null) 
			throw new CompteIntrouvableException("Compte introuvable");	
		return cp;
	}

	@Override
	public void verser(String numCpte, double montant) throws CompteIntrouvableException{
		Compte cp = this.consulterCompte(numCpte);
		Versement versement = new Versement(new Date(), montant, cp);
		operationRepository.save(versement);
		cp.setSolde(cp.getSolde()+montant); 
		compteRepository.save(cp);
	}

	@Override
	public void retirer(String numCpte, double montant) throws CompteIntrouvableException
	,SoldeInsuffisantException{
		
		Compte cp = this.consulterCompte(numCpte);
		
		/*
		 * Avant le retrait, verifier si le solde est suffisant pour rétirer
		 * Aussi, Cela depend si c'est un compte courant(prendre en considération
		 * le découvert)  ou un compte epargne (le découvert est égale à zero)
		 */
		double facilitesCaisse = 0;
		if(cp instanceof CompteCourant)
			facilitesCaisse=((CompteCourant) cp).getDecouvert();  //On récupère le découvert du compte	
		if(cp.getSolde()+facilitesCaisse < montant)
			throw new SoldeInsuffisantException("Solde insuffisant");
		Retrait retrait = new Retrait(new Date(), montant, cp);
		operationRepository.save(retrait);
		cp.setSolde(cp.getSolde() - montant);
		compteRepository.save(cp);
	}

	@Override  //Virement de cp1 vers cp2
	public void virement(String numCpte1, String numCpte2, double montant) throws CompteIntrouvableException, SoldeInsuffisantException, VirementMemeCompteException{
		if(numCpte1.equals(numCpte2))
			throw new VirementMemeCompteException("Impossible d'effectuer un virement sur le meme compte");
		this.retirer(numCpte1, montant);
		this.verser(numCpte2, montant);
	}

	@Override
	public Page<Operation> listOperation(String numCpte, int page, int size) {
		
		//PageRequest est une implementation de  l'interface Pageable
		return operationRepository.listOperation(numCpte, 
				new PageRequest(page, size));
	}

}
