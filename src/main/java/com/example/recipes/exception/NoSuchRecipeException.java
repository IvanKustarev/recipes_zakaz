package com.example.recipes.exception;

public class NoSuchRecipeException extends RuntimeException {

    public NoSuchRecipeException(String message) {
        super(message);
    }

}
