package name.svetov.saas.dialogue.dialogue.repository;

import org.reactivestreams.Publisher;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface DialogueMessageStateRepository {
    Publisher<Boolean> add(UUID dialogueId, UUID messageId, UUID recipientId);
    Publisher<Boolean> markRead(UUID dialogueId, UUID messageId, UUID recipientId, boolean read);
}
