package kr.hhplus.be.server.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private String receivedMessage;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(String message){
        this.receivedMessage = message;
        latch.countDown();
    }

    public String getReceivedMessage() throws InterruptedException{
        latch.await();
        return receivedMessage;
    }

}
