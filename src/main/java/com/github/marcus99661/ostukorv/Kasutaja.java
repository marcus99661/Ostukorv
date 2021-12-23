package com.github.marcus99661.ostukorv;


import org.springframework.data.annotation.Id;

public class Kasutaja {

    @Id
    public String id;

    public String name;
    public String email;
    public String password;

    public Kasutaja() {}

    public Kasutaja(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Kasutaja[id=%s, name='%s', email='%s', password='%s']",
                id, name, email, password);
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String setEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}