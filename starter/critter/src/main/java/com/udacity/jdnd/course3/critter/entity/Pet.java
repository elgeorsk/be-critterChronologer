package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.Set;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Nationalized
    private String name;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    private Customer owner;

    private LocalDate birthDate;

    @Column(length = 240)
    private String notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
