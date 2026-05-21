package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.customer.CustomerRepository;
import com.ccsw.tutorial.game.GameRepository;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSaveDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
class LoanServiceImpl implements LoanService {

    /* Inyección Beans */
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ModelMapper modelMapper;


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<LoanDto> findAll(Long gameId, Long customerId, LocalDate date, Pageable pageable) {
        Specification<Loan> specification = Specification.
                where(LoanSpecification.hasGame(gameId))
                .and(LoanSpecification.hasCustomer(customerId))
                .and(LoanSpecification.containsDate(date));

        return loanRepository.findAll(specification,pageable).map(loan -> this.toDto(loan)); /* Utiliza el método "toDto" de esta misma clase como función */
    }

    private LoanDto toDto(Loan loan) {
        return modelMapper.map(loan, LoanDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(LoanSaveDto loan) throws Exception {
        /* endDate no puede ser anterior a startDate */
        if (loan.getEndDate().isBefore(loan.getStartDate())) throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio.");

        /* Periodo máximo de préstamo es de 14 días */
        long days = ChronoUnit.DAYS.between(loan.getStartDate(), loan.getEndDate());
        if (days > 14) throw new Exception("No se pueden prestar juegos más de 14 días.");

        Long excludeId;
        if (loan.getId() != null) {
            excludeId = loan.getId(); /* Si el préstamo ya tiene ID, significa que estás editando un préstamo ya existente -> no valida ID*/
        } else {
            excludeId = -1L; /* Si el ID es null, significa que el préstamo es nuevo, así que el ID es "-1L" -> ninguno lo tiene y la validación no excluye nada */
        }


        /* El juego no puede estar prestado a otro cliente en ese período */
        List<Loan> gameConflicts = loanRepository.findOverlappingByGame(loan.getGameId(), loan.getStartDate(), loan.getEndDate(), excludeId);
        if (!gameConflicts.isEmpty()) throw new Exception("El juego ya está prestado en ese periodo.");

        /* El cliente no puede tener más de 2 juegos prestados en ese rango de fechas */
        List<Loan> clientLoans = loanRepository.findOverlappingByClient(loan.getCustomerId(), loan.getStartDate(), loan.getEndDate(), excludeId);
        if(clientLoans.size() > 2) throw new Exception("El cliente ya tiene dos préstamos activos en este periodo de tiempo."); /* Si la lista de juegos prestados es más que 2 */

        /* Si nada de lo de arriba se da... */

        Loan newLoan;
        if (loan.getId() != null) {
            newLoan = loanRepository.findById(loan.getId()).orElseThrow(); /* Validamos que el ID no esté ya cogido */
        } else {
            newLoan = new Loan();
        }

        newLoan.setGame(gameRepository.findById(loan.getGameId()).orElseThrow());
        newLoan.setCustomer(customerRepository.findById(loan.getCustomerId()).orElseThrow());
        newLoan.setStartDate(loan.getStartDate());
        newLoan.setEndDate(loan.getEndDate());

        loanRepository.save(newLoan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        loanRepository.deleteById(id);
    }
}
