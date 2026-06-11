package org.crm.crmticketingapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.crm.crmticketingapi.enums.TicketStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTicketStatusRequest {

    @NotNull(message = "Ticket status is required")
    private TicketStatus status;
}