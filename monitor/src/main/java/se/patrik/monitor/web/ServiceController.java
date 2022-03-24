package se.patrik.monitor.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import se.patrik.monitor.service.CreateServiceDto;
import se.patrik.monitor.service.ServiceDto;
import se.patrik.monitor.service.ServiceService;
import se.patrik.monitor.status.StatusUpdate;
import se.patrik.monitor.status.StatusService;
import se.patrik.monitor.status.StatusUpdatingService;

import java.util.List;

@RestController
@RequestMapping("/v1/services")
public class ServiceController {
    private final ServiceService serviceService;
    private final StatusUpdatingService statusUpdatingService;
    private final StatusService statusService;

    public ServiceController(ServiceService serviceService, StatusUpdatingService statusUpdatingService, StatusService statusService) {
        this.serviceService = serviceService;
        this.statusUpdatingService = statusUpdatingService;
        this.statusService = statusService;
    }

    @PostMapping
    public ServiceDto createService(@RequestBody CreateServiceDto createServiceRequest) {
        ServiceDto service = serviceService.createService(createServiceRequest);
        statusUpdatingService.updateStatus(service);
        return service;
    }

    @GetMapping
    public List<ServiceDto> getServices() {
        return serviceService.getServices();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/statuses", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StatusUpdate> getStatusUpdates() {
        return statusService.getStatusUpdates();
    }
}