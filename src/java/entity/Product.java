package entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "qty", nullable = false)
    private int qty;

    @Column(name = "reg_date", nullable = false)
    private Date reg_date;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Gun_status gun_status;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "barrel_id")
    private Barrel barrel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "gun_condition_id")
    private Gun_condition gun_condition;

    @ManyToOne
    @JoinColumn(name = "action_id")
    private Gun_Action action;

    @ManyToOne
    @JoinColumn(name = "bullet_type_id")
    private Bullet_type bullet_type;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Gun_status getGun_status() {
        return gun_status;
    }

    public void setGun_status(Gun_status gun_status) {
        this.gun_status = gun_status;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Barrel getBarrel() {
        return barrel;
    }

    public void setBarrel(Barrel barrel) {
        this.barrel = barrel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gun_condition getGun_condition() {
        return gun_condition;
    }

    public void setGun_condition(Gun_condition gun_condition) {
        this.gun_condition = gun_condition;
    }

    public Gun_Action getAction() {
        return action;
    }

    public void setAction(Gun_Action action) {
        this.action = action;
    }

    public Bullet_type getBullet_type() {
        return bullet_type;
    }

    public void setBullet_type(Bullet_type bullet_type) {
        this.bullet_type = bullet_type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
