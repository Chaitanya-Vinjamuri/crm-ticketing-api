package org.crm.crmticketingapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.crm.crmticketingapi.enums.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

@Entity
@Table(
        name = "agents",
        indexes = {
                @Index(
                        name = "idx_agent_department",
                        columnList = "department"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true,
            length = 20
    )
    private String agentCode;

    @NotBlank(message = "Agent name is required")
    @Size(
            min = 3,
            max = 100,
            message = "Agent name must be between 3 and 100 characters"
    )
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String email;

    @NotNull(message = "Department is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @NotNull(message = "Active status is required")
    @Column(nullable = false)
    private Boolean active;

    @NotNull(message = "Assigned ticket count is required")
    @Column(nullable = false)
    private Integer assignedTicketCount;

    @NotNull(message = "Created date is required")
    @Column(nullable = false)
    private Timestamp createdAt;
}