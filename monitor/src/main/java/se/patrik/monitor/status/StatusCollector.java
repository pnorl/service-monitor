package se.patrik.monitor.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
public class StatusCollector {
    private final WebClient client;

    public StatusCollector() {
        this.client = WebClient.builder().clientConnector(new ReactorClientHttpConnector(
                HttpClient.create().followRedirect(true)
        )).build();
    }

    public Mono<Status> getStatus(String url) {
        return client
                .get()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .map(r -> toStatus(r.getStatusCode()))
                .onErrorReturn(Status.FAIL);
    }

    private Status toStatus(HttpStatus status) {
        if (status == HttpStatus.OK) {
            return Status.OK;
        } else {
            return Status.FAIL;
        }
    }


}
