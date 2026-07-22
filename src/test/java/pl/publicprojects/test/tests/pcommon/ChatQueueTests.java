package pl.publicprojects.test.tests.pcommon;

import org.junit.jupiter.api.Test;
import pl.publicprojects.pcommon.app.helper.ChatQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatQueueTests {

    public void emulateConcurrent(ChatQueue queue, int threads) throws ExecutionException, InterruptedException {
        List<Future<?>> futureList = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        for(int iterate = 0; iterate < threads; iterate++) {
            int currentIteration = iterate;
            futureList.add(
                    pool.submit(() -> queue.add("Message " + currentIteration))
            );
        }
        for (Future<?> future : futureList) {
            future.get();
        }
        pool.close();
    }

    @Test
    public void exceedMessageLimitTest() throws ExecutionException, InterruptedException {
        //Arrange
        ChatQueue sut = new ChatQueue();
        this.emulateConcurrent(sut, 50);

        //Act
        Queue<String> act = sut.getChatQueue();

        //Assert
        assertEquals(20, act.size());
    }

    @Test
    public void maximumMessagesTest() throws ExecutionException, InterruptedException {
        //Arrange
        ChatQueue sut = new ChatQueue();
        this.emulateConcurrent(sut, 20);

        //Act
        Queue<String> act = sut.getChatQueue();

        //Assert
        assertEquals(20, act.size());
    }

    @Test
    public void fewMessagesTest() throws ExecutionException, InterruptedException {
        //Arrange
        ChatQueue sut = new ChatQueue();
        this.emulateConcurrent(sut, 10);

        //Act
        Queue<String> act = sut.getChatQueue();

        //Assert
        assertEquals(10, act.size());
    }
}
