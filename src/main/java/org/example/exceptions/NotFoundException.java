package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.*;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Id not found.")
public class NotFoundException extends RuntimeException {

    public NotFoundException() {

    }
}
