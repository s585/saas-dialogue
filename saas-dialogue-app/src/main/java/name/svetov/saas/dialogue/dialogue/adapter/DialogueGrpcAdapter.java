package name.svetov.saas.dialogue.dialogue.adapter;

import name.svetov.saas.dialogue.grpc.Dialogue;
import name.svetov.saas.dialogue.grpc.DialogueMessage;
import org.reactivestreams.Publisher;

public interface DialogueGrpcAdapter {
    Publisher<Dialogue.DialogueRs> getDialogue(Dialogue.DialogueSearchRq rq);
    Publisher<Dialogue.DialogueRs> createDialogue(Dialogue.DialogueCreateRq rq);
    Publisher<DialogueMessage.DialogueMessageRs> createMessage(DialogueMessage.DialogueMessageCreateRq rq);
    Publisher<DialogueMessage.DialogueMessageSearchRs> findAllMessagesByDialogueId(DialogueMessage.DialogueMessageSearchRq rq);
}
