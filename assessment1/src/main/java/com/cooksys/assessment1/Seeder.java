package com.cooksys.assessment1;

import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.Profile;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Seeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    public void run(String... args) throws Exception{
        User user1 = new User();
        Credentials credentials1 = new Credentials();
        credentials1.setUsername("test");
        credentials1.setPassword("password");
        user1.setCredentials(credentials1);
        Profile profile1 = new Profile();
        profile1.setEmail("test@test.com");
        profile1.setPhone("9999999999");
        profile1.setFirstName("Luis");
        profile1.setLastName("Del Mar");
        user1.setProfile(profile1);

        userRepository.saveAndFlush(user1);

        System.out.println(userRepository.findAll());

    }
}
