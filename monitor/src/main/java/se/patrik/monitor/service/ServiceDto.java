package se.patrik.monitor.service;

import se.patrik.monitor.status.Status;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

public class ServiceDto {
    public ServiceDto() {
    }

    private Long id;
    private String name;
    private String url;
    private LocalDateTime created;
    private Optional<Status> status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Optional<Status> getStatus() {
        return status;
    }

    public void setStatus(Optional<Status> status) {
        this.status = status;
    }
}
