package org.crm.crmticketingapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.crm.crmticketingapi.enums.Department;

import java.time.LocalDateTime;

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
            length = 100
    )
    private String name;

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Integer assignedTicketCount;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}