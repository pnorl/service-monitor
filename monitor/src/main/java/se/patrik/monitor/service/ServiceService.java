package se.patrik.monitor.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public ServiceDto createService(CreateServiceDto createServiceDto) {
        ServiceEntity service = toEntity(createServiceDto);
        return toDto(serviceRepository.save(service));
    }

    @Transactional(readOnly = true)
    public List<ServiceDto> getServices() {
        return serviceRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private ServiceEntity toEntity(CreateServiceDto createServiceDto) {
        return new ServiceEntity(createServiceDto.getName(), createServiceDto.getUrl());
    }

    private ServiceDto toDto(ServiceEntity service){
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(service.getId());
        serviceDto.setCreated(service.getCreated());
        serviceDto.setName(service.getName());
        serviceDto.setUrl(service.getUrl());
        serviceDto.setStatus(service.getLatestStatus());
        return serviceDto;
    }
}
