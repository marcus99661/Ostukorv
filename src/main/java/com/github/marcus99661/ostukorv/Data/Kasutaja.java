package com.github.marcus99661.ostukorv.Data;


import org.springframework.data.annotation.Id;

public class Kasutaja {

    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public Kasutaja() {}

    public Kasutaja(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Kasutaja[id=%s, first_name='%s', last_name='%s', email='%s', password='%s']",
                id, firstName, lastName, email, password);
    }

    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
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
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}