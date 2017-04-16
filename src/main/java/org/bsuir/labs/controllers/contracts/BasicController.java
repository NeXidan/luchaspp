package org.bsuir.labs.controllers.contracts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BasicController<T> {
    public ResponseEntity<T> success(T model) {
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    public ResponseEntity<T> error(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

}
