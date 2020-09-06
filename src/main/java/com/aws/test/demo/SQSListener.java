package com.aws.test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SQSListener {

    public static final String QUEUE_NAME = "test-queue";

    private final Logger LOG = LoggerFactory.getLogger(SQSListener.class);

    @SqsListener(value = QUEUE_NAME, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void listen(String message) {
        LOG.warn("id: {}, received: '{}'", this.getClass().toString(), message);
    }

}
