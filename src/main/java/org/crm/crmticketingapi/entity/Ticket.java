package org.crm.crmticketingapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.crm.crmticketingapi.enums.IssueType;
import org.crm.crmticketingapi.enums.Priority;
import org.crm.crmticketingapi.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tickets",
        indexes = {
                @Index(
                        name = "idx_ticket_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_ticket_priority",
                        columnList = "priority"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(
            min = 5,
            max = 200,
            message = "Title must be between 5 and 200 characters"
    )
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(
            min = 10,
            max = 1000,
            message = "Description must be between 10 and 1000 characters"
    )
    @Column(nullable = false, length = 1000)
    private String description;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid customer email format")
    @Column(nullable = false)
    private String customerEmail;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;


    @NotNull(message = "Issue type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueType issueType;

    @NotNull(message = "Created date is required")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;

    @NotNull(message = "Assigned agent is required")
    @ManyToOne
    @JoinColumn(
            name = "agent_id",
            nullable = false
    )
    private Agent assignedAgent;
}