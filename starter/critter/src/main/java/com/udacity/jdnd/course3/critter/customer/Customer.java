package com.udacity.jdnd.course3.critter.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer extends User {

    private String phoneNumber;

    private String notes;

    @OneToMany(mappedBy="customer", fetch= FetchType.EAGER)
    private List<Pet> pets = new ArrayList<>();

    public Customer(String phoneNumber, String notes, List<Pet> pets) {
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

    public Customer() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
