package com.example.panoptikumstop.services;

import com.example.panoptikumstop.model.dto.DominDto;
import com.example.panoptikumstop.model.entity.Domin;
import com.example.panoptikumstop.repo.DominRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * Dieser Service bietet Funktionen zur Verarbeitung von Domains und Datenspenden.
 */
@Service
@Slf4j
@AllArgsConstructor
public class DominService {
    @Autowired
    private DominRepo dominRepo;
    /**
     * Verarbeitet eine Datenspende für eine bestimmte Domain.
     *
     * @param dominDto Die Informationen zur Datenspende, einschließlich der Liste von Cookies und der URL der Domain.
     */

    public void Datenspende(DominDto dominDto) {

        String listOfCookies = dominDto.getListOfCookies();
        Domin d = Domin.builder().ListOfCookies(String.valueOf(listOfCookies)).name(filerDomin(dominDto.getUrl())).build();
        dominRepo.save(d);
        log.info(d.toString());

    }
    /**
     * Sucht nach einer Domain in der Datenbank anhand ihres Namens.
     *
     * @param domin Der Name der gesuchten Domain.
     * @return Die gefundene Domain oder null, wenn keine gefunden wurde.
     */

    public Domin findByDomin(String domin) {
        var a = dominRepo.findByName(domin);

        log.info(a.toString());
        return a;

    }
    /**
     * Extrahiert den Namen der Domain aus einer URL.
     *
     * @param url Die URL, aus der der Domain-Name extrahiert werden soll.
     * @return Der extrahierte Domain-Name oder null, wenn die URL ungültig ist.
     */
    public String filerDomin(String url) {
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            if (host != null && !host.isEmpty()) {
                return host.startsWith("www.") ? host.substring(4) : host;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
