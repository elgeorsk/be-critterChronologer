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

    @ElementCollection(targetClass=PetType.class)
    private Set<PetType> PetType;

    @Nationalized
    private String name;

    @ManyToOne(targetEntity = Customer.class)
    private Customer owner;

    private LocalDate birthDate;

    @Column(length = 240)
    private String notes;

    public Set<PetType> getPetType() {
        return PetType;
    }

    public void setPetType(Set<PetType> petType) {
        PetType = petType;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
