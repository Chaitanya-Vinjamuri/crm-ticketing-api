package org.crm.crmticketingapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.crm.crmticketingapi.enums.IssueType;
import org.crm.crmticketingapi.enums.Priority;
import org.crm.crmticketingapi.enums.TicketStatus;

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

    @Column(
            nullable = false,
            length = 200
    )
    private String title;

    @Column(
            nullable = false,
            length = 1000
    )
    private String description;

    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueType issueType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;

    @ManyToOne
    @JoinColumn(
            name = "agent_id",
            nullable = false
    )
    private Agent assignedAgent;
}