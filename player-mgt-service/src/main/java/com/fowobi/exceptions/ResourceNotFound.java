package com.fowobi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceNotFound extends RuntimeException {
    private String message;

}
