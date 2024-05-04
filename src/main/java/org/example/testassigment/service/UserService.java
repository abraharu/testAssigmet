package org.example.testassigment.service;

import lombok.RequiredArgsConstructor;
import org.example.testassigment.exception.UserNotFoundException;
import org.example.testassigment.model.User;
import org.example.testassigment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${spring.application.minimum-age}")
    private int minimumAge;

    public User addUser(User user) {
        if (LocalDate.now().minusYears(minimumAge).isBefore(user.getBirthDate())) {
                     throw new IllegalArgumentException("User must be at least " + minimumAge + " years old");
                 }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }

        User existingUser = userOptional.get();
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setBirthDate(updatedUser.getBirthDate());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("The 'from' date cannot be after the 'to' date.");
        }
        return userRepository.findUsersByBirthDateBetween(from, to);
    }

    public User updateUserField(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (updatedUser.getFirstName() != null) user.setFirstName(updatedUser.getFirstName());
            if (updatedUser.getLastName() != null) user.setLastName(updatedUser.getLastName());
            if (updatedUser.getAddress() != null) user.setAddress(updatedUser.getAddress());
            if (updatedUser.getPhoneNumber() != null) user.setPhoneNumber(updatedUser.getPhoneNumber());
            if (updatedUser.getBirthDate() != null) user.setBirthDate(updatedUser.getBirthDate());
            userRepository.save(user);
            return user;
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }
}
