package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ccsw
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private long SEQUENCE = 1;
    private Map<Long, CustomerDto> customers = new HashMap<Long, CustomerDto>();

    /**
     * {@inheritDoc}
     */
    public List<CustomerDto> findAll() {

        return new ArrayList<CustomerDto>(this.customers.values());
    }

    /**
     * {@inheritDoc}
     */
    public void save(Long id, CustomerDto dto) {

        CustomerDto Customer;

        if (id == null) {
            Customer = new CustomerDto();
            Customer.setId(this.SEQUENCE++);
            this.customers.put(Customer.getId(), Customer);
        } else {
            Customer = this.customers.get(id);
        }

        Customer.setName(dto.getName());
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Long id) {

        this.customers.remove(id);
    }

}