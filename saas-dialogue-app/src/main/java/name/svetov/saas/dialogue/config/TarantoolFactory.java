package name.svetov.saas.dialogue.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.tarantool.driver.api.*;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.auth.SimpleTarantoolCredentials;
import lombok.RequiredArgsConstructor;
import name.svetov.saas.dialogue.constants.TextConstants;

import java.util.Arrays;
import java.util.List;

@Factory
@RequiredArgsConstructor
public class TarantoolFactory {
    private final TarantoolConfigurationProperties properties;

    @Bean
    public TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient() {
        return TarantoolClientFactory.createClient()
            .withTarantoolClientConfig(clientConfig())
            .withAddresses(tarantoolClusterAddresses())
            .withRetryingByNumberOfAttempts(10)
            .build();
    }

    private TarantoolClientConfig clientConfig() {
        return new TarantoolClientConfig.Builder()
            .withCredentials(new SimpleTarantoolCredentials(properties.getUsername(), properties.getPassword()))
            .withRequestTimeout(1000 * 30)
            .build();
    }

    private List<TarantoolServerAddress> tarantoolClusterAddresses() {
        return Arrays.stream(properties.getNodes().split(TextConstants.COMMA))
            .map(node -> node.split(TextConstants.COLON))
            .map(address -> new TarantoolServerAddress(address[0], Integer.parseInt(address[1])))
            .toList();
    }
}
