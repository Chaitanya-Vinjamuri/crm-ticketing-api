package org.crm.crmticketingapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.crm.crmticketingapi.enums.IssueType;
import org.crm.crmticketingapi.enums.Priority;
import org.crm.crmticketingapi.enums.TicketStatus;

import java.sql.Timestamp;

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

    @Column(
            nullable = false,
            unique = true,
            length = 20
    )
    private String ticketCode;

    @NotBlank(message = "Title is required")
    @Size(
            min = 5,
            max = 200,
            message = "Title must be between 5 and 200 characters"
    )
    @Column(
            nullable = false,
            length = 200
    )
    private String title;

    @NotBlank(message = "Description is required")
    @Size(
            min = 10,
            max = 1000,
            message = "Description must be between 10 and 1000 characters"
    )
    @Column(
            nullable = false,
            length = 1000
    )
    private String description;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid customer email format")
    @Size(
            min = 5,
            max = 150,
            message = "Customer email must be between 5 and 150 characters"
    )
    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false)
    private Timestamp slaDueAt;

    @NotNull(message = "Issue type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueType issueType;

    @Column(nullable = false)
    private Timestamp createdAt;

    private Timestamp resolvedAt;

    @NotNull(message = "Assigned agent is required")
    @ManyToOne
    @JoinColumn(
            name = "agent_id",
            nullable = false
    )
    @JsonIgnore
    private Agent assignedAgent;

    @JsonProperty("agentId")
    public Long getAgentId() {

        return assignedAgent != null
                ? assignedAgent.getId()
                : null;
    }
}