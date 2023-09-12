package com.example.panoptikumstop.repo;

import com.example.panoptikumstop.model.entity.Domin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Das Interface UserRepo definiert Methoden für die Datenbankzugriffe auf die Benutzerentitäten.
 * Es erweitert das JpaRepository-Interface von Spring Data JPA und bietet Standardfunktionen für die Datenbankmanipulation.
 */
@Repository
public interface DominRepo extends JpaRepository<Domin, Long> {

    Domin findByName(String name);
}
