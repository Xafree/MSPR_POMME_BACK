package com.gostyle.webservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class CustomResponseBody {
    private String timestamp;
    private int status;
    private String httpMessage;
    private String detail;
    private String path;

    public CustomResponseBody(int status, String httpMessage, String detail, String path) {
        this.status = status;
        this.httpMessage = httpMessage;
        this.detail = detail;
        this.path = path;

        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.timestamp = timestamp.format(dateFormatter);
    }
}
