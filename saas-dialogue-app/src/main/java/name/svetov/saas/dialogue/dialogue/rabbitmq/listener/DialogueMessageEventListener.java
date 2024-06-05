package name.svetov.saas.dialogue.dialogue.rabbitmq.listener;

import name.svetov.saas.dialogue.dialogue.rabbitmq.event.DialogueMessageReadEvent;

public interface DialogueMessageEventListener {
    void process(DialogueMessageReadEvent event);
}
