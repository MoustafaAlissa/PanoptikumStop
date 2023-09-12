package com.example.panoptikumstop.model.dto;

import lombok.*;
/**
 * Die Klasse DominDto stellt eine Datenübertragungsklasse (DTO) dar.
 */
@AllArgsConstructor
@Data
@Builder
@ToString
@NoArgsConstructor
public class DominDto {
    private String url;
    private String listOfCookies;
}
