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
        User user2 = new User();
        
        Credentials credentials1 = new Credentials();
        Credentials credentials2 = new Credentials();
        
        credentials1.setUsername("firstUser");
        credentials1.setPassword("password1");
        
        credentials2.setUsername("secondUser");
        credentials2.setPassword("password2");
        
        user1.setCredentials(credentials1);
        user2.setCredentials(credentials2);
        
        Profile profile1 = new Profile();
        Profile profile2 = new Profile();
        
        profile1.setEmail("test@test.com");
        profile1.setPhone("9999999999");
        profile1.setFirstName("Luis");
        profile1.setLastName("Del Mar");
        user1.setProfile(profile1);
        
        profile2.setEmail("test@test2.com");
        profile2.setPhone("2222222222");
        profile2.setFirstName("Luis2");
        profile2.setLastName("Del Mar2");
        user2.setProfile(profile2);

        Tweet tweet1 = new Tweet();
        tweet1.setAuthor(user1);
        tweet1.setContent("Test string");


        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);
        tweetRepository.saveAndFlush(tweet1);
    }
}
