package org.crm.crmticketingapi.dto.response;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String status;

    private String message;
}