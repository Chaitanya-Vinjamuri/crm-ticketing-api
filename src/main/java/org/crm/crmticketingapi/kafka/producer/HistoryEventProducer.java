package org.crm.crmticketingapi.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.crm.crmticketingapi.dto.event.HistoryEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryEventProducer {

    private final KafkaTemplate<String, HistoryEvent> kafkaTemplate;

    public void publish(
            HistoryEvent historyEvent) {

        kafkaTemplate.send(
                "history-events",
                historyEvent
        );

        System.out.println(
                "Published Event -> "
                        + historyEvent
        );
    }
}