package com.example.panoptikumstop.repo;

import com.example.panoptikumstop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Das Interface UserRepo definiert Methoden für die Datenbankzugriffe auf die Benutzerentitäten.
 * Es erweitert das JpaRepository-Interface von Spring Data JPA und bietet Standardfunktionen für die Datenbankmanipulation.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
