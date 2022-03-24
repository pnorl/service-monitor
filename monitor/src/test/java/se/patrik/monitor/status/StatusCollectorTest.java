package se.patrik.monitor.status;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class StatusCollectorTest {
    private final StatusCollector statusCollector = new StatusCollector();
    public static MockWebServer externalApi;

    @BeforeAll
    static void setUp() throws IOException {
        externalApi = new MockWebServer();
        externalApi.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        externalApi.shutdown();
    }

    @Test
    public void test200responseReturnsStatusOK() throws InterruptedException {
        externalApi.enqueue(new MockResponse().setResponseCode(200));
        String url = externalApi.url("/running-service").url().toString();
        Mono<Status> status =  statusCollector.getStatus(url);
        StepVerifier.create(status)
                .expectNextMatches(s -> s == Status.OK)
                .verifyComplete();
        RecordedRequest request = externalApi.takeRequest();
        assertThat(request.getPath()).isEqualTo("/running-service");
    }

    @Test
    public void testNon200responseReturnsStatusFAIL() throws InterruptedException{
        externalApi.enqueue(new MockResponse().setResponseCode(500));
        String url = externalApi.url("/failing-service").url().toString();
        Mono<Status> status =  statusCollector.getStatus(url);
        StepVerifier.create(status)
                .expectNextMatches(s -> s == Status.FAIL)
                .verifyComplete();
        RecordedRequest request = externalApi.takeRequest();
        assertThat(request.getPath()).isEqualTo("/failing-service");
    }
}
