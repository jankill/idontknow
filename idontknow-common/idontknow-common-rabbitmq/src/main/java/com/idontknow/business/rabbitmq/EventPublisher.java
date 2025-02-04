package com.idontknow.business.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    @Qualifier(RabbitConfig.RABBIT_EVENT_PUBLISHER)
    private final AmqpTemplate amqpTemplate;

    public void publish(
            @NonNull final String exchange,
            @NonNull final String routingKey,
            @NonNull final String payload) {

        try {

            final MessageProperties props =
                    MessagePropertiesBuilder.newInstance()
                            .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                            .setContentEncoding(StandardCharsets.UTF_8.toString())
                            .build();

            log.info(
                    "[RABBITMQ][PUB][{}] headers {} payload {} ", routingKey, props.getHeaders(), payload);

            final Message message = MessageBuilder.withBody(payload.getBytes()).andProperties(props).build();
            this.amqpTemplate.send(exchange, routingKey, message);

        } catch (final Exception ex) {
            log.error(
                    format(
                            "[RABBITMQ][PUB][%s] error publishing message with payload %s", routingKey, payload),
                    ex);
        }
    }
}
