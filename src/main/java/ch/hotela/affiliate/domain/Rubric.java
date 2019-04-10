package ch.hotela.affiliate.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Rubric.
 */
@Entity
@Table(name = "rubric")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rubric implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "rubric_type", nullable = false)
    private String rubricType;

    @Column(name = "total_rate", precision = 10, scale = 2)
    private BigDecimal totalRate;

    @Column(name = "sex")
    private String sex;

    @NotNull
    @Column(name = "start_age", nullable = false)
    private Integer startAge;

    @NotNull
    @Column(name = "end_age", nullable = false)
    private Integer endAge;

    @NotNull
    @Column(name = "employee_rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal employeeRate;

    @Column(name = "di_rate", precision = 10, scale = 2)
    private BigDecimal diRate;

    @Column(name = "ail_salary_limit", precision = 10, scale = 2)
    private BigDecimal ailSalaryLimit;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Affiliate affiliate;

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

    public Rubric description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Rubric startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Rubric endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRubricType() {
        return rubricType;
    }

    public Rubric rubricType(String rubricType) {
        this.rubricType = rubricType;
        return this;
    }

    public void setRubricType(String rubricType) {
        this.rubricType = rubricType;
    }

    public BigDecimal getTotalRate() {
        return totalRate;
    }

    public Rubric totalRate(BigDecimal totalRate) {
        this.totalRate = totalRate;
        return this;
    }

    public void setTotalRate(BigDecimal totalRate) {
        this.totalRate = totalRate;
    }

    public String getSex() {
        return sex;
    }

    public Rubric sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getStartAge() {
        return startAge;
    }

    public Rubric startAge(Integer startAge) {
        this.startAge = startAge;
        return this;
    }

    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }

    public Integer getEndAge() {
        return endAge;
    }

    public Rubric endAge(Integer endAge) {
        this.endAge = endAge;
        return this;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }

    public BigDecimal getEmployeeRate() {
        return employeeRate;
    }

    public Rubric employeeRate(BigDecimal employeeRate) {
        this.employeeRate = employeeRate;
        return this;
    }

    public void setEmployeeRate(BigDecimal employeeRate) {
        this.employeeRate = employeeRate;
    }

    public BigDecimal getDiRate() {
        return diRate;
    }

    public Rubric diRate(BigDecimal diRate) {
        this.diRate = diRate;
        return this;
    }

    public void setDiRate(BigDecimal diRate) {
        this.diRate = diRate;
    }

    public BigDecimal getAilSalaryLimit() {
        return ailSalaryLimit;
    }

    public Rubric ailSalaryLimit(BigDecimal ailSalaryLimit) {
        this.ailSalaryLimit = ailSalaryLimit;
        return this;
    }

    public void setAilSalaryLimit(BigDecimal ailSalaryLimit) {
        this.ailSalaryLimit = ailSalaryLimit;
    }

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public Rubric affiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
        return this;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
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
        Rubric rubric = (Rubric) o;
        if (rubric.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rubric.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rubric{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", rubricType='" + getRubricType() + "'" +
            ", totalRate=" + getTotalRate() +
            ", sex='" + getSex() + "'" +
            ", startAge=" + getStartAge() +
            ", endAge=" + getEndAge() +
            ", employeeRate=" + getEmployeeRate() +
            ", diRate=" + getDiRate() +
            ", ailSalaryLimit=" + getAilSalaryLimit() +
            "}";
    }
}
