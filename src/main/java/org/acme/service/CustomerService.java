package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.CustomerDTO;
import org.acme.entity.CustomerEntity;
import org.acme.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    public List<CustomerDTO> findAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        customerRepository.findAll().stream().forEach(item -> {
            customers.add(mapCustomerEntitytoDTO(item));
        });
        return customers;
    }

    public void createNewCustomer(CustomerDTO customerDTO) {
        customerRepository.persist(mapEntitytoCustomerDTO(customerDTO));
    }

    public void changeCustomer(Long id, CustomerDTO customerDTO) {
        CustomerEntity customer = customerRepository.findById(id);
        customer.setAddress(customerDTO.getAddress());
        customerRepository.persist(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO mapCustomerEntitytoDTO(CustomerEntity customer) {
        return CustomerDTO.builder().address(customer.getAddress()).age(customer.getAge()).
                email(customer.getEmail()).phone(customer.getPhone()).build();
    }

    private CustomerEntity mapEntitytoCustomerDTO(CustomerDTO customer) {
        return CustomerEntity.builder().address(customer.getAddress()).age(customer.getAge()).
                email(customer.getEmail()).phone(customer.getPhone()).build();
    }
}
