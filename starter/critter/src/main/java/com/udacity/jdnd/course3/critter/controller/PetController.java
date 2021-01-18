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

    //private ModelMapper modelMapper;
    private PetService petService;
    private UserService userService;

    @Autowired
    public PetController(PetService petService, UserService userService){
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer customer = null;
        if ((Long) petDTO.getOwnerId() != null) {
            customer = userService.getCustomerById(petDTO.getOwnerId());
        }
        Pet pet = convertDTO2Pet(petDTO);
        pet.setOwner(customer);
        Pet savedPet = petService.savePet(pet);
        return convertPet2DTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        //throw new UnsupportedOperationException();
        Pet pet = petService.getPet(petId);
        if (pet != null) {
            return convertPet2DTO(pet);
        }
        return null;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        //throw new UnsupportedOperationException();
        List<PetDTO> petsDTO = new ArrayList<>();
        List<Pet> pets = petService.getAllPets();

        for(Pet pet : pets){
            petsDTO.add(convertPet2DTO(pet));
        }

        return petsDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        //throw new UnsupportedOperationException();
        return petService.getAllPetsByOwner(ownerId).
                stream().
                map(this::convertPet2DTO).
                collect(Collectors.toList());
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
