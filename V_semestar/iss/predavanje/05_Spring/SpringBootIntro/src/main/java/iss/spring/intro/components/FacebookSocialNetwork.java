package iss.spring.intro.components;

import org.springframework.stereotype.Component;

@Component("Facebook")
public class FacebookSocialNetwork implements SocialNetwork {

	@Override
	public void publishMessage(String message) {
		System.out.println("Facebook published: " + message);
	}

}
