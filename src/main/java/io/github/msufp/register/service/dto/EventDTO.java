package io.github.msufp.register.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Event entity.
 */
public class EventDTO implements Serializable {

    private Long id;

    private String name;

    private Integer fee;

    private String numberOfMembers;

    private String typeOfParticipation;

    private Boolean freeEvent;

    private Boolean abstractSelect;

    private Boolean test;

    private String status;

    private String parentEvent;

    private String eventType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(String numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public String getTypeOfParticipation() {
        return typeOfParticipation;
    }

    public void setTypeOfParticipation(String typeOfParticipation) {
        this.typeOfParticipation = typeOfParticipation;
    }

    public Boolean isFreeEvent() {
        return freeEvent;
    }

    public void setFreeEvent(Boolean freeEvent) {
        this.freeEvent = freeEvent;
    }

    public Boolean isAbstractSelect() {
        return abstractSelect;
    }

    public void setAbstractSelect(Boolean abstractSelect) {
        this.abstractSelect = abstractSelect;
    }

    public Boolean isTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParentEvent() {
        return parentEvent;
    }

    public void setParentEvent(String parentEvent) {
        this.parentEvent = parentEvent;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (eventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fee=" + getFee() +
            ", numberOfMembers='" + getNumberOfMembers() + "'" +
            ", typeOfParticipation='" + getTypeOfParticipation() + "'" +
            ", freeEvent='" + isFreeEvent() + "'" +
            ", abstractSelect='" + isAbstractSelect() + "'" +
            ", test='" + isTest() + "'" +
            ", status='" + getStatus() + "'" +
            ", parentEvent='" + getParentEvent() + "'" +
            ", eventType='" + getEventType() + "'" +
            "}";
    }
}
