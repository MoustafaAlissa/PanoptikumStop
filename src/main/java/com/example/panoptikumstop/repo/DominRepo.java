package com.example.panoptikumstop.repo;

import com.example.panoptikumstop.model.entity.Domin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DominRepo extends JpaRepository<Domin, Long> {

    Domin findByName(String name);
}
