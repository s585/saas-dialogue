package name.svetov.saas.dialogue.dialogue.converter;

import jakarta.inject.Singleton;
import name.svetov.saas.dialogue.dialogue.model.Dialogue;
import name.svetov.saas.dialogue.dialogue.model.DialogueMessage;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Singleton
public class TarantoolRecordDialogueConverter {
    public Dialogue convertToDialogue(List<Object> source) {
        return Dialogue.builder()
            .id(convertToUUID(source.get(1)))
            .participants(convertToUUIDSet(source.get(2)))
            .createdDate(OffsetDateTime.parse(String.valueOf(source.get(3))))
            .updatedDate(OffsetDateTime.parse(String.valueOf(source.get(3))))
            .build();
    }

    public DialogueMessage convertToDialogueMessage(List<Object> source) {
        return DialogueMessage.builder()
            .id(convertToUUID(source.get(1)))
            .dialogueId(convertToUUID(source.get(2)))
            .authorId(convertToUUID(source.get(3)))
            .recipientId(convertToUUID(source.get(4)))
            .payload(String.valueOf(source.get(5)))
            .createdDate(OffsetDateTime.parse(String.valueOf(source.get(6))))
            .updatedDate(OffsetDateTime.parse(String.valueOf(source.get(7))))
            .build();
    }

    private static UUID convertToUUID(Object source) {
        if (source instanceof UUID) {
            return (UUID) source;
        } else {
            return UUID.fromString(source.toString());
        }
    }

    private static Set<UUID> convertToUUIDSet(Object source) {
        if (source instanceof Collection<?>) {
            return ((Collection<?>) source).stream()
                .map(TarantoolRecordDialogueConverter::convertToUUID)
                .collect(Collectors.toSet());
        } else {
            return Set.of(convertToUUID(source));
        }
    }
}
