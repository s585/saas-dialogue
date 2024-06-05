package name.svetov.saas.dialogue.dialogue.repository;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import name.svetov.saas.dialogue.dialogue.model.DialogueMessage;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.generated.tables.records.DialogueMessageRecord;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.jooq.generated.Tables.DIALOGUE_MESSAGE;
import static org.jooq.generated.Tables.DIALOGUE_MESSAGE_STATE;

@Requires(property = "dialogue-app.dialogue.impl", value = "postgres")
@Singleton
public class PostgresDialogueMessageRepository implements DialogueMessageRepository {
    private final DSLContext context;

    public PostgresDialogueMessageRepository(@Named("r2dbc") DSLContext context) {
        this.context = context;
    }

    @Override
    public Publisher<DialogueMessage> findAllUnreadByDialogueId(UUID dialogueId) {
        return Flux.from(
            context.select()
                .from(DIALOGUE_MESSAGE)
                .join(DIALOGUE_MESSAGE_STATE)
                .on(DIALOGUE_MESSAGE_STATE.MESSAGE_ID.eq(DIALOGUE_MESSAGE.ID))
                .where(
                    DIALOGUE_MESSAGE.DIALOGUE_ID.eq(dialogueId),
                    DIALOGUE_MESSAGE_STATE.READ.isFalse()
                )
        ).map(response -> response.into(DialogueMessage.class));
    }

    @Override
    public Publisher<DialogueMessage> add(DialogueMessage message) {
        return Mono.from(insert(message).returning())
            .map(response -> response.into(DialogueMessage.class));
    }

    private InsertSetMoreStep<DialogueMessageRecord> insert(DialogueMessage message) {
        var now = OffsetDateTime.now();
        return context.insertInto(DIALOGUE_MESSAGE)
            .set(DIALOGUE_MESSAGE.DIALOGUE_ID, message.getDialogueId())
            .set(DIALOGUE_MESSAGE.AUTHOR_ID, message.getAuthorId())
            .set(DIALOGUE_MESSAGE.RECIPIENT_ID, message.getRecipientId())
            .set(DIALOGUE_MESSAGE.PAYLOAD, message.getPayload())
            .set(DIALOGUE_MESSAGE.CREATED_DATE, now)
            .set(DIALOGUE_MESSAGE.UPDATED_DATE, now);
    }
}
