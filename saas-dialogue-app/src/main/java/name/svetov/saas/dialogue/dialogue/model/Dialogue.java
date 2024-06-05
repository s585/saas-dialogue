package name.svetov.saas.dialogue.dialogue.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Serdeable
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Dialogue {
    private UUID id;
    @Singular
    private Set<UUID> participants;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
}
