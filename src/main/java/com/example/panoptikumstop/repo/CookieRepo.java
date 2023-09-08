package com.example.panoptikumstop.repo;


import com.example.panoptikumstop.model.entity.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookieRepo extends JpaRepository<Cookie, Long> {

    Cookie findByName(String name);


    @Query("SELECT c FROM Cookie c WHERE c.name = :name")
    List<Cookie> findAllByName(String name);

}
