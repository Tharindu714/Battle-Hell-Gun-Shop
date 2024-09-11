package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_items")
public class Purchase_items {
        @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "puchase_id")
    private Purchase order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "qty", nullable = false)
    private int qty;

    @ManyToOne
    @JoinColumn(name = "purchase_status_id")
    private Purchase_Status order_status;

    public Purchase_items() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Purchase getOrder() {
        return order;
    }

    public void setOrder(Purchase order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Purchase_Status getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Purchase_Status order_status) {
        this.order_status = order_status;
    }
}
