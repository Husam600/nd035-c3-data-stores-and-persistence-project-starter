package com.udacity.jdnd.course3.critter.customer;

import com.udacity.jdnd.course3.critter.common.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(Long petId) throws ObjectNotFoundException {
        Optional<Customer> customer = customerRepository.findByPetsId(petId);
        if (customer.isEmpty()) {
            throw new ObjectNotFoundException("No customer found for the id:" + petId);
        } else {
            return customer.get();
        }
    }

    public Customer getCustomerById(long customerId) throws ObjectNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new ObjectNotFoundException("No customer found for the id:" + customerId);
        } else {
            return customer.get();
        }
    }

}
