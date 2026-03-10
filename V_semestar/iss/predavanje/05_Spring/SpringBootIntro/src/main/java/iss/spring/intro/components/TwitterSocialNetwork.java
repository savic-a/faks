package iss.spring.intro.components;

import org.springframework.stereotype.Component;

@Component("Twitter")
public class TwitterSocialNetwork implements SocialNetwork {

	@Override
	public void publishMessage(String message) {
		System.out.println("Twiter twitted: " + message);
	}

}
