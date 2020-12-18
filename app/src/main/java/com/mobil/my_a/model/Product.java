package com.mobil.my_a.model;

public class Product {
    private int id;
    private String pname;
    private String description;
    private String oldprice;
    private String newprice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice;
    }

    public String getNewprice() {
        return newprice;
    }

    public void setNewprice(String newprice) {
        this.newprice = newprice;
    }
}
