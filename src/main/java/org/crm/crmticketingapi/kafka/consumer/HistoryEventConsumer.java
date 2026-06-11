package org.crm.crmticketingapi.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dao.TicketHistoryDao;
import org.crm.crmticketingapi.dto.event.HistoryEvent;
import org.crm.crmticketingapi.entity.TicketHistory;
import org.crm.crmticketingapi.enums.HistoryAction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class HistoryEventConsumer {

    private final TicketHistoryDao ticketHistoryDao;

    @KafkaListener(
            topics = "history-events",
            groupId = "history-group",
            containerFactory = "listenerContainerFactory"
    )
    public void consume(
            HistoryEvent historyEvent) {

        System.out.println(
                "Consumed Event -> "
                        + historyEvent
        );

        TicketHistory ticketHistory =
                TicketHistory.builder()
                        .objectType(
                                historyEvent.getObjectType()
                        )
                        .objectId(
                                historyEvent.getObjectId()
                        )
                        .action(
                                HistoryAction.valueOf(
                                        historyEvent.getAction()
                                )
                        )
                        .createdAt(
                                new Timestamp(
                                        System.currentTimeMillis()
                                )
                        )
                        .build();

        ticketHistoryDao.save(
                ticketHistory
        );
    }
}