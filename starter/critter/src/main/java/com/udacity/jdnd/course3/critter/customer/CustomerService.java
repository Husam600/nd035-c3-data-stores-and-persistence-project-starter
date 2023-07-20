package com.udacity.jdnd.course3.critter.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.save(convertCustomerDTOToCustomer(customerDTO));
        return convertCustomerToCustomerDTO(customer);
    }

    public List<CustomerDTO> getCustomerList() {
        return customerRepository
                .findAll()
                .stream()
                .map(this::convertCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getOwnerByPet(Long petId) {
        Optional<Customer> customer = customerRepository.findByPetsId(petId);
        if (customer.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return convertCustomerToCustomerDTO(customer.get());
        }
    }

    public Customer getCustomerById(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return customer.get();
        }
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Pet> petList = customer.getPets();
        List<Long> petIds = new ArrayList<>();
        for (Pet pet : petList) {
            petIds.add(pet.getId());
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

}
