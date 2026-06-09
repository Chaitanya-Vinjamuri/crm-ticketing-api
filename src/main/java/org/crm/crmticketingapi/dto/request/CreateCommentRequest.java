package org.crm.crmticketingapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequest {

    @NotBlank(message = "Comment message is required")
    @Size(
            min = 2,
            max = 1000,
            message = "Comment must be between 2 and 1000 characters"
    )
    private String message;

    @NotNull(message = "Ticket id is required")
    private Long ticketId;

    @NotNull(message = "Agent id is required")
    private Long agentId;
}