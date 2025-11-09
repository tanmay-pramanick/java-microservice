package com.java.microservice.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="USERS")
public class User implements Serializable {

    @Id
    @Column(name = "UUID")
    private String uuid;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column
    private String color;

    public String getUuid() {return uuid;}

    public void setUuid(String uuid) {this.uuid = uuid;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getColor() {return color;}

    public void setColor(String color) {this.color = color;}
}
