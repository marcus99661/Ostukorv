package com.github.marcus99661.ostukorv;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

	@Autowired
	public KasutajaRepository repository;

	@Autowired
	public AdminRepository adminRepository;

	@Autowired
	public ToodeRepository toodeRepository;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		//repository.deleteAll();
		//adminRepository.deleteAll();
		//toodeRepository.deleteAll();
		/*
		adminRepository.save(new Admin("admin", "admin"));
		repository.save(new Kasutaja("Bob", "bob@gmail.com", "Bobpassword"));
		repository.save(new Kasutaja("Martin", "martin@gmail.com", "Martinpassword"));
		toodeRepository.save(new Toode("ABC1", "Auto", "auto.png", "Hea auto", "10000", "1"));
		toodeRepository.save(new Toode("ABC2", "Tool", "tool.png", "Väga hea Razeri tool", "100", "5"));
		toodeRepository.save(new Toode("ABC3","Monitor", "monitor.png", "Ülimalt hea Samsung 32 tolli, kumber, 144Hz ja väga hea video kvaliteet. Ülimalt hea Samsung 32 tolli, kumber, 144Hz ja väga hea video kvaliteet. Ülimalt hea Samsung 32 tolli, kumber, 144Hz ja väga hea video kvaliteet. Ülimalt hea Samsung 32 tolli, kumber, 144Hz ja väga hea video kvaliteet. Ülimalt hea Samsung 32 tolli, kumber, 144Hz ja väga hea video kvaliteet", "200", "10"));
		toodeRepository.save(new Toode("ABC4","Tass", "tass.png", "Lamba pildiga tass", "5", "500"));
		 */
	}
}
