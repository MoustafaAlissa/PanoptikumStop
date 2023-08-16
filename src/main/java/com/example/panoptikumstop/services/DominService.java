package com.example.panoptikumstop.services;

import com.example.panoptikumstop.model.dto.DominDto;
import com.example.panoptikumstop.model.entity.Domin;
import com.example.panoptikumstop.repo.DominRepo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DominService {
    @Autowired
    private DominRepo dominRepo;


    public void Datenspende(DominDto dominDto) {

        String listOfCookies = dominDto.getListOfCookies();
        Domin d = Domin.builder().ListOfCookies(String.valueOf(listOfCookies)).name(dominDto.getUrl()).build();
        dominRepo.save(d);
        System.out.println(d.toString());

    }


    public Domin findByDomin(String domin) {
        var a = dominRepo.findByName(domin);
        System.out.println(a.toString());
        return a;

    }
}
