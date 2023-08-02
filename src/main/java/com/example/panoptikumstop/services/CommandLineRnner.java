package com.example.panoptikumstop.services;


import com.example.panoptikumstop.model.entity.Cookie;
import com.example.panoptikumstop.repo.CookieRepo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class CommandLineRnner implements CommandLineRunner {

    private String TEBELFROMGITHUB = "https://github.com/jkwakman/Open-Cookie-Database/raw/master/open-cookie-database.csv";
    private String TEBEL = "open-cookie-database.csv";
    @Autowired
    private CookieRepo cookieRepo;

    @Override
    public void run(String... args) throws Exception {

        String url = TEBELFROMGITHUB;
        File file = new File(TEBEL);

        int spalteIndex1 = 1;
        int spalteIndex2 = 2;
        int spalteIndex3 = 3;
        int spalteIndex4 = 4;
        int spalteIndex5 = 5;
        int spalteIndex6 = 6;
        int spalteIndex7 = 7;

        try {
            FileUtils.copyURLToFile(new URL(url), file);

            CSVReader reader = new CSVReader(new FileReader(file));
            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                Cookie c = Cookie.builder()
                        .platform(row[spalteIndex1])
                        .category(row[spalteIndex2])
                        .name(row[spalteIndex3])
                        .domain(row[spalteIndex4])
                        .description(row[spalteIndex5])
                        .time(row[spalteIndex6])
                        .dataController(row[spalteIndex7])
                        .isChecked(true)
                        .build();
                cookieRepo.save(c);
            }

            file.delete();


        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }


    }

}
