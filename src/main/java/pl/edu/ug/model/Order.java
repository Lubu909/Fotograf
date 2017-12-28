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
    private Date dataZamowienia;

    @Temporal(TemporalType.TIMESTAMP)
    private Date terminWykonania;

    private String description;

    private Integer hours;

    private Integer status;
}
