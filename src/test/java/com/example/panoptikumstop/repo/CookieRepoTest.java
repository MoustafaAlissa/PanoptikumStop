package com.example.panoptikumstop.repo;

import com.example.panoptikumstop.model.entity.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CookieRepoTest {

    @Autowired
    private CookieRepo cookieRepo;


    @Test
    void findByName() {
        Cookie cookie= Cookie.builder()
                .name("test")
                .build();

        cookieRepo.save(cookie);

        String nema = cookieRepo.findByName("test").getName();

        assertEquals(cookie.getName(), nema);
    }

}