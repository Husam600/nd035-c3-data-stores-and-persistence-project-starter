package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.customer.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = petRepository.save(convertPetDTOToPet(petDTO));
        return convertPetToPetDTO(pet);
    }

    public List<PetDTO> getPetList() {
        return petRepository.findAll().stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    public List<PetDTO> getPetByCustomerId(Long id) {
        return petRepository
                .getPetByCustomerId(id)
                .stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    public PetDTO findPetById(Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return convertPetToPetDTO(pet.get());
        }
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerRepository.findById(petDTO.getOwnerId()).get());
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
}
