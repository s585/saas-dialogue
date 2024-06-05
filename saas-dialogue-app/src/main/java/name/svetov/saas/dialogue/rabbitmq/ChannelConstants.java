package name.svetov.saas.dialogue.rabbitmq;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChannelConstants {
    public static final String DIALOGUE_MESSAGE_EXCHANGE = "dialogue_message.exchange";
    public static final String DIALOGUE_MESSAGE_CREATED_QUEUE = "dialogue_message.created.queue";
}
