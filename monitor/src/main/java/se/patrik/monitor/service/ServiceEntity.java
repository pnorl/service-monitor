package se.patrik.monitor.service;

import se.patrik.monitor.status.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Entity
@Table(name="services")
public class ServiceEntity {

    public ServiceEntity() {
    }

    public ServiceEntity(String name, String url) {
        this.name = name;
        this.url = url;
        this.created = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private LocalDateTime created;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

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

    public Optional<Status> getLatestStatus() {
        return Optional.ofNullable(status);
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
