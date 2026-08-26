package com.packt.cardatabase;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.AppUserRepository;
import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@SpringBootApplication
@EnableMethodSecurity
public class CardatabaseApplication implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);
	
	private final CarRepository carRepository;
	private final OwnerRepository ownerRepository;
	private final AppUserRepository appUserRepository;
	
	//생성자주입
	public CardatabaseApplication(CarRepository carRepository, OwnerRepository ownerRepository, AppUserRepository appUserRepository) {
		this.carRepository = carRepository;
		this.ownerRepository = ownerRepository;
		this.appUserRepository = appUserRepository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("Application stared");
	}
	
	@Override
	public void run(String... args) throws Exception {
		Owner owner1 = new Owner("가", "길동");
		Owner owner2 = new Owner("나", "두식");
		
		ownerRepository.saveAll(Arrays.asList(owner1, owner2));
		
		carRepository.save(new Car("ford", "mustang", "red", "ADF-1121", 2023, 59000, owner1));
		carRepository.save(new Car("ford", "leaf", "white", "SSJ-3002", 2024, 69000, owner2));
		carRepository.save(new Car("ford", "prius", "silver", "KKO-0212", 2025, 79000, owner2));
//		
//		List<Car> list = carRepository.findAll();
		
//		for(Car car : list) {
//			logger.info("brand : {}, model : {}, model_year : {}, owner : {}", car.getBrand(), car.getModel(), car.getModelYear(), "");
//			logger.info("brand : {}, model : {}, model_year : {}, owner : {}", car.getBrand(), car.getModel(), car.getModelYear(), car.getOwner().getFirstname()+car.getOwner().getLastname());
//		}
		
		AppUser user = new AppUser("user", new BCryptPasswordEncoder().encode("user"), "USER");   
		appUserRepository.save(user);
		
		AppUser admin = new AppUser("admin", new BCryptPasswordEncoder().encode("admin"), "ADMIN");   
		appUserRepository.save(admin);
	}

}
