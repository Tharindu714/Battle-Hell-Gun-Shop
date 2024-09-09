package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bullets")
public class Bullets implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "powder", length = 25, nullable = false)
    private String powder;

    @Column(name = "bullet_case", length = 25, nullable = false)
    private String bullet_case;

    @Column(name = "primer", length = 25, nullable = false)
    private String primer;

    public Bullets() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPowder() {
        return powder;
    }

    public void setPowder(String powder) {
        this.powder = powder;
    }

    public String getBullet_case() {
        return bullet_case;
    }

    public void setBullet_case(String bullet_case) {
        this.bullet_case = bullet_case;
    }

    public String getPrimer() {
        return primer;
    }

    public void setPrimer(String primer) {
        this.primer = primer;
    }
}
