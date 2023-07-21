package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.customer.Customer;
import com.udacity.jdnd.course3.critter.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet) {
        Pet savedPet =  petRepository.save(pet);
        Customer customer = savedPet.getCustomer();
        List<Pet> customerPets = customer.getPets();
        if(customerPets == null){
            customerPets = new ArrayList<>();
        }
        customerPets.add(savedPet);
        customer.setPets(customerPets);
        customerRepository.save(customer);
        return savedPet;
    }

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(Long id) {
        return petRepository.getPetByCustomerId(id);
    }

    public Pet findById(Long id) {
        Optional<Pet> petOptional = petRepository.findById(id);
        return petOptional.orElse(null);
    }
}
