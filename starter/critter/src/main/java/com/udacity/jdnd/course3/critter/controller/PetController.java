package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private UserService userService;

    @Autowired
    public PetController(PetService petService, UserService userService){
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer customer = userService.getCustomerById(petDTO.getOwnerId());
        Pet pet = convertDTO2Pet(petDTO);
        return convertPet2DTO(petService.savePet(pet, customer));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPet2DTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petsDTO = new ArrayList<>();
        List<Pet> pets = petService.getAllPets();

        for(Pet pet : pets){
            PetDTO petDTO = convertPet2DTO(pet);
            petsDTO.add(petDTO);
        }

        return petsDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petsDTO = new ArrayList<>();
        List<Pet> pets = petService.getAllPetsByOwner(ownerId);

        for(Pet pet : pets){
            PetDTO petDTO = convertPet2DTO(pet);
            petsDTO.add(petDTO);
        }
        return petsDTO;
    }

    private Pet convertDTO2Pet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    private PetDTO convertPet2DTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }
}
