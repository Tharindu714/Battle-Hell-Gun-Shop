package dto;

import entity.Product;
import entity.Purchase;
import entity.Purchase_Status;
import java.io.Serializable;

public class Purchase_item_DTO implements Serializable {

    private int qty;
    private Product product;
    private Purchase purchase;
    private Purchase_Status purchase_status;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Purchase_Status getPurchase_status() {
        return purchase_status;
    }

    public void setPurchase_status(Purchase_Status purchase_status) {
        this.purchase_status = purchase_status;
    }
}
