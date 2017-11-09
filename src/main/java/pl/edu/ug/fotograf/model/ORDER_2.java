package pl.edu.ug.fotograf.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(name = "order_2.all", query = "SELECT o2 FROM ORDER_2 o2")
public class ORDER_2 {
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    private String description;

    public ORDER_2() {
    }

    public ORDER_2(Date datetime, String description) {
        this.datetime = datetime;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
