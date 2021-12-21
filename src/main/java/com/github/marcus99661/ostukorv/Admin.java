package com.github.marcus99661.ostukorv;

import org.springframework.data.annotation.Id;

public class Admin {
    @Id
    public String id;

    public String name;
    public String password;

    public Admin() {}

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, name='%s', password='%s']",
                id, name, password);
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
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
    public void setPassword(String password) {
        this.password = password;
    }
}
