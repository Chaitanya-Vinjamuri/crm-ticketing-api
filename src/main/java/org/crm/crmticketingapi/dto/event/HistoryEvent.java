package org.crm.crmticketingapi.dto.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HistoryEvent {

    private String objectType;

    private Long objectId;

    private String action;
}