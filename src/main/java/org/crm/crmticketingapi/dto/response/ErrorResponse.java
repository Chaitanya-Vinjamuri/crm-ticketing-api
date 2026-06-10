package org.crm.crmticketingapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private Timestamp timestamp;

    private int status;

    private String error;

    private String message;
}