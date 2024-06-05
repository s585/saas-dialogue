package name.svetov.saas.dialogue.dialogue.rabbitmq.listener;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.dialogue.rabbitmq.event.DialogueMessageReadEvent;
import name.svetov.saas.dialogue.dialogue.service.DialogueService;
import reactor.core.publisher.Mono;

import static name.svetov.saas.dialogue.rabbitmq.ChannelConstants.DIALOGUE_MESSAGE_CREATED_QUEUE;

@RabbitListener
@RequiredArgsConstructor
public class DialogueMessageEventListenerImpl implements DialogueMessageEventListener {
    private final DialogueService dialogueService;

    @Override
    @Queue(DIALOGUE_MESSAGE_CREATED_QUEUE)
    public void process(DialogueMessageReadEvent event) {
        Mono.from(
            dialogueService.markMessageRead(
                event.getDialogueId(),
                event.getMessageId(),
                event.getRecipientId(),
                event.isRead()
            )
        ).subscribe();
    }
}
