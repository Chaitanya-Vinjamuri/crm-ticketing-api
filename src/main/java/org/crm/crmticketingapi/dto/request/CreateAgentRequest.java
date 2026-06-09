package org.crm.crmticketingapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.crm.crmticketingapi.enums.Department;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAgentRequest {

    @NotBlank(message = "Agent name is required")
    @Size(
            min = 3,
            max = 100,
            message = "Agent name must be between 3 and 100 characters"
    )
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Department is required")
    private Department department;

    @NotNull(message = "Active status is required")
    private Boolean active;
}