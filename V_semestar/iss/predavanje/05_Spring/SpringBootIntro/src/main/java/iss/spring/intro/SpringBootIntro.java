package iss.spring.intro;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import iss.spring.intro.configuration.AppConfig;

@SpringBootApplication
public class SpringBootIntro {
	
	public static void main(String[] args) {
//		SpringApplication.run(SpringBootIntro .class, args);
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		MySpringComponent comp = context.getBean(MySpringComponent.class);

		comp.db1.dump();
		
		comp.db2.dump();
		
		comp.tw.publishMessage("Field DI: " + comp.tw.hashCode());
		
		comp.fb.publishMessage("Setter DI: " + comp.fb.hashCode());
		
		comp.in.publishMessage("Constructor DI: " + comp.in.hashCode());
		
		context.close();  // close the context
	}

} 