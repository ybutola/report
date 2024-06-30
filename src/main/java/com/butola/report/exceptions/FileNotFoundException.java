package com.butola.report.exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String fileName) {
        super("File " + fileName + " not found in the database.");
    }
}
