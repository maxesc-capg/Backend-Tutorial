package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.customer.CustomerRepository;
import com.ccsw.tutorial.game.GameRepository;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Max Escriva
 */
@Service
@Transactional
class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CustomerRepository customerRepository;

    /**
     * Método para recuperar un listado paginado y filtrado de {@link Loan}
     *
     * @param dto dto de búsqueda (con filtros opcionales y paginación)
     * @return {@link Page} de {@link Loan}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {

        return this.loanRepository.findWithFilters(
                dto.getGameId(),
                dto.getCustomerId(),
                dto.getDate(),
                dto.getPageable().getPageable()
        );
    }

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param id   PK de la entidad (null si es creación)
     * @param data datos del préstamo
     * @throws Exception si el id no existe al modificar
     */
    @Override
    public void save(Long id, LoanDto data) throws Exception {

        Loan loan;

        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.loanRepository.findById(id).orElse(null);
            if (loan == null) {
                throw new Exception("Loan not found with id: " + id);
            }
        }

        // Buscamos las entidades reales por ID para no depender de modelMapper
        loan.setGame(this.gameRepository.findById(data.getGame().getId()).orElse(null));
        loan.setCustomer(this.customerRepository.findById(data.getCustomer().getId()).orElse(null));
        loan.setLoanStart(data.getLoanStart());
        loan.setLoanEnd(data.getLoanEnd());

        this.loanRepository.save(loan);
    }

    /**
     * Método para borrar un {@link Loan}
     *
     * @param id PK de la entidad
     * @throws Exception si no encuentra el préstamo
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.loanRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }
}