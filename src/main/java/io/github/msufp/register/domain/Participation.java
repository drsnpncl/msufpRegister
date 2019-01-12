package io.github.msufp.register.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Participation.
 */
@Entity
@Table(name = "participation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Participation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_date")
    private ZonedDateTime regDate;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "payment_accept")
    private Boolean paymentAccept;

    @Column(name = "abstract_select")
    private Boolean abstractSelect;

    @OneToOne    @JoinColumn(unique = true)
    private UserGroup userGroup;

    @OneToOne    @JoinColumn(unique = true)
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRegDate() {
        return regDate;
    }

    public Participation regDate(ZonedDateTime regDate) {
        this.regDate = regDate;
        return this;
    }

    public void setRegDate(ZonedDateTime regDate) {
        this.regDate = regDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public Participation paymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Boolean isPaymentAccept() {
        return paymentAccept;
    }

    public Participation paymentAccept(Boolean paymentAccept) {
        this.paymentAccept = paymentAccept;
        return this;
    }

    public void setPaymentAccept(Boolean paymentAccept) {
        this.paymentAccept = paymentAccept;
    }

    public Boolean isAbstractSelect() {
        return abstractSelect;
    }

    public Participation abstractSelect(Boolean abstractSelect) {
        this.abstractSelect = abstractSelect;
        return this;
    }

    public void setAbstractSelect(Boolean abstractSelect) {
        this.abstractSelect = abstractSelect;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public Participation userGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
        return this;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Event getEvent() {
        return event;
    }

    public Participation event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
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
        Participation participation = (Participation) o;
        if (participation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), participation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Participation{" +
            "id=" + getId() +
            ", regDate='" + getRegDate() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", paymentAccept='" + isPaymentAccept() + "'" +
            ", abstractSelect='" + isAbstractSelect() + "'" +
            "}";
    }
}
