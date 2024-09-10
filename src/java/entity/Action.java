package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "action")
public class Action implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bolt", length = 25, nullable = false)
    private String bolt;

    @Column(name = "safetyclip", length = 45, nullable = false)
    private String safetyclip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBolt() {
        return bolt;
    }

    public void setBolt(String bolt) {
        this.bolt = bolt;
    }

    public String getSafetyclip() {
        return safetyclip;
    }

    public void setSafetyclip(String safetyclip) {
        this.safetyclip = safetyclip;
    }
}
