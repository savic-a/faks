package iss.spring.intro.components;

import org.springframework.stereotype.Component;

@Component("Instagram")
public class InstagramSocialNetwork implements SocialNetwork {

	@Override
	public void publishMessage(String message) {
		System.out.println("Instagram story: " + message);
	}

}
