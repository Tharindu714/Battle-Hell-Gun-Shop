package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "stock")
public class Stock implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "w_trigger", length = 25, nullable = false)
    private String w_trigger;

    @Column(name = "magazine", length = 25, nullable = false)
    private String magazine;

    @Column(name = "forestock", length = 25, nullable = false)
    private String forestock;

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

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
    }

    public String getForestock() {
        return forestock;
    }

    public void setForestock(String forestock) {
        this.forestock = forestock;
    }

    
}
