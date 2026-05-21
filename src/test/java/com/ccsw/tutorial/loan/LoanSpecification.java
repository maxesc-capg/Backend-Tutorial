package com.ccsw.tutorial.loan;


import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * @author Max Escrivá
 */
public class LoanSpecification {

    /**
     *
     * @param gameId id of the game we are looking for
     * @return equality predicate
     */
    public static Specification<Loan> hasGame(Long gameId) {
        return ((root /* Una manera de decir "from loan" */, query, criteriaBuilder/*WHERE*/) ->
        {
            if (gameId == null) {
                return null; /* Si no pasan gameId o es nulo, devuelve null, el filtro se ignora, no añade nada al WHERE */
            } else { /* Si lo pasan, genera "WHERE game_id = :gameId" */
                /* " :gameId " en una query es como los " ? ", parámetros a pasar */
                return criteriaBuilder.equal(root.get("game")/*tabla*/.get("id")/*campo*/, gameId/*parámetro*/);
            }
        });
    }

    /**
     *
     * @param customerId id of the customer we are looking for
     * @return equality predicate
     */
    public static Specification<Loan> hasCustomer(Long customerId) {
        return ((root, query, cb) ->
        {
            if (customerId == null) {
                return null;
            } else {
                return cb.equal(root.get("customer").get("id"), customerId);
            }
        });
    }

    /**
     *
     * @param date date that must be between startDate and endDate
     * @return
     */

    /* La fecha elegida debe estar DENTRO del rango startDate-endDate del préstamo */
    public static Specification<Loan> containsDate(LocalDate date) {
        return (root, query, cb) ->
        {
            if (date == null) {
                return null;
            } else {
                return cb.and( /* "and" junta dos condiciones que DEBEN ser ciertas: (como &&) */
                        cb.lessThanOrEqualTo(root.get("startDate"), date), /* la fecha dada tiene que ser MENOR o IGUAL que la fecha de inicio del préstamo */
                        /* es como " startDate <= date " */
                        cb.greaterThanOrEqualTo(root.get("endDate"), date) /* la fecha dada tiene que ser MAYOR o IGUAL que la fecha final del préstamo */
                        /* es como " endDate >= date " */
                );
            }
        };
    }
}