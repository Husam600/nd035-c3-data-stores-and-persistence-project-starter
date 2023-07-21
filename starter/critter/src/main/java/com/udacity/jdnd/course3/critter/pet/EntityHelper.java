package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.customer.Customer;
import com.udacity.jdnd.course3.critter.customer.CustomerService;
import org.springframework.beans.BeanUtils;

public class EntityHelper {

    public static Pet petDTOToEntity(PetDTO petDTO, CustomerService customerService) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet, "customerId");
        Long userId = petDTO.getCustomerId();
        Customer customer = customerService.getCustomerById(userId);
        pet.setCustomer(customer);
        return pet;
    }

    public static PetDTO entityToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setCustomerId(pet.getCustomer().getId());
        return petDTO;
    }
}
