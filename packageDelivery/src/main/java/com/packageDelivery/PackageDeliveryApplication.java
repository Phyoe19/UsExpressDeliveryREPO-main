package com.packageDelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.packageDelivery.entities.Admin;
import com.packageDelivery.entities.Role;
import com.packageDelivery.services.AdminService;

@SpringBootApplication
public class PackageDeliveryApplication implements CommandLineRunner{

	@Autowired
	AdminService adminService;

	public static void main(String[] args) {
		SpringApplication.run(PackageDeliveryApplication.class, args);
	}

	@Override public void run(String... args) throws Exception { 
// TODO Auto-generated method stub 
		
		if(adminService.getAll().size() <= 0) {
		
		Admin admin = new Admin();
	  admin.setEmail("admin@gmail.com");
	  admin.setName("admin");
	  admin.setPassword("admin");
	  admin.setRole(Role.ADMIN);
	  
	  System.out.println(adminService.save(admin)); }
	}

}

@Configuration
class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods(CorsConfiguration.ALL).allowedOrigins("http://localhost:8081");// frontend
																													// url
	}

}
