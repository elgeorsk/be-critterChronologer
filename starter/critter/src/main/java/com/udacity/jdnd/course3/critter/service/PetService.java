package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PetService(PetRepository petRepository, CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public Pet savePet(Pet pet, Customer customer){
        pet.setOwner(customer);
        Pet savedPet = petRepository.save(pet);
        customer = savedPet.getOwner();
        customer.addPet(savedPet);
        customerRepository.save(customer);
        return savedPet;

    }

    public Pet getPet(Long petId){
        return petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getAllPetsByOwner(Long ownerId){
        return petRepository.findPetsByOwnerId(ownerId);
    }
}
