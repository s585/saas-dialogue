package name.svetov.saas.dialogue.dialogue.repository;

import name.svetov.saas.dialogue.dialogue.model.Dialogue;
import org.reactivestreams.Publisher;

import java.util.UUID;

public interface DialogueRepository {
    Publisher<Dialogue> getOneById(UUID dialogueId);
    Publisher<Dialogue> add(Dialogue dialogue);
}
