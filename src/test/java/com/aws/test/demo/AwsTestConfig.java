package com.aws.test.demo;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;

import static com.aws.test.demo.DemoApplicationIT.localStack;
import static com.aws.test.demo.SQSListener.QUEUE_NAME;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@TestConfiguration
public class AwsTestConfig {

    @Bean
    public AmazonSQSAsync amazonSQS() {
        AmazonSQSAsync sqs = AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(localStack.getDefaultCredentialsProvider())
                .withEndpointConfiguration(localStack.getEndpointConfiguration(SQS))
                .build();
        sqs.createQueue(QUEUE_NAME);
        return sqs;
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(
            AmazonSQSAsync amazonSQSAsync) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setWaitTimeOut(1);
        return factory;
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(@Autowired AmazonSQSAsync amazonSQS) {
        return new QueueMessagingTemplate(amazonSQS);
    }
}