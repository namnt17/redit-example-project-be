package com.example.reditexampleprojectbe.exception;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String vote_not_found) {
        super(vote_not_found);
    }

    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
}
