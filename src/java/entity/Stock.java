package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "stock")
public class Stock implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "w_trigger", length = 45, nullable = false)
    private String w_trigger;

    @ManyToOne
    @JoinColumn(name = "forestock_id")
    private Forestock forestock;

    @ManyToOne
    @JoinColumn(name = "magazine_id")
    private Magazine magazine;

    public Stock() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getW_trigger() {
        return w_trigger;
    }

    public void setW_trigger(String w_trigger) {
        this.w_trigger = w_trigger;
    }

    public Forestock getForestock() {
        return forestock;
    }

    public void setForestock(Forestock forestock) {
        this.forestock = forestock;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
  
}
