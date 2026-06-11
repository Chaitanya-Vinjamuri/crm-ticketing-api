package org.crm.crmticketingapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.crm.crmticketingapi.enums.HistoryAction;

import java.sql.Timestamp;

@Entity
@Table(
        name = "ticket_history",
        indexes = {
                @Index(
                        name = "idx_history_object_type",
                        columnList = "objectType"
                ),
                @Index(
                        name = "idx_history_object_id",
                        columnList = "objectId"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketHistory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false,
            length = 50
    )
    private String objectType;

    @Column(
            nullable = false
    )
    private Long objectId;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            nullable = false
    )
    private HistoryAction action;

    @Column(
            nullable = false
    )
    private Timestamp createdAt;
}