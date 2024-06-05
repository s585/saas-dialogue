package name.svetov.saas.dialogue.dialogue.repository;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.jooq.generated.Tables.DIALOGUE_MESSAGE_STATE;

@Requires(property = "dialogue-app.dialogue.impl", value = "postgres")
@Singleton
public class PostgresDialogueMessageStateRepository implements DialogueMessageStateRepository {
    private final DSLContext context;

    public PostgresDialogueMessageStateRepository(@Named("r2dbc") DSLContext context) {
        this.context = context;
    }

    @Override
    public Publisher<Boolean> add(UUID dialogueId, UUID messageId, UUID recipientId) {
        return Mono.from(
            context.insertInto(DIALOGUE_MESSAGE_STATE)
                .set(DIALOGUE_MESSAGE_STATE.DIALOGUE_ID, dialogueId)
                .set(DIALOGUE_MESSAGE_STATE.MESSAGE_ID, messageId)
                .set(DIALOGUE_MESSAGE_STATE.RECIPIENT_ID, recipientId)
                .set(DIALOGUE_MESSAGE_STATE.READ, false)
        ).map(response -> response == 1);
    }

    @Override
    public Publisher<Boolean> markRead(UUID dialogueId, UUID messageId, UUID recipientId, boolean read) {
        return Mono.from(
            context.update(DIALOGUE_MESSAGE_STATE)
                .set(DIALOGUE_MESSAGE_STATE.READ, read)
                .where(
                    DIALOGUE_MESSAGE_STATE.DIALOGUE_ID.eq(dialogueId),
                    DIALOGUE_MESSAGE_STATE.MESSAGE_ID.eq(messageId),
                    DIALOGUE_MESSAGE_STATE.RECIPIENT_ID.eq(recipientId)
                )
        ).map(response -> response == 1);
    }
}
