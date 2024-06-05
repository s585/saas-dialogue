package name.svetov.saas.dialogue.dialogue.service;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.dialogue.model.Dialogue;
import name.svetov.saas.dialogue.dialogue.model.DialogueMessage;
import name.svetov.saas.dialogue.dialogue.repository.TarantoolDialogueMessageRepository;
import name.svetov.saas.dialogue.dialogue.repository.TarantoolDialogueRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Requires(property = "dialogue-app.dialogue.impl", value = "tarantool")
@Singleton
@RequiredArgsConstructor
public class TarantoolDialogueService implements DialogueService {
private final TarantoolDialogueRepository dialogueRepository;
private final TarantoolDialogueMessageRepository dialogueMessageRepository;

    @Override
    public Publisher<Dialogue> findDialogue(UUID dialogueId) {
        return dialogueRepository.getOneById(dialogueId);
    }

    @Override
    public Publisher<Dialogue> createDialogue(Dialogue dialogue) {
        return dialogueRepository.add(dialogue);
    }

    @Override
    public Publisher<DialogueMessage> findAllMessagesByDialogueId(UUID dialogueId) {
        return dialogueMessageRepository.findAllUnreadByDialogueId(dialogueId);
    }

    @Override
    public Publisher<DialogueMessage> createMessage(DialogueMessage message) {
        return dialogueMessageRepository.add(message);
    }

    @Override
    public Publisher<Boolean> markMessageRead(UUID dialogueId,
                                                      UUID messageId,
                                                      UUID recipientId,
                                                      boolean read) {
        return Mono.just(true);
    }
}
