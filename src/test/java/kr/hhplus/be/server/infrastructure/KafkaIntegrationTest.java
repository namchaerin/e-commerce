package kr.hhplus.be.server.infrastructure;

import kr.hhplus.be.server.infrastructure.kafka.KafkaConsumer;
import kr.hhplus.be.server.infrastructure.kafka.KafkaProducer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KafkaIntegrationTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConsumer kafkaConsumer;

    @DisplayName("카프카 메세지 발행 및 소비 테스트")
    @Test
    void testMessagePublishAndConsumeIntegration() throws InterruptedException {

        //given
        String topic = "test-topic";
        String message = "test-message";

        //when
        kafkaProducer.sendMassage(topic, message);
        String receivedMessage = kafkaConsumer.getReceivedMessage();

        //then
        Assertions.assertThat(receivedMessage).isEqualTo(message);

    }


}
