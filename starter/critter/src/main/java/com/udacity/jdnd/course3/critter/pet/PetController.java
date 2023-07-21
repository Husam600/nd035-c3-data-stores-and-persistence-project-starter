package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.savePet(EntityHelper.petDTOToEntity(petDTO, customerService));
        return EntityHelper.entityToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return EntityHelper.entityToPetDTO(petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> petList = petService.getAll();
        petList.forEach(pet -> petDTOList.add(EntityHelper.entityToPetDTO(pet)));
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> petList = petService.getPetsByOwner(ownerId);
        petList.forEach(pet -> petDTOList.add(EntityHelper.entityToPetDTO(pet)));
        return petDTOList;
    }
}
