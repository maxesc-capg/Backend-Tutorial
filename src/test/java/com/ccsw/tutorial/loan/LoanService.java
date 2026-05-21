package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * @author Max Escrivá
 */

/* Service -> "la lógica de negocio" (métodos -> operaciones de guardado, borrado, y listado) */
public interface LoanService {

    /* Aquí especificas cuáles son, qué harán. Es una INTERFAZ, no tienen body! */

    /**
     * Método para recuperar todos los préstamos de forma paginada
     * @param gameId id (PK) del juego prestado
     * @param customerId id (PK) del cliente
     * @param date fecha
     * @param pageable forma paginada
     * @return {@link Page} de {@link LoanDto}
     */
    Page<LoanDto> findAll(Long gameId, Long customerId, LocalDate date, Pageable pageable); /* Dto -> lo que sale del service NO DEBE SABER de la entidad. */

    /**
     *
     * @param loan datos completos la entidad (juego, cliente, fecha inicio, fecha final)
     * @throws Exception si endDate es menor que startDate
     */
    void save(LoanSaveDto loan) throws Exception; /* Lanzará excepción si endDate < startDate */

    /**
     *
     * @param id id (PK) del préstamo que borraremos
     */
    void delete(Long id);

}
