package br.com.sossp.sosspapp.models;

import lombok.Data;

@Data
public class Address {

    private Long zipcode;
    private String addressName;
    private String neighborhood;
    private String city;
    private State state;
    private int number;
    private String complement;

}
