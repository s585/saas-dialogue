package name.svetov.saas.dialogue.dialogue.converter;

import name.svetov.saas.dialogue.config.SaasMapperDefaultConfig;
import name.svetov.saas.dialogue.dialogue.model.Dialogue;
import name.svetov.saas.dialogue.dialogue.model.DialogueMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Mapper(config = SaasMapperDefaultConfig.class)
public interface DialogueGrpcConverter {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participant", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Dialogue map(name.svetov.saas.dialogue.grpc.Dialogue.DialogueCreateRq source);

    name.svetov.saas.dialogue.grpc.Dialogue.DialogueRs map(Dialogue source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "read", ignore = true)
    DialogueMessage map(name.svetov.saas.dialogue.grpc.DialogueMessage.DialogueMessageCreateRq source);

    name.svetov.saas.dialogue.grpc.DialogueMessage.DialogueMessageRs map(DialogueMessage source);

    default String map(OffsetDateTime value) {
        return value.toString();
    }

    default List<String> map(Set<UUID> value) {
        return value.stream()
            .filter(Objects::nonNull)
            .map(Object::toString)
            .toList();
    }
}
