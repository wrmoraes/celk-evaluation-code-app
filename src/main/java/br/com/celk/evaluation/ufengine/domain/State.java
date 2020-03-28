package br.com.celk.evaluation.ufengine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A State.
 */
@Entity
@Table(name = "state")
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "initials", length = 2, nullable = false)
    private String initials;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @OneToMany(mappedBy = "state")
    private Set<County> counties = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("states")
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public State initials(String initials) {
        this.initials = initials;
        return this;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public State name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public State createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<County> getCounties() {
        return counties;
    }

    public State counties(Set<County> counties) {
        this.counties = counties;
        return this;
    }

    public State addCounty(County county) {
        this.counties.add(county);
        county.setState(this);
        return this;
    }

    public State removeCounty(County county) {
        this.counties.remove(county);
        county.setState(null);
        return this;
    }

    public void setCounties(Set<County> counties) {
        this.counties = counties;
    }

    public Country getCountry() {
        return country;
    }

    public State country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof State)) {
            return false;
        }
        return id != null && id.equals(((State) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "State{" +
            "id=" + getId() +
            ", initials='" + getInitials() + "'" +
            ", name='" + getName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
