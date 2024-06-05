package name.svetov.saas.dialogue.dialogue.service;

import name.svetov.saas.dialogue.dialogue.model.Dialogue;
import name.svetov.saas.dialogue.dialogue.model.DialogueMessage;
import org.reactivestreams.Publisher;

import java.util.UUID;

public interface DialogueService {
    Publisher<Dialogue> findDialogue(UUID dialogueId);
    Publisher<Dialogue> createDialogue(Dialogue dialogue);
    Publisher<DialogueMessage> findAllMessagesByDialogueId(UUID dialogueId);
    Publisher<DialogueMessage> createMessage(DialogueMessage message);
    Publisher<Boolean> markMessageRead(UUID dialogueId, UUID messageId, UUID recipientId, boolean read);
}
