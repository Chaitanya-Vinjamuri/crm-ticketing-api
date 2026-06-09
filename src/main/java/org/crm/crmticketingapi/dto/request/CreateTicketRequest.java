package org.crm.crmticketingapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.crm.crmticketingapi.enums.IssueType;
import org.crm.crmticketingapi.enums.Priority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTicketRequest {

    @NotBlank(message = "Title is required")
    @Size(
            min = 5,
            max = 200,
            message = "Title must be between 5 and 200 characters"
    )
    private String title;

    @NotBlank(message = "Description is required")
    @Size(
            min = 10,
            max = 1000,
            message = "Description must be between 10 and 1000 characters"
    )
    private String description;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Issue type is required")
    private IssueType issueType;

    @NotNull(message = "Agent id is required")
    private Long agentId;
}