package iss.spring.intro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import iss.spring.intro.components.DummyBean1;
import iss.spring.intro.components.DummyBean2;
import iss.spring.intro.components.SocialNetwork;

@Component
public class MySpringComponent {

	@Autowired
	@Qualifier("Twitter")
	public SocialNetwork tw;
	
	public SocialNetwork fb;

	@Autowired
	@Qualifier("Facebook")
	public void setSocialNetwork(SocialNetwork fb) {
		this.fb = fb;
	}
	
	public SocialNetwork in;
	
	@Autowired
	DummyBean1 db1;
	
	@Autowired
	DummyBean2 db2;

	public MySpringComponent() {
		System.out.println("############################# MySpringComponent default constructor");
	}
	
	@Autowired
	public MySpringComponent(@Qualifier("Instagram") SocialNetwork in) {
		this();
		this.in = in;
		System.out.println("####################################### Constructor DI, Instagram: " + in.hashCode());
	}

}
