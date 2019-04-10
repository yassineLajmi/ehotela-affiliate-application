package ch.hotela.affiliate.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Affiliate.
 */
@Entity
@Table(name = "affiliate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Affiliate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_affilie", nullable = false)
    private Long numeroAffilie;

    @Column(name = "titre")
    private String titre;

    @Column(name = "noms")
    private String noms;

    @Column(name = "adresse_ligne_1")
    private String adresseLigne1;

    @Column(name = "adresse_ligne_2")
    private String adresseLigne2;

    @Column(name = "numero_postal")
    private Integer numeroPostal;

    @Column(name = "localite")
    private String localite;

    @Column(name = "canton")
    private String canton;

    @Column(name = "langue")
    private Integer langue;

    @Column(name = "groupe_code")
    private Integer groupeCode;

    @Column(name = "groupe_nom")
    private String groupeNom;

    @Column(name = "is_ccnt")
    private Integer isCcnt;

    @Column(name = "time_type")
    private String timeType;

    @NotNull
    @Column(name = "group_type", nullable = false)
    private String groupType;

    @NotNull
    @Column(name = "weekly_work_time", precision = 10, scale = 2, nullable = false)
    private BigDecimal weeklyWorkTime;

    @OneToMany(mappedBy = "affiliate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rubric> employees = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroAffilie() {
        return numeroAffilie;
    }

    public Affiliate numeroAffilie(Long numeroAffilie) {
        this.numeroAffilie = numeroAffilie;
        return this;
    }

    public void setNumeroAffilie(Long numeroAffilie) {
        this.numeroAffilie = numeroAffilie;
    }

    public String getTitre() {
        return titre;
    }

    public Affiliate titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNoms() {
        return noms;
    }

    public Affiliate noms(String noms) {
        this.noms = noms;
        return this;
    }

    public void setNoms(String noms) {
        this.noms = noms;
    }

    public String getAdresseLigne1() {
        return adresseLigne1;
    }

    public Affiliate adresseLigne1(String adresseLigne1) {
        this.adresseLigne1 = adresseLigne1;
        return this;
    }

    public void setAdresseLigne1(String adresseLigne1) {
        this.adresseLigne1 = adresseLigne1;
    }

    public String getAdresseLigne2() {
        return adresseLigne2;
    }

    public Affiliate adresseLigne2(String adresseLigne2) {
        this.adresseLigne2 = adresseLigne2;
        return this;
    }

    public void setAdresseLigne2(String adresseLigne2) {
        this.adresseLigne2 = adresseLigne2;
    }

    public Integer getNumeroPostal() {
        return numeroPostal;
    }

    public Affiliate numeroPostal(Integer numeroPostal) {
        this.numeroPostal = numeroPostal;
        return this;
    }

    public void setNumeroPostal(Integer numeroPostal) {
        this.numeroPostal = numeroPostal;
    }

    public String getLocalite() {
        return localite;
    }

    public Affiliate localite(String localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getCanton() {
        return canton;
    }

    public Affiliate canton(String canton) {
        this.canton = canton;
        return this;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public Integer getLangue() {
        return langue;
    }

    public Affiliate langue(Integer langue) {
        this.langue = langue;
        return this;
    }

    public void setLangue(Integer langue) {
        this.langue = langue;
    }

    public Integer getGroupeCode() {
        return groupeCode;
    }

    public Affiliate groupeCode(Integer groupeCode) {
        this.groupeCode = groupeCode;
        return this;
    }

    public void setGroupeCode(Integer groupeCode) {
        this.groupeCode = groupeCode;
    }

    public String getGroupeNom() {
        return groupeNom;
    }

    public Affiliate groupeNom(String groupeNom) {
        this.groupeNom = groupeNom;
        return this;
    }

    public void setGroupeNom(String groupeNom) {
        this.groupeNom = groupeNom;
    }

    public Integer getIsCcnt() {
        return isCcnt;
    }

    public Affiliate isCcnt(Integer isCcnt) {
        this.isCcnt = isCcnt;
        return this;
    }

    public void setIsCcnt(Integer isCcnt) {
        this.isCcnt = isCcnt;
    }

    public String getTimeType() {
        return timeType;
    }

    public Affiliate timeType(String timeType) {
        this.timeType = timeType;
        return this;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getGroupType() {
        return groupType;
    }

    public Affiliate groupType(String groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public BigDecimal getWeeklyWorkTime() {
        return weeklyWorkTime;
    }

    public Affiliate weeklyWorkTime(BigDecimal weeklyWorkTime) {
        this.weeklyWorkTime = weeklyWorkTime;
        return this;
    }

    public void setWeeklyWorkTime(BigDecimal weeklyWorkTime) {
        this.weeklyWorkTime = weeklyWorkTime;
    }

    public Set<Rubric> getEmployees() {
        return employees;
    }

    public Affiliate employees(Set<Rubric> rubrics) {
        this.employees = rubrics;
        return this;
    }

    public Affiliate addEmployee(Rubric rubric) {
        this.employees.add(rubric);
        rubric.setAffiliate(this);
        return this;
    }

    public Affiliate removeEmployee(Rubric rubric) {
        this.employees.remove(rubric);
        rubric.setAffiliate(null);
        return this;
    }

    public void setEmployees(Set<Rubric> rubrics) {
        this.employees = rubrics;
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
        Affiliate affiliate = (Affiliate) o;
        if (affiliate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), affiliate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Affiliate{" +
            "id=" + getId() +
            ", numeroAffilie=" + getNumeroAffilie() +
            ", titre='" + getTitre() + "'" +
            ", noms='" + getNoms() + "'" +
            ", adresseLigne1='" + getAdresseLigne1() + "'" +
            ", adresseLigne2='" + getAdresseLigne2() + "'" +
            ", numeroPostal=" + getNumeroPostal() +
            ", localite='" + getLocalite() + "'" +
            ", canton='" + getCanton() + "'" +
            ", langue=" + getLangue() +
            ", groupeCode=" + getGroupeCode() +
            ", groupeNom='" + getGroupeNom() + "'" +
            ", isCcnt=" + getIsCcnt() +
            ", timeType='" + getTimeType() + "'" +
            ", groupType='" + getGroupType() + "'" +
            ", weeklyWorkTime=" + getWeeklyWorkTime() +
            "}";
    }
}
