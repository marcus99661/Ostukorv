package com.github.marcus99661.ostukorv.Data;

import org.bson.types.Binary;
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


    //pilt, BASE64 encoded String, esimene pilt image listist
    public String thumbnail;

    //ArrayList täidetud Image hashidega (service.findByHash)
    public ArrayList<String> image;
    public String category;

    //Toote tägid, mille järgi saab otsida ja soovitada
    // public ArrayList<String> tags;

    //List reviewdest (kasutaja, hinnang, sisu, LocalDateTime kell)
    // public ArrayList<Review> reviews;


    //List allahindlustega (algus, lõpp, protsent)
    // public ArrayList<Allahindlus> discounts;


    /**
     * Ostukorvis kasutuseks
     */
    public String tooteKogus;
    public String koguseHind;

    public Toode() {}

    public Toode(String kood, String name, String desc, double price, String amount, ArrayList<String> image, String category) {
        this.kood = kood;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.amount = amount;
        this.image = image;
        this.category = category;
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
    public String getCategory() {
        return category;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public String getTooteKogus() {
        return tooteKogus;
    }
    public String getKoguseHind() {
        return koguseHind;
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
    public void setCategory(String category) {
        this.category = category;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public void setTooteKogus(String tooteKogus) {
        this.tooteKogus = tooteKogus;
    }
    public void setKoguseHind(String koguseHind) {
        this.koguseHind = koguseHind;
    }
}
