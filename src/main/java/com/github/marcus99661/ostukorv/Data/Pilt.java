package com.github.marcus99661.ostukorv.Data;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Pilt")
public class Pilt {

    @Id
    private String id;

    private String name;

    private Binary image;

    private String hash;

    public Pilt(String name, Binary image, String hash) {
        this.name = name;
        this.image = image;
        this.hash = hash;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Binary getImage() {
        return image;
    }
    public String getHash() {
        return hash;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(Binary image) {
        this.image = image;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
}
