package pl.edu.ug.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Transient
    public static final Integer STATUS_CREATED = 1;
    @Transient
    public static final Integer STATUS_MODIFIED = 2;
    @Transient
    public static final Integer STATUS_ACCEPTED = 3;
    @Transient
    public static final Integer STATUS_REJECTED = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "klient_id")
    private User klient;

    @ManyToOne
    @JoinColumn(name = "fotograf_id")
    private User fotograf;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_zamowienia")
    private Date dataZamowienia;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="termin_wykonania")
    private Date terminWykonania;

    @Transient
    private String dateForm;
    @Transient
    private String timeForm;

    private String description;

    private Integer hours;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getKlient() {
        return klient;
    }

    public void setKlient(User klient) {
        this.klient = klient;
    }

    public User getFotograf() {
        return fotograf;
    }

    public void setFotograf(User fotograf) {
        this.fotograf = fotograf;
    }

    public Date getDataZamowienia() {
        return dataZamowienia;
    }

    public void setDataZamowienia(Date dataZamowienia) {
        this.dataZamowienia = dataZamowienia;
    }

    public Date getTerminWykonania() {
        return terminWykonania;
    }

    public void setTerminWykonania(Date terminWykonania) {
        this.terminWykonania = terminWykonania;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDateForm() {
        return dateForm;
    }

    public void setDateForm(String dateForm) {
        this.dateForm = dateForm;
    }

    public String getTimeForm() {
        return timeForm;
    }

    public void setTimeForm(String timeForm) {
        this.timeForm = timeForm;
    }
}
