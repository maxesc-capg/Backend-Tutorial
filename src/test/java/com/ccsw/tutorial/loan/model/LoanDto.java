package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.game.model.Game;

import java.util.Date;

/**
 * @author Max Escriva
 */
public class LoanDto {

    private Long id;
    private Game game;
    private Customer customer;
    private Date loanStart;
    private Date loanEnd;

    /**
     * @return id
     */

    public Long getId() {
        return id;
    }

    /**
     * @param id new value of {@link #getId()}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     *
     * @param game new value of {@link #getGame()}
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     *
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer new value of {@link #getCustomer()}
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     *
     * @return loanStart
     */
    public Date getLoanStart() {
        return loanStart;
    }

    /**
     *
     * @param loanStart new value of {@link #getLoanStart()}
     */
    public void setLoanStart(Date loanStart) {
        this.loanStart = loanStart;
    }

    /**
     *
     * @return getLoanEnd
     */
    public Date getLoanEnd() {
        return loanEnd;
    }

    /**
     *
     * @param loanEnd new value of {@link #getLoanEnd()}
     */
    public void setLoanEnd(Date loanEnd) {
        this.loanEnd = loanEnd;
    }
}
