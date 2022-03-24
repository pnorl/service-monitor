package se.patrik.monitor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceTest {
    @Mock private ServiceRepository serviceRepository;
    @InjectMocks ServiceService service;

    @Test
    public void testCreateService() {
        CreateServiceDto createServiceDto = new CreateServiceDto();
        createServiceDto.setName("name");
        createServiceDto.setUrl("url");
        when(serviceRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        ServiceDto created = service.createService(createServiceDto);
        assertThat(created.getCreated()).isNotNull();
        assertThat(created.getName()).isEqualTo("name");
        assertThat(created.getUrl()).isEqualTo("url");
    }

    @Test
    public void testGetServices() {
        when(serviceRepository.findAll()).thenReturn(Collections.singletonList(new ServiceEntity("name", "url")));
        List<ServiceDto> services = service.getServices();
        assertThat(services.size()).isEqualTo(1);
        assertThat(services.get(0).getName()).isEqualTo("name");
        assertThat(services.get(0).getUrl()).isEqualTo("url");
    }
}
