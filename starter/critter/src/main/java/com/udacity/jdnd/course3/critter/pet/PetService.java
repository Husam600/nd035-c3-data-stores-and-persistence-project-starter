package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.common.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Pet savePet(Pet pet) throws ObjectNotFoundException {
        pet.getCustomer().getPets().add(pet);
        return petRepository.save(pet);
    }

    public List<Pet> getPetList() {
        return petRepository.findAll();
    }

    public List<Pet> getPetByCustomerId(Long id) {
        return petRepository.getPetByCustomerId(id);
    }

    public Pet findPetById(Long id) throws ObjectNotFoundException {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isEmpty()) {
            throw new ObjectNotFoundException("The pet with id:" + id + " not found");
        } else {
            return pet.get();
        }
    }
}
