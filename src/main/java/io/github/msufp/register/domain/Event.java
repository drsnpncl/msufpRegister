package io.github.msufp.register.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fee")
    private Integer fee;

    @Column(name = "number_of_members")
    private String numberOfMembers;

    @Column(name = "type_of_participation")
    private String typeOfParticipation;

    @Column(name = "free_event")
    private Boolean freeEvent;

    @Column(name = "abstract_select")
    private Boolean abstractSelect;

    @Column(name = "test")
    private Boolean test;

    @Column(name = "status")
    private String status;

    @Column(name = "parent_event")
    private String parentEvent;

    @Column(name = "event_type")
    private String eventType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFee() {
        return fee;
    }

    public Event fee(Integer fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getNumberOfMembers() {
        return numberOfMembers;
    }

    public Event numberOfMembers(String numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
        return this;
    }

    public void setNumberOfMembers(String numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public String getTypeOfParticipation() {
        return typeOfParticipation;
    }

    public Event typeOfParticipation(String typeOfParticipation) {
        this.typeOfParticipation = typeOfParticipation;
        return this;
    }

    public void setTypeOfParticipation(String typeOfParticipation) {
        this.typeOfParticipation = typeOfParticipation;
    }

    public Boolean isFreeEvent() {
        return freeEvent;
    }

    public Event freeEvent(Boolean freeEvent) {
        this.freeEvent = freeEvent;
        return this;
    }

    public void setFreeEvent(Boolean freeEvent) {
        this.freeEvent = freeEvent;
    }

    public Boolean isAbstractSelect() {
        return abstractSelect;
    }

    public Event abstractSelect(Boolean abstractSelect) {
        this.abstractSelect = abstractSelect;
        return this;
    }

    public void setAbstractSelect(Boolean abstractSelect) {
        this.abstractSelect = abstractSelect;
    }

    public Boolean isTest() {
        return test;
    }

    public Event test(Boolean test) {
        this.test = test;
        return this;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getStatus() {
        return status;
    }

    public Event status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParentEvent() {
        return parentEvent;
    }

    public Event parentEvent(String parentEvent) {
        this.parentEvent = parentEvent;
        return this;
    }

    public void setParentEvent(String parentEvent) {
        this.parentEvent = parentEvent;
    }

    public String getEventType() {
        return eventType;
    }

    public Event eventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
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
