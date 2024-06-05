package name.svetov.saas.dialogue.dialogue.rabbitmq.event;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Serdeable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DialogueMessageReadEvent {
    private UUID dialogueId;
    private UUID messageId;
    private UUID recipientId;
    private boolean read;
}
