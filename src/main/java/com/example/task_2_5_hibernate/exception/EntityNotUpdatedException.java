package com.example.task_2_5_hibernate.exception;

public class EntityNotUpdatedException extends RuntimeException{
    public EntityNotUpdatedException(String message) {
        super(message);
    }
}
