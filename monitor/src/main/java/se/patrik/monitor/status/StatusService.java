package se.patrik.monitor.status;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.*;
import se.patrik.monitor.service.ServiceRepository;

@Service
public class StatusService {

    private final ServiceRepository serviceRepository;
    final Sinks.Many<StatusUpdate> statusUpdates;

    public StatusService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
        this.statusUpdates = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Flux<StatusUpdate> getStatusUpdates() {
        return statusUpdates.asFlux();
    }

    @Transactional
    public void updateServiceStatus(Long serviceId, Status status) {
        serviceRepository.findById(serviceId).ifPresent(service -> {
            service.setStatus(status);
            serviceRepository.save(service);
            statusUpdates.tryEmitNext(new StatusUpdate(serviceId, status));
        });
    }
}