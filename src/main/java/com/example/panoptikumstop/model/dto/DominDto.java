package com.example.panoptikumstop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class DominDto {
    private String url;
    private String listOfCookies;
}
