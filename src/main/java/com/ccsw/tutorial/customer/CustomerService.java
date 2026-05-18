package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.CustomerDto;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface CustomerService {

    /**
     * Método para recuperar todas las categorías
     *
     * @return {@link List} de {@link Customer}
     */
    List<CustomerDto> findAll();

    /**
     * Método para crear o actualizar una categoría
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, CustomerDto dto);

    /**
     * Método para borrar una categoría
     *
     * @param id PK de la entidad
     */
    void delete(Long id);

}