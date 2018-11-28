package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.MatchType;

/**
 * A DialplanEntry.
 */
@Entity
@Table(name = "dialplan_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DialplanEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "from_peer_match_type", nullable = false)
    private MatchType fromPeerMatchType;

    @Column(name = "from_peer_match_exp")
    private String fromPeerMatchExp;

    @NotNull
    @Column(name = "destination", nullable = false)
    private String destination;

    @NotNull
    @Column(name = "new_destination", nullable = false)
    private String newDestination;

    @NotNull
    @Column(name = "source", nullable = false)
    private String source;

    @NotNull
    @Column(name = "new_source", nullable = false)
    private String newSource;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dialplanEntries")
    private Peer fromPeer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MatchType getFromPeerMatchType() {
        return fromPeerMatchType;
    }

    public DialplanEntry fromPeerMatchType(MatchType fromPeerMatchType) {
        this.fromPeerMatchType = fromPeerMatchType;
        return this;
    }

    public void setFromPeerMatchType(MatchType fromPeerMatchType) {
        this.fromPeerMatchType = fromPeerMatchType;
    }

    public String getFromPeerMatchExp() {
        return fromPeerMatchExp;
    }

    public DialplanEntry fromPeerMatchExp(String fromPeerMatchExp) {
        this.fromPeerMatchExp = fromPeerMatchExp;
        return this;
    }

    public void setFromPeerMatchExp(String fromPeerMatchExp) {
        this.fromPeerMatchExp = fromPeerMatchExp;
    }

    public String getDestination() {
        return destination;
    }

    public DialplanEntry destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNewDestination() {
        return newDestination;
    }

    public DialplanEntry newDestination(String newDestination) {
        this.newDestination = newDestination;
        return this;
    }

    public void setNewDestination(String newDestination) {
        this.newDestination = newDestination;
    }

    public String getSource() {
        return source;
    }

    public DialplanEntry source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNewSource() {
        return newSource;
    }

    public DialplanEntry newSource(String newSource) {
        this.newSource = newSource;
        return this;
    }

    public void setNewSource(String newSource) {
        this.newSource = newSource;
    }

    public Peer getFromPeer() {
        return fromPeer;
    }

    public DialplanEntry fromPeer(Peer peer) {
        this.fromPeer = peer;
        return this;
    }

    public void setFromPeer(Peer peer) {
        this.fromPeer = peer;
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
        DialplanEntry dialplanEntry = (DialplanEntry) o;
        if (dialplanEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dialplanEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DialplanEntry{" +
            "id=" + getId() +
            ", fromPeerMatchType='" + getFromPeerMatchType() + "'" +
            ", fromPeerMatchExp='" + getFromPeerMatchExp() + "'" +
            ", destination='" + getDestination() + "'" +
            ", newDestination='" + getNewDestination() + "'" +
            ", source='" + getSource() + "'" +
            ", newSource='" + getNewSource() + "'" +
            "}";
    }
}
