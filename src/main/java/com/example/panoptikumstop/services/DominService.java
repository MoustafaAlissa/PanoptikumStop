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

@Service
@Slf4j
@AllArgsConstructor
public class DominService {
    @Autowired
    private DominRepo dominRepo;


    public void Datenspende(DominDto dominDto) {

        String listOfCookies = dominDto.getListOfCookies();
        Domin d = Domin.builder().ListOfCookies(String.valueOf(listOfCookies)).name(filerDomin(dominDto.getUrl())).build();
        dominRepo.save(d);
        log.info(d.toString());

    }


    public Domin findByDomin(String domin) {
        var a = dominRepo.findByName(domin);

        log.info(a.toString());
        return a;

    }

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
