package org.example.testassigment.repository;

import org.example.testassigment.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setFirstName("User");
        user1.setLastName("One");
        user1.setBirthDate(LocalDate.of(1995, 5, 15));

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setFirstName("User");
        user2.setLastName("Two");
        user2.setBirthDate(LocalDate.of(1999, 8, 25));

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void testFindUsersByBirthDateBetween() {
        LocalDate start = LocalDate.of(1990, 1, 1);
        LocalDate end = LocalDate.of(2000, 12, 31);
        List<User> users = userRepository.findUsersByBirthDateBetween(start, end);
        assertFalse(users.isEmpty(), "The list of users should not be empty");
    }
}

