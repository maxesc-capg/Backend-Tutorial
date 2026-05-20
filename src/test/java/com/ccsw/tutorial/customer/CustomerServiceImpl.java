package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> findAll() {
        return (List<Customer>) this.customerRepository.findAll();
    }

    @Override
    public List<Customer> getCustomers(String name) {
        if (name != null && !name.isEmpty()) {
            return customerRepository.findByNameIgnoreCase(name);
        }
        return (List<Customer>) customerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, CustomerDto dto) {

        Customer Customer;

        if (id == null) {
            Customer = new Customer();
        } else {
            Customer = this.customerRepository.findById(id).orElse(null);
        }

        Customer.setName(dto.getName());

        this.customerRepository.save(Customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.customerRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }

        this.customerRepository.deleteById(id);
    }

}