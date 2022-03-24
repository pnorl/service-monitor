package se.patrik.monitor.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import se.patrik.monitor.service.ServiceEntity;
import se.patrik.monitor.service.ServiceRepository;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {
    @Mock private ServiceRepository serviceRepository;
    @InjectMocks private StatusService statusService;

    @Test
    public void testStatusUpdatesAvailableInStatusStream() {
        when(serviceRepository.findById(eq(1L))).thenReturn(Optional.of(new ServiceEntity("name", "url")));
        when(serviceRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        Flux<StatusUpdate> statusUpdates = statusService.getStatusUpdates();
        statusService.updateServiceStatus(1L, Status.OK);
        StepVerifier.create(statusUpdates)
                .expectNextMatches(s -> s.getStatus() == Status.OK && s.getServiceId() == 1L).thenCancel().verify();
    }
}
