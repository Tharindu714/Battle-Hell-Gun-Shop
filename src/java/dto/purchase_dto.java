package dto;


import entity.Address;
import entity.User;
import java.io.Serializable;
import java.util.Date;

public class purchase_dto implements Serializable {

    private Date datetime;
    private User user;
    private Address address;

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    
}
