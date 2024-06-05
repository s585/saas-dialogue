package name.svetov.saas.dialogue.dialogue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DialogueMessage {
    private UUID id;
    private UUID dialogueId;
    private UUID authorId;
    private UUID recipientId;
    private String payload;
    private boolean read;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
}
