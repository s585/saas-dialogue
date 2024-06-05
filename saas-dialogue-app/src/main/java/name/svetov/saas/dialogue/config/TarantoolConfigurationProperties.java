package name.svetov.saas.dialogue.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("tarantool")
@Getter
@Setter
public class TarantoolConfigurationProperties {
    private String nodes;
    private String username;
    private String password;
}
