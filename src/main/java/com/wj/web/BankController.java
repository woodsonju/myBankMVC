package com.wj.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wj.dao.entities.Compte;
import com.wj.dao.entities.Operation;
import com.wj.metier.IBankService;
import com.wj.metier.exception.CompteIntrouvableException;
import com.wj.metier.exception.SoldeInsuffisantException;
import com.wj.metier.exception.VirementMemeCompteException;

@Controller
public class BankController {
	
	@Autowired
	private IBankService bankService;
	
	@RequestMapping("/operations")
	public String index(/*Model model, int p, int s, String numCpte*/) {
		
		return "comptes";
	}
	
	@RequestMapping("/consulterCompte")
	public String consulter(Model model, String numCpte,
			@RequestParam(name="page", defaultValue="0")int page,
			@RequestParam(name="size", defaultValue="5")int size){
		
		model.addAttribute("numCpte", numCpte);
		Compte cp;
		
		try {
			cp = bankService.consulterCompte(numCpte);
			
			//Page<Operation> pageOperation = bankService.listOperation(numCpte, 0, 4);
			Page<Operation> pageOperations = bankService.listOperation(numCpte, page, size);
			//La methode getContent permet de retourner la liste des opérations
			model.addAttribute("listOperations", pageOperations.getContent());
			
			//Recuperation du nombre de pages
			int[] pages = new int[pageOperations.getTotalPages()];
			model.addAttribute("pages", pages); //transferer page vers le modele
			
			//Récuperation de la taille de chaque page
			//model.addAttribute("size", size);
			
			//Ajout de la page courante
			model.addAttribute("pageCourante", page);
			
			model.addAttribute("compte", cp);
		} catch (CompteIntrouvableException e) {
			//e.printStackTrace();
			/*
			 * Je stock l'exception dans le model.
			 * Car le message de l'exception je vais l'afficher dans la vue
			 */
			model.addAttribute("compteIntrouvableException", e.getCompteIntrouvable());
		}

		return "comptes";
	}
	
	@RequestMapping(value="/saveOperation", method=RequestMethod.POST)
	public String saveOperation(Model model, String typeOperation, String numCpte,
		double montant, String numCpte2) {
		
		try {
			if(typeOperation.equals("versement")) {
				bankService.verser(numCpte, montant);	
			} 
			else if(typeOperation.equals("retrait")) {
				bankService.retirer(numCpte, montant);
			}
			else if(typeOperation.equals("virement")) {    
				bankService.virement(numCpte, numCpte2, montant);
			}
			
		} catch (CompteIntrouvableException e) {	
			model.addAttribute("compteIntrouvableException", e.getCompteIntrouvable());
			return "redirect:/consulterCompte?numCpte="+numCpte+
					"&compteIntrouvableException="+e.getCompteIntrouvable();
		
		} catch( SoldeInsuffisantException e) {
			model.addAttribute("soldeInsuffisantException", e.getSoldeInsuffisant());
			return "redirect:/consulterCompte?numCpte="+numCpte+
					"&soldeInsuffisantException="+e.getSoldeInsuffisant();
		} catch (VirementMemeCompteException e) {
			model.addAttribute("virementMemeCompteException", e.getVirementMemeCompteImpossible());
			return "redirect:/consulterCompte?numCpte="+numCpte+
					"&virementMemeCompteException="+e.getVirementMemeCompteImpossible();
		}
		return "redirect:/consulterCompte?numCpte="+numCpte; //numCpte car c'est une operation qui concerne le même compte
	}
	
}
