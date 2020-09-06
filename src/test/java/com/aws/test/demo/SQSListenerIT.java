package com.aws.test.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

import static com.aws.test.demo.SQSListener.QUEUE_NAME;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

class SQSListenerIT extends AbstractIntegrationTest {

    Logger LOG = LoggerFactory.getLogger(SQSListenerIT.class);

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @SpyBean
    public SQSListener sqsMessageListener;

    @AfterEach
    public void resetMocks() {
        Mockito.reset(sqsMessageListener);
    }

    @Test
    public void test1() {
        LOG.warn("calling {}", sqsMessageListener.getClass().toString());
        sendMessageFromFileAndWaitForListenerToRead("test 1");
    }

    @Test
    public void test2() {
        LOG.warn("calling {}", sqsMessageListener.getClass().toString());
        sendMessageFromFileAndWaitForListenerToRead("test 2");
    }

    @Test
    public void test3() {
        LOG.warn("calling {}", sqsMessageListener.getClass().toString());
        sendMessageFromFileAndWaitForListenerToRead("test 3");
    }

    private void sendMessageFromFileAndWaitForListenerToRead(String message) {
        LOG.warn("sendMessage to queue: {}", message);
        queueMessagingTemplate.convertAndSend(QUEUE_NAME, message);

        given().await()
                .atMost(5, SECONDS)
                .untilAsserted(() -> verify(sqsMessageListener).listen(anyString()));
    }

}