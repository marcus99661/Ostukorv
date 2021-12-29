package com.github.marcus99661.ostukorv;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Toode {
    @Id
    public String id;

    /**
     * TULEB PANNA CONSTRUCTORISSE, TOSTRING, GETTER JA SETTER
     */
    public String kood;
    public String name;
    public String desc;
    public double price;
    public String amount;
    // ArrayList täidetud Image ID-ga (service.getById)
    ArrayList<String> image;
    // Toode tägid, mille järgi saab otsida ja soovitada
    // ArrayList<String> tags;
    // List reviewedest (kasutaja, hinnang, notes, LocalDateTime kell)
    // ArrayList<Review> reviewed;
    // List allahindlustega (algus, lõpp)
    // ArrayList<Allahindlus> allahindlused;


    public Toode() {}

    public Toode(String kood, String name, String desc, double price, String amount, ArrayList<String> image) {
        this.kood = kood;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.amount = amount;
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format(
                "Toode[kood=%s, name='%s', desc='%s', price='%s', amount='%s', images='%s']",
                kood, name, desc, price, amount, image.toString());
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }
    public double getPrice() {
        return price;
    }
    public String getAmount() {
        return amount;
    }
    public String getKood() {
        return kood;
    }
    public ArrayList<String> getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public void setKood(String kood) {
        this.kood = kood;
    }
    public void setImage(ArrayList<String> imageId) {
        this.image = imageId;
    }
}
