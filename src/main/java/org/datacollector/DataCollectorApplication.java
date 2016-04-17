package org.datacollector;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = { "org.datacollector" })
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@EnableWebSecurity
@EnableTransactionManagement
public class DataCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataCollectorApplication.class, args);
	}
	
	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
		return hemf.getSessionFactory();
	}

}
