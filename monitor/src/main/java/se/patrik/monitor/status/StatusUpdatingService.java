package se.patrik.monitor.status;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.patrik.monitor.service.ServiceDto;
import se.patrik.monitor.service.ServiceService;

@Service
public class StatusUpdatingService {
    private final ServiceService serviceService;
    private final StatusService statusService;
    private final StatusCollector statusCollector;

    public StatusUpdatingService(ServiceService serviceService, StatusService statusService, StatusCollector statusCollector) {
        this.serviceService = serviceService;
        this.statusService = statusService;
        this.statusCollector = statusCollector;
    }

    @Scheduled(fixedRate = 5000)
    public void updateStatuses() {
        serviceService.getServices().forEach(this::updateStatus);
    }

    public void updateStatus(ServiceDto service) {
        statusCollector.getStatus(service.getUrl()).subscribe(status -> statusService.updateServiceStatus(service.getId(), status));
    }
}
