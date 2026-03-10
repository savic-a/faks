package iss.spring.intro.components;

//@Component
/**
 * Ili stavljamo anotaciju @Bean ispred metode koja kreira instancu, unutar AppConfig klase,
 * ili stavljamo iznad ove klase anotaciju @Component.
 */
public class DummyBean2 {

	public void dump() {
		System.out.println("Dummy Bean2");
	}
}
