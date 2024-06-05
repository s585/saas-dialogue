package name.svetov.saas.dialogue.dialogue.grpc;

import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.dialogue.adapter.DialogueGrpcAdapter;
import name.svetov.saas.dialogue.grpc.Dialogue;
import name.svetov.saas.dialogue.grpc.DialogueServiceGrpc;
import reactor.core.publisher.Mono;

@Singleton
@RequiredArgsConstructor
public class DialogueGrpcEndpoint extends DialogueServiceGrpc.DialogueServiceImplBase {
    private final DialogueGrpcAdapter grpcAdapter;

    @Override
    public void get(Dialogue.DialogueSearchRq request, StreamObserver<Dialogue.DialogueRs> responseObserver) {
        Mono.from(grpcAdapter.getDialogue(request))
            .doOnNext(responseObserver::onNext)
            .doOnNext(empty -> responseObserver.onCompleted())
            .block();
    }

    @Override
    public void create(Dialogue.DialogueCreateRq request, StreamObserver<Dialogue.DialogueRs> responseObserver) {
        Mono.from(grpcAdapter.createDialogue(request))
            .doOnNext(responseObserver::onNext)
            .doOnNext(empty -> responseObserver.onCompleted())
            .block();
    }
}
