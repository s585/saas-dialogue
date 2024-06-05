package name.svetov.saas.dialogue.dialogue.repository;

import io.micronaut.context.annotation.Requires;
import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.DefaultTarantoolTupleFactory;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.api.tuple.TarantoolTupleFactory;
import io.tarantool.driver.mappers.DefaultMessagePackMapper;
import io.tarantool.driver.mappers.factories.DefaultMessagePackMapperFactory;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.dialogue.converter.TarantoolRecordDialogueConverter;
import name.svetov.saas.dialogue.dialogue.model.Dialogue;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Requires(property = "dialogue-app.dialogue.impl", value = "tarantool")
@Singleton
@RequiredArgsConstructor
public class TarantoolDialogueRepository implements DialogueRepository {
    private final TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient;
    private final TarantoolRecordDialogueConverter converter;

    @Override
    public Publisher<Dialogue> getOneById(UUID dialogueId) {
        return Mono.fromFuture(
                tarantoolClient.call("get_dialogue_by_id", dialogueId)
            )
            .flatMapIterable(
                items -> items.stream()
                    .filter(Objects::nonNull)
                    .flatMap(item -> ((List<Object>) item).stream())
                    .map(source -> converter.convertToDialogue((List<Object>) source))
                    .toList()
            );
    }

    @Override
    public Publisher<Dialogue> add(Dialogue dialogue) {
        var tupleFactory = createTupleFactory(DefaultMessagePackMapperFactory.getInstance().defaultSimpleTypeMapper());
        return Mono.fromFuture(tarantoolClient.call("add_dialogue", tupleFactory.create(dialogue.getParticipants())))
            .flatMapIterable(
                items -> items.stream()
                    .filter(Objects::nonNull)
                    .map(item -> converter.convertToDialogue((List<Object>) item))
                    .toList()
            );
    }

    private TarantoolTupleFactory createTupleFactory(DefaultMessagePackMapper mapper) {
        return new DefaultTarantoolTupleFactory(mapper);
    }
}
