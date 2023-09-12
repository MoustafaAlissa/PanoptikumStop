package com.example.panoptikumstop.services;
import com.example.panoptikumstop.model.dto.DominDto;
import com.example.panoptikumstop.model.entity.Cookie;
import com.example.panoptikumstop.model.entity.Domin;
import com.example.panoptikumstop.repo.CookieRepo;
import com.example.panoptikumstop.repo.DominRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DominServiceTest {

    private DominService dominService;

    @Mock
    private DominRepo dominRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dominService = new DominService(dominRepo);
    }

    @Test
    void testDatenspende() {
        DominDto dominDto = new DominDto();
        dominDto.setUrl("https://example.com");
        dominDto.setListOfCookies("cookie1,cookie2,cookie3");

        when(dominRepo.save(Mockito.any())).thenReturn(new Domin());

        dominService.Datenspende(dominDto);

        // Hier können Sie weitere Assertions hinzufügen, um sicherzustellen, dass die Methode wie erwartet funktioniert.
    }

    @Test
    void testFindByDomin() {
        String dominName = "example.com";
        Domin mockDomin = new Domin();
        mockDomin.setName(dominName);

        when(dominRepo.findByName(dominName)).thenReturn(mockDomin);

        Domin result = dominService.findByDomin(dominName);

        assertEquals(dominName, result.getName());
    }
}
