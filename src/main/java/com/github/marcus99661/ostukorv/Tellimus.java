package com.github.marcus99661.ostukorv;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Tellimus {

    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String email;
    public String phoneNum;
    public String city;
    public String address;
    public String postalCode;
    public List<Toode> products;
    public LocalDateTime orderTime;
    // public String payment;
    // public String transport;
}
