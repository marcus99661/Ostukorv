package com.github.marcus99661.ostukorv.Data;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    public String kood;
    public boolean done;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNum;
    public String city;
    public String address;
    public String country;
    public String county;
    public String zipCode;
    public double price;
    public String payment;

    /**
     * Tellimuse List toote koodidega
     */
    public HashMap<String, Integer> products;
    public LocalDateTime orderTime;

    // public String transport;

    /**
     * Admini lehe jaoks
     */

    public String tempDate;


    public Tellimus(String firstName, String lastName, String email, String phoneNum, String city, String address, String zipCode, HashMap<String, Integer> products, String payment, String country, String county, double price) {
        this.kood = UUID.randomUUID().toString();
        this.done = false;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
        this.products = products;
        this.orderTime = LocalDateTime.now();
        this.payment = payment.toUpperCase();
        this.country = country;
        this.county = county;
        this.price = price;
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
                ", price=" + price +
                ", products=" + products +
                ", orderTime=" + orderTime +
                ", payment=" + payment +
                '}';
    }

    public String getKood() {return kood;}
    public String getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getPhoneNum() {return phoneNum;}
    public String getCity() {return city;}
    public String getAddress() {return address;}
    public String getCountry() {return country;}
    public String getCounty() {return county;}
    public String getZipCode() {return zipCode;}
    public double getPrice() {return price;}
    public HashMap<String, Integer> getProducts() {return products;}
    public LocalDateTime getOrderTime() {return orderTime;}
    public String getPayment() {return payment;}

    public void setKood(String kood) {this.kood = kood;}
    public void setId(String id) {this.id = id;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setEmail(String email) {this.email = email;}
    public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}
    public void setCity(String city) {this.city = city;}
    public void setAddress(String address) {this.address = address;}
    public void setCountry(String country) {this.country = country;}
    public void setCounty(String county) {this.county = county;}
    public void setZipCode(String zipCode) {this.zipCode = zipCode;}
    public void setPrice(double price) {this.price = price;}
    public void setProducts(HashMap<String, Integer> products) {this.products = products;}
    public void setOrderTime(LocalDateTime orderTime) {this.orderTime = orderTime;}
    public void setPayment(String payment) {this.payment = payment;}
}
