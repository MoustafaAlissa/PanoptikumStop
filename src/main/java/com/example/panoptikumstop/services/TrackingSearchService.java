package com.example.panoptikumstop.services;


import com.example.panoptikumstop.model.dto.CookieDto;
import com.example.panoptikumstop.model.entity.Cookie;
import com.example.panoptikumstop.repo.CookieRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Dieser Service bietet Funktionen zur Suche und Verwaltung von Cookies und Tracking-Informationen.
 */
@Service
@Slf4j
@AllArgsConstructor
public class TrackingSearchService {
    @Autowired
    private final CookieRepo cookieRepo;
    private final String EASYLIST_COOKIELIST_URL = "https://secure.fanboy.co.nz/fanboy-cookiemonster.txt";

    private final String AND = "And";
    private final String TEXT = "keine Beschreibung, aber Cookies wurden in Easylist gefunden";
    private final String UNKNOWN = "Unknown";
    private Map<String, Boolean> cache = new ConcurrentHashMap<>();
    /**
     * Sucht nach einem bestimmten Wort in einer externen Cookie-Liste.
     *
     * @param word Das zu suchende Wort.
     * @return true, wenn das Wort in der Liste gefunden wurde; false, wenn nicht.
     */
    public boolean searchWordInFile(String word) {
        if (cache.containsKey(word)) {
            return cache.get(word);
        }
        boolean found = false;

        try {
            log.info("Suche in Fanboy list gestartet.");
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(EASYLIST_COOKIELIST_URL);
            CloseableHttpResponse response = client.execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            found = reader.lines().anyMatch(line -> (line).equals(word));

        } catch (IOException e) {

            log.error(e.getMessage());
        }
        log.info("Ergebnis der Suche ist " + found);
        cache.put(word, found);
        return found;
    }

    /**
     * Sucht nach einem Cookie anhand seines Namens.
     *
     * @param name Der Name des gesuchten Cookies.
     * @return Das gefundene Cookie oder ein neues Cookie, wenn es nicht in der Datenbank gefunden wurde.
     */
    public Cookie findCookie(String name) {
        String c = filerString(name);
        Cookie cookie = cookieRepo.findByName(c);
        if (cookie == null) {


            if (searchWordInFile(name)) {

                cookie = addFromEsayList(c);
            } else {

                cookie = add(c);
            }


        }
        return cookie;
    }
    /**
     * Sucht nach einer Liste von Cookies anhand eines JSON-Strings und gibt eine Liste der gefundenen Cookies zurück.
     *
     * @param input Der JSON-String, der eine Liste von Cookies enthält.
     * @return Eine Liste der gefundenen Cookies.
     */
    public List<Cookie> findCookieList(String input) {
        JSONObject jsonObject = new JSONObject(input);
        String list = jsonObject.getString("list");
        String[] parts = list.split("(; )");
        log.info(list);
        return Arrays.stream(parts).parallel().
                map(part -> part.replace("\"", "")).
                map(this::findCookie).filter(Objects::nonNull).
                collect(Collectors.toList());
    }
    /**
     * Fügt ein neues Cookie zur Datenbank hinzu, indem Informationen von einer externen Quelle abgerufen werden.
     *
     * @param name Der Name des neuen Cookies.
     * @return Das hinzugefügte Cookie.
     */
    public Cookie add(String name) {
        Cookie c = null;
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
                        .time(infoList.get(13))
                        .description(infoList.get(3) + AND + infoList.get(6) + AND + infoList.get(9) + AND + infoList.get(14))
                        .isTrack(false)
                        .build();
                cookieRepo.save(c);
                log.info(c.getName() + " : wurde in DB gespeichert.");
            }
        } catch (Exception e) {
            log.warn(name + ": konnte in cookiepedia nicht gefunden ");

        }


        return c;
    }

    /**
     * Fügt ein neues Cookie zur Datenbank hinzu, indem Informationen von einer externen Quelle abgerufen werden.
     * Dies wird aus einer bestimmten Easylist gemacht.
     *
     * @param name Der Name des neuen Cookies.
     * @return Das hinzugefügte Cookie.
     */
    public Cookie addFromEsayList(String name) {
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
                        .time(infoList.get(13))
                        .description(infoList.get(3) + AND + infoList.get(6) + AND + infoList.get(9) + AND + infoList.get(14))
                        .isTrack(true)
                        .build();

            } else {
                c = Cookie.builder()
                        .name(name)
                        .description(TEXT)
                        .isTrack(true)
                        .build();
            }
        } catch (Exception e) {
            c = Cookie.builder()
                    .name(name)
                    .description(TEXT)
                    .isTrack(true)
                    .build();
        }

        cookieRepo.save(c);
        log.info(c.getName() + " : wurde in DB gespeichert.");
        return c;
    }
    /**
     * Löscht alle Cookies mit dem angegebenen Namen aus der Datenbank.
     *
     * @param name Der Name des zu löschenden Cookies.
     */
    public void deleteCookie(String name) {

        cookieRepo.findAllByName(name).stream().findFirst().ifPresent(c -> cookieRepo.delete(c));

    }
    /**
     * Erstellt ein neues Cookie in der Datenbank anhand der übergebenen Informationen.
     *
     * @param cookie Die Informationen für das neue Cookie.
     * @return Das erstellte Cookie.
     */
    public Cookie createCookie(CookieDto cookie) {
        Cookie c = Cookie.builder()
                .dataController(cookie.getDataController())
                .domain(cookie.getDomain())
                .name(cookie.getName())
                .category(cookie.getCategory())
                .description(cookie.getDescription())
                .platform(cookie.getPlatform())
                .time(cookie.getRetentionPeriod())
                .build();
        cookieRepo.save(c);

        return c;
    }
    /**
     * Aktualisiert ein vorhandenes Cookie in der Datenbank anhand der übergebenen Informationen.
     *
     * @param cookie Die aktualisierten Informationen für das Cookie.
     * @return Das aktualisierte Cookie.
     */
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
                    .time(cookie.getRetentionPeriod())
                    .isTrack(cookie.isChecked())
                    .build();
            cookieRepo.save(c);

        }

        return c;
    }
    /**
     * Gibt eine Liste von Cookies zurück, die nicht für das Tracking vorgesehen sind.
     *
     * @return Eine Liste von Cookies, die nicht für das Tracking vorgesehen sind.
     */
    public List<Cookie> getTrackingCookies() {
        return cookieRepo.findAll().stream().filter(c -> !c.isTrack()).collect(Collectors.toList());

    }
    /**
     * Bereinigt einen gegebenen String von bestimmten Zeichen.
     *
     * @param word Der zu bereinigende String.
     * @return Der bereinigte String.
     */
    public String filerString(String word) {

        return word.chars().mapToObj(c -> (char) c).dropWhile(y -> y == '#' || y == '-').map(Object::toString).collect(Collectors.joining());

    }

}
