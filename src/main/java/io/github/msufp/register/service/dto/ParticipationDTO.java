package io.github.msufp.register.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Participation entity.
 */
public class ParticipationDTO implements Serializable {

    private Long id;

    private ZonedDateTime regDate;

    private String paymentMode;

    private Boolean paymentAccept;

    private Boolean abstractSelect;

    private Long userGroupId;

    private Long eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(ZonedDateTime regDate) {
        this.regDate = regDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Boolean isPaymentAccept() {
        return paymentAccept;
    }

    public void setPaymentAccept(Boolean paymentAccept) {
        this.paymentAccept = paymentAccept;
    }

    public Boolean isAbstractSelect() {
        return abstractSelect;
    }

    public void setAbstractSelect(Boolean abstractSelect) {
        this.abstractSelect = abstractSelect;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParticipationDTO participationDTO = (ParticipationDTO) o;
        if (participationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), participationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParticipationDTO{" +
            "id=" + getId() +
            ", regDate='" + getRegDate() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", paymentAccept='" + isPaymentAccept() + "'" +
            ", abstractSelect='" + isAbstractSelect() + "'" +
            ", userGroup=" + getUserGroupId() +
            ", event=" + getEventId() +
            "}";
    }
}
