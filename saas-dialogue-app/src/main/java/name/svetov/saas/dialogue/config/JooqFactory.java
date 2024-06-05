package name.svetov.saas.dialogue.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;

@Factory
@RequiredArgsConstructor
public class JooqFactory {
    private final ConnectionFactory connectionFactory;

    @Bean
    public DefaultConfiguration configuration() {
        var jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.settings().setRenderSchema(false);
        jooqConfiguration.settings().setRenderQuotedNames(RenderQuotedNames.NEVER);
        jooqConfiguration.settings().setExecuteWithOptimisticLocking(true);
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES);
        jooqConfiguration.set(connectionFactory);
        return jooqConfiguration;
    }

    @Bean
    @Named("r2dbc")
    public DSLContext dslContext() {
        return new DefaultDSLContext(configuration());
    }
}
