package pl.publicprojects.pcommon.protocol.helper;

import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Queue;

@Getter
public class ChatQueue {

    private final Queue<String> chatQueue;

    public ChatQueue() {
        this.chatQueue = new ArrayDeque<>();
    }

    public void add(String message) {
        chatQueue.add(message);
        if(chatQueue.size() > 20) {
            chatQueue.poll();
        }
    }
}
