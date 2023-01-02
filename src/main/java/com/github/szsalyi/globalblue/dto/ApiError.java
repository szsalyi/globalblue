package com.github.szsalyi.globalblue.dto;

import org.springframework.http.HttpStatus;

public record ApiError(HttpStatus status, String message,
                       String errors) {
}
