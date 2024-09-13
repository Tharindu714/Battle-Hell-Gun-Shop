package dto;

import entity.Product;
import java.io.Serializable;

public class Wish_DTO implements Serializable {

    private Product product;

    public Wish_DTO() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
