package name.svetov.saas.dialogue.dialogue.repository;

import name.svetov.saas.dialogue.dialogue.model.DialogueMessage;
import org.reactivestreams.Publisher;

import java.util.UUID;

public interface DialogueMessageRepository {
    Publisher<DialogueMessage> findAllUnreadByDialogueId(UUID dialogueId);
    Publisher<DialogueMessage> add(DialogueMessage message);
}
