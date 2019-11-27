package com.wj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wj.dao.ClientRepository;
import com.wj.dao.CompteRepository;
import com.wj.dao.OperationRepository;
import com.wj.metier.IBankService;

/*
 * Une nouvelle méthode : En implementant mon application avec CommandLineRunner; 
 * elle nous oblige à rédefinir une méthode run. Donc quand spring boot
 * termine de démarrer, il appelle la méthode run. Les tests je les fais dans 
 * la méthode run 
 */

@SpringBootApplication
public class MyBankApplication implements CommandLineRunner{
	

	//Test de la couche DAO
	@Autowired    		/*C'est identique à  ClientRepository clientRepository
	 									= ctx.getBean(ClientRepository.class);*/
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	
	
	
	//Test de la couche Métier
	@Autowired
	IBankService bankService;
	
	public static void main(String[] args) {
		SpringApplication.run(MyBankApplication.class, args);		
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		//Test de la couche DAO
		/*	 
		Client c1 = clientRepository.save(new Client("Woodson", "woodson@gmail.com"));
		Client c2 = clientRepository.save(new Client("Louis", "louis@gmail.com"));
		Client c3 = clientRepository.save(new Client("matheo", "matheo@gmail.com"));
		
		Compte cp1 = compteRepository.save(
				new CompteCourant("c1", new Date(), 90000, c1, 6000));
		Compte cp2 = compteRepository.save(
				new CompteEpargne("c2", new Date(), 6000, c2, 5.5));
		Compte cp3 = compteRepository.save(
				new CompteCourant("c3", new Date(), 1000000, c3, 50000));
		
		operationRepository.save(
				new Versement(new Date(), 9000, cp1));
		operationRepository.save(
				new Versement(new Date(), 6000, cp1));
		operationRepository.save(
				new Versement(new Date(), 2300, cp1));
		operationRepository.save(
				new Retrait(new Date(), 9000, cp1));
		
		operationRepository.save(
				new Versement(new Date(), 2300, cp2));
		operationRepository.save(
				new Versement(new Date(), 6000, cp2));
		operationRepository.save(
				new Versement(new Date(), 2300, cp2));
		operationRepository.save(
				new Retrait(new Date(), 9000, cp2));
		
		operationRepository.save(
				new Versement(new Date(), 23000, cp3));
		operationRepository.save(
				new Versement(new Date(), 60000, cp3));
		operationRepository.save(
				new Versement(new Date(), 23000, cp3));
		operationRepository.save(
				new Retrait(new Date(), 90000, cp3));
		
		
		
		//Test de la couche de métier
		bankService.verser("c1", 100000);
	*/
	}
	

}
