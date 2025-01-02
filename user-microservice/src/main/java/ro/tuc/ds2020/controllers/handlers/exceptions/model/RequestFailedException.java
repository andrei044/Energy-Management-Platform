package ro.tuc.ds2020.controllers.handlers.exceptions.model;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class RequestFailedException extends CustomException{
    private static final String MESSAGE =  "Request to microservice failed !";
    private static final HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
    public RequestFailedException(String microservice) {
        super(MESSAGE, httpStatus, microservice, new ArrayList<>());
    }
}
