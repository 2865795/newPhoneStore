package ee.ivkhkdev.models;

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String country;

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return name + " (" + country + ")";
    }
}

