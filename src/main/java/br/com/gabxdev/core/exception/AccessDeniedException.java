package br.com.gabxdev.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccessDeniedException extends ResponseStatusException {
    public AccessDeniedException() {
        super(HttpStatus.FORBIDDEN, "You do not have permission to perform this action.");
    }
}