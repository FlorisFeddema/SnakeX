package snakex.model.manager;

import java.util.ArrayList;
import java.util.List;

public class Chat implements IsChat {
    private List<Message> messages;

    public Chat(){
        messages = new ArrayList<>();
    }

    @Override
    public Message getLastMessage() {
        return messages.get(messages.size()-1);
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
    }
}
