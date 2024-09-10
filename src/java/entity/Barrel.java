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
@Table(name = "barrel")
public class Barrel implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chamber", length = 25, nullable = false)
    private String chamber;

    @ManyToOne
    @JoinColumn(name = "gun_sights_id")
    private Gun_sight gun_sight;

    @ManyToOne
    @JoinColumn(name = "muzzle_id")
    private Muzzle muzzle;

    public Barrel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public Gun_sight getGun_sight() {
        return gun_sight;
    }

    public void setGun_sight(Gun_sight gun_sight) {
        this.gun_sight = gun_sight;
    }

    public Muzzle getMuzzle() {
        return muzzle;
    }

    public void setMuzzle(Muzzle muzzle) {
        this.muzzle = muzzle;
    }

}
