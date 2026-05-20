package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.game.model.GameDto;

import java.util.Date;

/**
 * @author Max Escriva
 */
public class LoanDto {

    private Long id;
    private GameDto game;        // DTO, no la entidad!
    private CustomerDto customer; // DTO, no la entidad!
    private Date loanStart;
    private Date loanEnd;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public GameDto getGame() { return game; }
    public void setGame(GameDto game) { this.game = game; }

    public CustomerDto getCustomer() { return customer; }
    public void setCustomer(CustomerDto customer) { this.customer = customer; }

    public Date getLoanStart() { return loanStart; }
    public void setLoanStart(Date loanStart) { this.loanStart = loanStart; }

    public Date getLoanEnd() { return loanEnd; }
    public void setLoanEnd(Date loanEnd) { this.loanEnd = loanEnd; }
}