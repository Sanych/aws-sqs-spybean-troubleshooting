package com.aws.test.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@SpringBootTest
@Testcontainers
@Import(AwsTestConfig.class)
@AutoConfigureWireMock(port = 18080)
public class DemoApplicationTest {

    protected static String QUEUE_NAME = "LocalStack-test-queue";

    @Container
    public static LocalStackContainer localStack = new LocalStackContainer()
            .withServices(SQS)
            .withEnv("DEFAULT_REGION", "us-west-1");

    @BeforeAll
    static void beforeAll() throws IOException, InterruptedException {
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", QUEUE_NAME);
    }

    @Test
    void contextLoads() {
    }

}