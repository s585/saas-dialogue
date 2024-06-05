package name.svetov.saas.dialogue.dialogue.repository;

import io.micronaut.context.annotation.Requires;
import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.dialogue.converter.TarantoolRecordDialogueConverter;
import name.svetov.saas.dialogue.dialogue.model.DialogueMessage;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Requires(property = "dialogue-app.dialogue.impl", value = "tarantool")
@Singleton
@RequiredArgsConstructor
public class TarantoolDialogueMessageRepository implements DialogueMessageRepository{
    private final TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient;
    private final TarantoolRecordDialogueConverter converter;

    @Override
    public Publisher<DialogueMessage> findAllUnreadByDialogueId(UUID dialogueId) {
        return Mono.fromFuture(
                tarantoolClient.call("find_all_dialogue_messages", dialogueId)
            )
            .flatMapIterable(
                items -> items.stream()
                    .filter(Objects::nonNull)
                    .flatMap(item -> ((List<Object>) item).stream())
                    .map(source -> converter.convertToDialogueMessage((List<Object>) source))
                    .toList()
            );
    }

    @Override
    public Publisher<DialogueMessage> add(DialogueMessage message) {
        return Mono.fromFuture(
                tarantoolClient.call(
                    "add_dialogue_message",
                    message.getDialogueId(),
                    message.getAuthorId(),
                    message.getRecipientId(),
                    message.getPayload()
                )
            )
            .flatMapIterable(
                items -> items.stream()
                    .filter(Objects::nonNull)
                    .map(item -> converter.convertToDialogueMessage((List<Object>) item))
                    .toList()
            );
    }
}
