package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    /**
     * Listado filtrado (Specifications)
     */

    Page<Loan> findAll(Specification<Loan> specification, Pageable pageable);

    /**
     * Validar que el juego no esté prestado en un rango de fechas.
     * @return Lista {@link List} con juegos solapados.
     */


    /* Primero se ejecuta la Query y después, la lista */
    @Query("""
        SELECT l FROM Loan l
        WHERE l.game.id = :gameId
          AND l.id <> :excludeId /* Esta línea es para ignorar el propio ID que buscamos en el solapamiento */
          AND l.startDate <= :endDate
          AND l.endDate   >= :startDate
    """)
    List<Loan> findOverlappingByGame(@Param("gameId") Long gameId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("excludeId") Long excludeId);


    /**
     * Validar que el cliente no tiene 2 préstamos en ese rango de fechas.
     * @return Lista {@link List} de clientes solapados
     */

    @Query("""
        SELECT l FROM Loan l
        WHERE l.customer.id = :customerId
          AND l.id <> :excludeId /* Esta línea es para ignorar el propio ID que buscamos en el solapamiento */
          AND l.startDate <= :endDate
          AND l.endDate   >= :startDate
    """)
    List<Loan> findOverlappingByClient(@Param("customerId") Long customerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("excludeId") Long excludeId);
}
