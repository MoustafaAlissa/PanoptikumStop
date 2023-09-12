package com.example.panoptikumstop.repo;


import com.example.panoptikumstop.model.entity.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Das Interface UserRepo definiert Methoden für die Datenbankzugriffe auf die Benutzerentitäten.
 * Es erweitert das JpaRepository-Interface von Spring Data JPA und bietet Standardfunktionen für die Datenbankmanipulation.
 */
@Repository
public interface CookieRepo extends JpaRepository<Cookie, Long> {

    Cookie findByName(String name);


    @Query("SELECT c FROM Cookie c WHERE c.name = :name")
    List<Cookie> findAllByName(String name);

}
