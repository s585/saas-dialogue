package name.svetov.saas.dialogue.dialogue.grpc;

import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.dialogue.adapter.DialogueGrpcAdapter;
import name.svetov.saas.dialogue.grpc.DialogueMessage;
import name.svetov.saas.dialogue.grpc.DialogueMessageServiceGrpc;
import reactor.core.publisher.Mono;

@Singleton
@RequiredArgsConstructor
public class DialogueMessageGrpcEndpoint extends DialogueMessageServiceGrpc.DialogueMessageServiceImplBase {
    private final DialogueGrpcAdapter webAdapter;

    @Override
    public void findMessages(DialogueMessage.DialogueMessageSearchRq request, StreamObserver<DialogueMessage.DialogueMessageSearchRs> responseObserver) {
        Mono.from(webAdapter.findAllMessagesByDialogueId(request))
            .doOnNext(responseObserver::onNext)
            .doOnNext(empty -> responseObserver.onCompleted())
            .block();
    }

    @Override
    public void create(DialogueMessage.DialogueMessageCreateRq request, StreamObserver<DialogueMessage.DialogueMessageRs> responseObserver) {
        Mono.from(webAdapter.createMessage(request))
            .doOnNext(responseObserver::onNext)
            .doOnNext(empty -> responseObserver.onCompleted())
            .block();
    }
}
