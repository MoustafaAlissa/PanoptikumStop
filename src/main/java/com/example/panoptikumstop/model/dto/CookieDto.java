package com.example.panoptikumstop.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CookieDto {

    private String platform;
    private String category;
    private String name;
    private String domain;
    private String description;
    private String retentionPeriod;
    private String dataController;
    private boolean checked;

}
