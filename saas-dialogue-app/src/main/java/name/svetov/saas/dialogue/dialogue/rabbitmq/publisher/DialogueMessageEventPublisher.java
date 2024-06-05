package name.svetov.saas.dialogue.dialogue.rabbitmq.publisher;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.Mandatory;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import name.svetov.saas.dialogue.dialogue.rabbitmq.event.DialogueMessageCreatedEvent;
import org.reactivestreams.Publisher;

import static name.svetov.saas.dialogue.rabbitmq.ChannelConstants.DIALOGUE_MESSAGE_CREATED_QUEUE;
import static name.svetov.saas.dialogue.rabbitmq.ChannelConstants.DIALOGUE_MESSAGE_EXCHANGE;


@RabbitClient(DIALOGUE_MESSAGE_EXCHANGE)
public interface DialogueMessageEventPublisher {
    @Binding(DIALOGUE_MESSAGE_CREATED_QUEUE)
    @Mandatory
    Publisher<Void> publish(DialogueMessageCreatedEvent event);
}
