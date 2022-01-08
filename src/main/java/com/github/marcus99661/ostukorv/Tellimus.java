package com.github.marcus99661.ostukorv;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashMap;
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

    /**
     * Tellimuse List toote koodidega
     */
    public HashMap<Integer, String> products;
    public LocalDateTime orderTime;
    public payment payment;
    // public String transport;
}
