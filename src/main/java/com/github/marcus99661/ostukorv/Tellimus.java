package com.github.marcus99661.ostukorv;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Tellimus {

    public enum Payment {
        PAYPAL,
        SEB,
        SWEDBANK,
        LHV;

        public static Payment parse(String text) {
            if (text.equalsIgnoreCase("paypal")) {
                return Payment.PAYPAL;
            } else if (text.equalsIgnoreCase("seb")) {
                return Payment.SEB;
            } else if (text.equalsIgnoreCase("swedbank")) {
                return Payment.SWEDBANK;
            } else if (text.equalsIgnoreCase("lhv")) {
                return Payment.LHV;
            } else {
                return null;
            }
        }
    }


    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String email;
    public String phoneNum;
    public String city;
    public String address;
    public String country;
    public String county;
    public String zipCode;

    /**
     * Tellimuse List toote koodidega
     */
    public HashMap<String, Integer> products;
    public LocalDateTime orderTime;
    public Payment payment;
    // public String transport;


    public Tellimus(String firstName, String lastName, String email, String phoneNum, String city, String address, String zipCode, HashMap<String, Integer> products, String payment, String country, String county) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
        this.products = products;
        this.orderTime = LocalDateTime.now();
        this.payment = Payment.parse(payment);
        this.country = country;
        this.county = county;
    }

    @Override
    public String toString() {
        return "Tellimus{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", county='" + county + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", products=" + products +
                ", orderTime=" + orderTime +
                ", payment=" + payment +
                '}';
    }
}
