package com.bfs.shopauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class ShopAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopAuthApplication.class, args);
	}

}
