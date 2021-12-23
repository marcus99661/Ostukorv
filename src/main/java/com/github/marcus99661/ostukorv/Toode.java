package com.github.marcus99661.ostukorv;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Toode {
    @Id
    public String id;

    // TULEB PANNA CONSTRUCTORISSE, TOSTRING, GETTER JA SETTER
    //public String tooteKood;
    public String name;
    public String picLoc;
    public String desc;
    public String price;
    public String amount;
    // List allahindlustega (algus, lõpp)
    //ArrayList<Allahindlus> allahindlused;


    public Toode() {}

    public Toode(String name, String picLoc, String desc, String price, String amount) {
        this.name = name;
        this.picLoc = picLoc;
        this.desc = desc;
        this.price = price;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format(
                "Toode[id=%s, name='%s', picLoc='%s', desc='%s', price='%s', amount='%s']",
                id, name, picLoc, desc, price, amount);
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPicLoc() {
        return picLoc;
    }
    public String getDesc() {
        return desc;
    }
    public String getPrice() {
        return price;
    }
    public String getAmount() {
        return amount;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPicLoc(String picLoc) {
        this.picLoc = picLoc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
