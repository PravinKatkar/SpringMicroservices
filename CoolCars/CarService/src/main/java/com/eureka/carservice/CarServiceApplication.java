package com.eureka.carservice;

import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@EnableDiscoveryClient
@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner init(CarRepository repository) {
		return args -> {
			Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti", "AMC Gremlin", "Triumph Stag",
					"Ford Pinto", "Yugo GV").forEach(name -> {
						repository.save(new Car(name));
					});
			repository.findAll().forEach(System.out::println);
		};
	}
}

@Data
@NoArgsConstructor
@Entity
class Car {
	public Car(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	private Long id;
	@NonNull
	private String name;
}

@RepositoryRestResource
interface CarRepository extends JpaRepository<Car, Long> {
}