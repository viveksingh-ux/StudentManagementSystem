package com.First.StudentManagementSystem.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {

    private LocalDateTime timeStamp;
    private int status;
    private String Error;
    private Object message;
    private String path;
}
