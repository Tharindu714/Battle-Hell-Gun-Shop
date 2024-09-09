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
@Table(name = "bullet_type")
public class Bullet_type implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type", length = 15, nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "bullets_id")
    private Bullets bullets;

    public Bullet_type() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bullets getBullets() {
        return bullets;
    }

    public void setBullets(Bullets bullets) {
        this.bullets = bullets;
    }
}
