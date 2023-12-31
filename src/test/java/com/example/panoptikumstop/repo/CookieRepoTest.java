package com.example.panoptikumstop.repo;

import com.example.panoptikumstop.model.entity.Cookie;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(SpringRunner.class)

@EnableJpaRepositories(basePackages = {"com.example.panoptikumstop.*"})
@EntityScan(basePackages = {"com.example.panoptikumstop.*"})
@DataJpaTest
@AutoConfigureEmbeddedDatabase

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CookieRepoTest {

@Autowired
    private CookieRepo cookieRepo;


    @Test
    void findByName() {
        Cookie cookie= Cookie.builder()
                .name("test")
                .description("test")
                .category("test")
                .platform("test")
                .domain("test")
                .dataController("test")
                .isTrack(false)
                .time("test")
                .build();

        cookieRepo.save(cookie);

        String nema = cookieRepo.findByName("test").getName();

        assertEquals(cookie.getName(), nema);
    }

}