package com.example.panoptikumstop.services;


import com.example.panoptikumstop.model.dto.CookieDto;
import com.example.panoptikumstop.model.entity.Cookie;
import com.example.panoptikumstop.repo.CookieRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TrackingSearchService {
    @Autowired
    private CookieRepo cookieRepo;
    private String EASYLIST_COOKIELIST_URL = "https://secure.fanboy.co.nz/fanboy-cookiemonster.txt";

    private String AND="And";
    private String TEXT1="Cookies wurden in Easylist gefunden, aber nicht in Cookiepedia.";
    private String TEXT2="Cookies wurden in Easylist gefunden, aber nicht in Cookiepedia.";

    public boolean searchWordInFile(String word) {

        boolean found = false;

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(EASYLIST_COOKIELIST_URL);
            CloseableHttpResponse response = client.execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            found = reader.lines().anyMatch(line  -> filerString(line).equals(word));
        } catch (IOException e) {

           log.error(e.getMessage());
        }
        return found;
    }



    public Cookie findCookie(String name) {

        Cookie cookie = cookieRepo.findByName(name);
        if (cookie == null && searchWordInFile(name)) {

                String c = filerString(name);
                cookie = add(c);

        }
        return cookie;
    }

    public List<Cookie> findCookieList(String  list) {
        String[] parts = list.substring(1, list.length() - 1).split(";");
        return Arrays.stream(parts).map(this::findCookie).collect(Collectors.toList());
    }

    public Cookie add(String name) {
        Cookie c;
        String apiUrl = "https://cookiepedia.co.uk/cookies/" + name;

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            String html = response.getBody();
            Document document = Jsoup.parse(html);
            Element contentElement = document.getElementById("content-left");
            List<String> infoList = new ArrayList<>();

            if (contentElement != null) {
                Elements elements = contentElement.select("p, strong");
                elements.stream().map(Element::text).forEach(infoList::add);

                c = Cookie.builder()
                        .name(name)
                        .category(infoList.get(2))
                        .description(infoList.get(0))
                        .retentionPeriod(infoList.get(7) +AND + infoList.get(8))
                        .description(infoList.get(3) +AND +infoList.get(6) +AND + infoList.get(9))
                        .isChecked(false)
                        .build();
            } else {
                c = Cookie.builder()
                        .name(name)
                        .description(TEXT2)
                        .isChecked(false)
                        .build();
            }
        } catch (Exception e) {
            c = Cookie.builder()
                    .name(name)
                    .description(TEXT1)
                    .isChecked(false)
                    .build();
        }

        cookieRepo.save(c);
        return c;
    }

    public void deleteCookie(String name) {

        cookieRepo.findAllByName(name).stream().findFirst().ifPresent(c -> cookieRepo.delete(c));

    }

    public Cookie createCookie(CookieDto cookie) {
        Cookie c = Cookie.builder()
                .dataController(cookie.getDataController())
                .domain(cookie.getDomain())
                .name(cookie.getName())
                .category(cookie.getCategory())
                .description(cookie.getDescription())
                .platform(cookie.getPlatform())
                .retentionPeriod(cookie.getRetentionPeriod())
                .build();
        cookieRepo.save(c);

        return c;
    }

    public Cookie updateCookie(CookieDto cookie) {
        Cookie c = cookieRepo.findByName(cookie.getName());
        if (c != null) {
            c = Cookie.builder()
                    .name(cookie.getName())
                    .dataController(cookie.getDataController())
                    .domain(cookie.getDomain())
                    .category(cookie.getCategory())
                    .description(cookie.getDescription())
                    .platform(cookie.getPlatform())
                    .retentionPeriod(cookie.getRetentionPeriod())
                    .isChecked(cookie.isChecked())
                    .build();
            cookieRepo.save(c);

        }

        return c;
    }

    public List<Cookie> getUncheckedList() {
        return cookieRepo.findAll().stream().filter(c -> !c.isChecked()).collect(Collectors.toList());

    }

    public String filerString(String word) {

        return word.chars().mapToObj(c -> (char) c).dropWhile(y -> y == '#' || y == '-').map(Object::toString).collect(Collectors.joining());

    }

}
