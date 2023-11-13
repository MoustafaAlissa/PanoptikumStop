package com.example.panoptikumstop.services;

import com.example.panoptikumstop.model.entity.Cookie;
import com.example.panoptikumstop.repo.CookieRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TrackingSearchServiceTest {

    private TrackingSearchService trackingSearchService;

    @Mock
    private CookieRepo cookieRepo;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trackingSearchService = new TrackingSearchService(cookieRepo);
    }

    @Test
    void testSearchWordInFile() {
        String wordToFind = "/cmpui-banner.js";

        boolean result = trackingSearchService.searchWordInFile(wordToFind);

        assertTrue(result);
    }

    @Test
    void testFindCookie() throws Exception {
        String cookieName = "TestCookie";
        Cookie mockCookie = new Cookie();
        mockCookie.setName(cookieName);

        when(cookieRepo.findByName(cookieName)).thenReturn(mockCookie);

        Cookie result = trackingSearchService.findCookie(cookieName);

        assertNotNull(result);
        assertEquals(cookieName, result.getName());
    }

}
