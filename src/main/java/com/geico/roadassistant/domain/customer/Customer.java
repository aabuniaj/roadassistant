package com.geico.roadassistant.domain.customer;

import lombok.Data;

@Data
public class Customer {
    String email;
    String fname;
    String lname;

    public Customer(String email, String fname, String lname) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
    }
}
