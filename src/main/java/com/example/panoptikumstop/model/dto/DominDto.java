package com.example.panoptikumstop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@Builder
@ToString
public class DominDto {
    private String url;
    private String listOfCookies;
}
