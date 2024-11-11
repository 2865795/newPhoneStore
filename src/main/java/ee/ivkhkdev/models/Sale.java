package ee.ivkhkdev.models;

import java.io.Serializable;

public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;
    private Phone phone;

    public Sale(User user, Phone phone) {
        this.user = user;
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String toString() {
        return "Покупатель: " + user.toString()+ " Телефон: " + phone.toString();
    }
}
