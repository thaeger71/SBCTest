package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Transport;

/**
 * A Peer.
 */
@Entity
@Table(name = "peer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Peer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "auth_user")
    private String authUser;

    @Column(name = "secret")
    private String secret;

    @Column(name = "address")
    private String address;

    @Column(name = "port")
    private Integer port;

    @Enumerated(EnumType.STRING)
    @Column(name = "transport")
    private Transport transport;

    @Column(name = "register")
    private Boolean register;

    @Column(name = "qualify")
    private Integer qualify;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Peer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public Peer user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuthUser() {
        return authUser;
    }

    public Peer authUser(String authUser) {
        this.authUser = authUser;
        return this;
    }

    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    public String getSecret() {
        return secret;
    }

    public Peer secret(String secret) {
        this.secret = secret;
        return this;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAddress() {
        return address;
    }

    public Peer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public Peer port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Transport getTransport() {
        return transport;
    }

    public Peer transport(Transport transport) {
        this.transport = transport;
        return this;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Boolean isRegister() {
        return register;
    }

    public Peer register(Boolean register) {
        this.register = register;
        return this;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    public Integer getQualify() {
        return qualify;
    }

    public Peer qualify(Integer qualify) {
        this.qualify = qualify;
        return this;
    }

    public void setQualify(Integer qualify) {
        this.qualify = qualify;
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
        Peer peer = (Peer) o;
        if (peer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), peer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Peer{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", user='" + getUser() + "'" +
            ", authUser='" + getAuthUser() + "'" +
            ", secret='" + getSecret() + "'" +
            ", address='" + getAddress() + "'" +
            ", port=" + getPort() +
            ", transport='" + getTransport() + "'" +
            ", register='" + isRegister() + "'" +
            ", qualify=" + getQualify() +
            "}";
    }
}
