package org.example.testassigment.service;

import org.example.testassigment.exception.UserNotFoundException;
import org.example.testassigment.model.User;
import org.example.testassigment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser_Success() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.now().minusYears(20));

        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName(), "First name should be John");
        assertEquals("Doe", savedUser.getLastName(), "Last name should be Doe");
        verify(userRepository).save(user);
    }

    @Test
    public void testAddUser_FailureUnderAge() {
        User user = new User();
        user.setEmail("young@example.com");
        user.setFirstName("Young");
        user.setLastName("User");
        user.setBirthDate(LocalDate.now().minusYears(10));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
        assertTrue(exception.getMessage().contains("User must be at least"), "Exception message should indicate age restriction");
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("existing@example.com");
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        existingUser.setBirthDate(LocalDate.of(1990, 1, 1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail("updated@example.com");
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");
        updatedUser.setBirthDate(LocalDate.of(1991, 1, 1));

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        verify(userRepository).save(existingUser);

        assertEquals("Updated", result.getFirstName());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals(LocalDate.of(1991, 1, 1), result.getBirthDate());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void testFindUsersByBirthDateRange() {
        LocalDate start = LocalDate.of(1990, 1, 1);
        LocalDate end = LocalDate.of(2000, 12, 31);
        userService.findUsersByBirthDateRange(start, end);
        verify(userRepository).findUsersByBirthDateBetween(start, end);
    }

    @Test
    public void testUpdateUserField_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUserField(1L, new User()));
    }

    @Test
    public void testUpdateUserField_Success() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("email@example.com");
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        existingUser.setBirthDate(LocalDate.of(1990, 1, 1));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = new User();
        updatedUser.setFirstName("Updated");

        User result = userService.updateUserField(1L, updatedUser);

        assertEquals("Updated", result.getFirstName(), "First name should be updated.");

        assertEquals("email@example.com", result.getEmail(), "Email should not change.");
        assertEquals("User", result.getLastName(), "Last name should not change.");
        assertEquals(LocalDate.of(1990, 1, 1), result.getBirthDate(), "Birth date should not change.");

        verify(userRepository).save(existingUser);
    }
}