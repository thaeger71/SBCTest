package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Transport;

/**
 * A Registration.
 */
@Entity
@Table(name = "registration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "remote_address")
    private String remoteAddress;

    @Column(name = "remote_port")
    private Integer remotePort;

    @Column(name = "expires")
    private Integer expires;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "contact")
    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "transport")
    private Transport transport;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("registrations")
    private Peer peer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public Registration remoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public Registration remotePort(Integer remotePort) {
        this.remotePort = remotePort;
        return this;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public Integer getExpires() {
        return expires;
    }

    public Registration expires(Integer expires) {
        this.expires = expires;
        return this;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Registration userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getContact() {
        return contact;
    }

    public Registration contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Transport getTransport() {
        return transport;
    }

    public Registration transport(Transport transport) {
        this.transport = transport;
        return this;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Peer getPeer() {
        return peer;
    }

    public Registration peer(Peer peer) {
        this.peer = peer;
        return this;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
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
        Registration registration = (Registration) o;
        if (registration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Registration{" +
            "id=" + getId() +
            ", remoteAddress='" + getRemoteAddress() + "'" +
            ", remotePort=" + getRemotePort() +
            ", expires=" + getExpires() +
            ", userAgent='" + getUserAgent() + "'" +
            ", contact='" + getContact() + "'" +
            ", transport='" + getTransport() + "'" +
            "}";
    }
}
