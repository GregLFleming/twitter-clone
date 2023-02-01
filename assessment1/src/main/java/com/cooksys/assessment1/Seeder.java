package com.cooksys.assessment1;

import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.entities.Profile;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.repositories.TweetRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import lombok.AllArgsConstructor;
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

        Tweet tweet1 = new Tweet();
        tweet1.setAuthor(user1);
        tweet1.setContent("Test string");

        userRepository.saveAndFlush(user1);
        tweetRepository.saveAndFlush(tweet1);

        User user2 = new User();
        Credentials credentials2 = new Credentials();
        credentials2.setUsername("test2");
        credentials2.setPassword("password");
        user2.setCredentials(credentials2);
        Profile profile2 = new Profile();
        profile2.setEmail("test2@test.com");
        profile2.setPhone("9999999999");
        profile2.setFirstName("Luis");
        profile2.setLastName("Del Mar");
        user2.setProfile(profile2);

        Tweet tweet2 = new Tweet();
        tweet2.setAuthor(user2);
        tweet2.setContent("Test string");

        userRepository.saveAndFlush(user2);
        tweetRepository.saveAndFlush(tweet2);

    }
}
