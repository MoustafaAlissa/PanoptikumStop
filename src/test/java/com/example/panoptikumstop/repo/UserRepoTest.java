package com.example.panoptikumstop.repo;

import com.example.panoptikumstop.model.entity.User;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableJpaRepositories(basePackages = {"com.example.panoptikumstop.*"})
@EntityScan(basePackages = {"com.example.panoptikumstop.*"})
@AutoConfigureEmbeddedDatabase
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepoTest {


    @Autowired
    private UserRepo userRepo;

    @Test

    public void testFindByEmail() {

        User user = new User();
        user.setEmail("test@example.com");
        userRepo.save(user);


        Optional<User> foundUser = userRepo.findByEmail("test@example.com");


        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    public void testExistsByEmail() {

        User user = new User();
        user.setEmail("test@example.com");
        userRepo.save(user);


        boolean exists = userRepo.existsByEmail("test@example.com");

        assertTrue(exists);
    }
}
