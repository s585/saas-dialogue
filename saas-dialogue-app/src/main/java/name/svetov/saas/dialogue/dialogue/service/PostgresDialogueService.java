package name.svetov.saas.dialogue.dialogue.service;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.dialogue.model.Dialogue;
import name.svetov.saas.dialogue.dialogue.model.DialogueMessage;
import name.svetov.saas.dialogue.dialogue.rabbitmq.event.DialogueMessageCreatedEvent;
import name.svetov.saas.dialogue.dialogue.rabbitmq.publisher.DialogueMessageEventPublisher;
import name.svetov.saas.dialogue.dialogue.repository.DialogueMessageRepository;
import name.svetov.saas.dialogue.dialogue.repository.DialogueMessageStateRepository;
import name.svetov.saas.dialogue.dialogue.repository.DialogueRepository;
import org.reactivestreams.Publisher;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Requires(property = "dialogue-app.dialogue.impl", value = "postgres")
@Singleton
@RequiredArgsConstructor
public class PostgresDialogueService implements DialogueService {
    private final DialogueRepository dialogueRepository;
    private final DialogueMessageRepository dialogueMessageRepository;
    private final DialogueMessageStateRepository dialogueMessageStateRepository;
    private final DialogueMessageEventPublisher eventPublisher;

    @Override
    public Publisher<Dialogue> findDialogue(UUID dialogueId) {
        return dialogueRepository.getOneById(dialogueId);
    }

    @Override
    public Publisher<Dialogue> createDialogue(Dialogue dialogue) {
        return dialogueRepository.add(dialogue);
    }

    @Transactional
    @Override
    public Publisher<DialogueMessage> createMessage(DialogueMessage message) {
        return Mono.from(dialogueMessageRepository.add(message))
            .onErrorComplete()
            .flatMap(persistedMessage ->
                Mono.from(
                        dialogueMessageStateRepository.add(
                            persistedMessage.getDialogueId(),
                            persistedMessage.getId(),
                            persistedMessage.getRecipientId()
                        )
                    ).onErrorComplete()
                    .flatMap(result ->
                        Mono.from(
                            eventPublisher.publish(
                                DialogueMessageCreatedEvent.builder()
                                    .dialogueId(persistedMessage.getDialogueId())
                                    .recipientId(persistedMessage.getRecipientId())
                                    .build()
                            )
                        )
                    ).then(Mono.just(persistedMessage))
            );
    }

    @Override
    public Publisher<DialogueMessage> findAllMessagesByDialogueId(UUID dialogueId) {
        return dialogueMessageRepository.findAllUnreadByDialogueId(dialogueId);
    }

    @Override
    public Publisher<Boolean> markMessageRead(UUID dialogueId,
                                              UUID messageId,
                                              UUID recipientId,
                                              boolean read) {
        return dialogueMessageStateRepository.markRead(dialogueId, messageId, recipientId, read);
    }
}
