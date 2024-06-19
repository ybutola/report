package com.butola.report.exceptions;

import io.swagger.v3.oas.models.security.SecurityScheme;

public class TemplateAlreadyExistsException extends RuntimeException {
    public TemplateAlreadyExistsException(String companyName, Integer year, Integer version) {
        super("File already exists for Company " + companyName + " Year " + year + " and Version " + version);
    }
}
