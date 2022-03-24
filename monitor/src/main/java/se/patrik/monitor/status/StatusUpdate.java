package se.patrik.monitor.status;

public class StatusUpdate {
    public StatusUpdate(Long serviceId, Status status) {
        this.status = status;
        this.serviceId = serviceId;
    }
    private final Status status;

    private final Long serviceId;

    public Status getStatus() {
        return status;
    }

    public Long getServiceId() {
        return serviceId;
    }
}
