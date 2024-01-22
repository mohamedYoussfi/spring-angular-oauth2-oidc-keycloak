package net.youssfi.customerservice.web;

import net.youssfi.customerservice.entities.Customer;
import net.youssfi.customerservice.repo.CustomerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
    private CustomerRepository customerRepository;

    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @GetMapping("/customers")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerRepository.findById(id).get();
    }

    @GetMapping("/mySession")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }
}
