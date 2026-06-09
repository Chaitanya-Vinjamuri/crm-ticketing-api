package org.crm.crmticketingapi.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment message is required")
    @Size(
            min = 2,
            max = 1000,
            message = "Comment must be between 2 and 1000 characters"
    )
    @Column(nullable = false, length = 1000)
    private String message;

    @NotNull(message = "Comment creation date is required")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Ticket reference is required")
    @ManyToOne
    @JoinColumn(
            name = "ticket_id",
            nullable = false
    )
    private Ticket ticket;

    @NotNull(message = "Agent reference is required")
    @ManyToOne
    @JoinColumn(
            name = "agent_id",
            nullable = false
    )
    private Agent agent;
}