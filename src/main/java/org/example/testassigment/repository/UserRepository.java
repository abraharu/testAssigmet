package org.example.testassigment.repository;

import org.example.testassigment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.birthDate >= :from AND u.birthDate <= :to")
    List<User> findUsersByBirthDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
