package br.com.celk.evaluation.ufengine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.celk.evaluation.ufengine.domain.enumeration.StreetType;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "street_type", nullable = false)
    private StreetType streetType;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "street_name", length = 50, nullable = false)
    private String streetName;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Size(min = 5, max = 10)
    @Column(name = "postal_code", length = 10, nullable = false)
    private String postalCode;

    @ManyToOne
    @JsonIgnoreProperties("addresses")
    private County county;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StreetType getStreetType() {
        return streetType;
    }

    public Address streetType(StreetType streetType) {
        this.streetType = streetType;
        return this;
    }

    public void setStreetType(StreetType streetType) {
        this.streetType = streetType;
    }

    public String getStreetName() {
        return streetName;
    }

    public Address streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getNumber() {
        return number;
    }

    public Address number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public County getCounty() {
        return county;
    }

    public Address county(County county) {
        this.county = county;
        return this;
    }

    public void setCounty(County county) {
        this.county = county;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", streetType='" + getStreetType() + "'" +
            ", streetName='" + getStreetName() + "'" +
            ", number=" + getNumber() +
            ", postalCode='" + getPostalCode() + "'" +
            "}";
    }
}
