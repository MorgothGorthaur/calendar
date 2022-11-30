package com.example.calendar.exception;

public class EmailNotUnique extends RuntimeException{
    public EmailNotUnique(String email){
        super("this email already used " + email);
    }
}
