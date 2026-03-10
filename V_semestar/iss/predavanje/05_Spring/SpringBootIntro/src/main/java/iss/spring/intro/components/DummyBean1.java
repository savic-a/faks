package iss.spring.intro.components;

import org.springframework.stereotype.Component;

@Component
/**
 * Ili stavljamo anotaciju @Bean ispred metode koja kreira instancu, unutar AppConfig klase,
 * ili stavljamo iznad ove klase anotaciju @Component.
 */
public class DummyBean1 {

	public void dump() {
		System.out.println("Dummy Bean1");
	}
}
