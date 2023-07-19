package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.common.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.customer.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        try {
            Pet pet = petService.savePet(convertPetDTOToPet(petDTO));
            return convertPetToPetDTO(pet);
        } catch (ObjectNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            return convertPetToPetDTO(petService.findPetById(petId));
        } catch (ObjectNotFoundException e) {
        System.out.println(e.getMessage());
        return null;
    }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.getPetList();
        return petList.stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.getPetByCustomerId(ownerId);
        return petList.stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) throws ObjectNotFoundException {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerService.getCustomerById(petDTO.getOwnerId()));
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
}
