package name.svetov.saas.dialogue.dialogue.adapter;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.dialogue.converter.DialogueGrpcConverter;
import name.svetov.saas.dialogue.dialogue.service.DialogueService;
import name.svetov.saas.dialogue.grpc.Dialogue;
import name.svetov.saas.dialogue.grpc.DialogueMessage;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Singleton
@RequiredArgsConstructor
public class DialogueGrpcAdapterImpl implements DialogueGrpcAdapter {
    private final DialogueService dialogueService;
    private final DialogueGrpcConverter converter;

    @Override
    public Publisher<Dialogue.DialogueRs> getDialogue(Dialogue.DialogueSearchRq rq) {
        return Mono.from(dialogueService.findDialogue(UUID.fromString(rq.getId())))
            .map(converter::map);
    }

    @Override
    public Publisher<Dialogue.DialogueRs> createDialogue(Dialogue.DialogueCreateRq rq) {
        return Mono.from(dialogueService.createDialogue(converter.map(rq)))
            .map(converter::map);
    }

    @Override
    public Publisher<DialogueMessage.DialogueMessageRs> createMessage(DialogueMessage.DialogueMessageCreateRq rq) {
        return Mono.from(dialogueService.createMessage(converter.map(rq)))
            .map(converter::map);
    }

    @Override
    public Publisher<DialogueMessage.DialogueMessageSearchRs> findAllMessagesByDialogueId(DialogueMessage.DialogueMessageSearchRq rq) {
        return Flux.from(dialogueService.findAllMessagesByDialogueId(UUID.fromString(rq.getDialogueId())))
            .collectList()
            .map(response -> {
                    var builder = DialogueMessage.DialogueMessageSearchRs.newBuilder();
                    response.stream()
                        .map(converter::map)
                        .forEach(builder::addMessages);
                    return builder.build();
                }
            );
    }
}
