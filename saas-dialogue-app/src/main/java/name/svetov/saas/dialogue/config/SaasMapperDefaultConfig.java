package name.svetov.saas.dialogue.config;

import org.mapstruct.*;

import java.util.UUID;

@MapperConfig(
    componentModel = MappingConstants.ComponentModel.JAKARTA,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    imports = UUID.class
)
public interface SaasMapperDefaultConfig {
}
