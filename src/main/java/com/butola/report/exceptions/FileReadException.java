package com.butola.report.exceptions;

public class FileReadException extends RuntimeException {
    public FileReadException(String fileName) {
        super("Error occured while reading the " + fileName + ".");
    }
}
