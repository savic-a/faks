package iss.spring.intro.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import iss.spring.intro.components.DummyBean2;

@Configuration
@ComponentScan(basePackages = { "iss.spring.intro"})
public class AppConfig {

	/**
	 * Ili stavljamo anotaciju @Bean ispred metode koja kreira instancu,
	 * ili stavljamo iznad same klase anotaciju @Component.
	 */
	@Bean
	public DummyBean2 getDummy() {
		return new DummyBean2();
	}
	
}
