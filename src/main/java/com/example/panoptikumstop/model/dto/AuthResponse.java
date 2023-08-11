package com.example.panoptikumstop.model.dto;

public record AuthResponse(String token, boolean datenSpenden, String role) {
}
