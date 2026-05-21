package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.game.model.GameDto;

import java.time.LocalDate;

/**
 * @author Max Escrivá
 */
public class LoanDto {

    private Long id;
    private GameDto game;        // DTO, no la entidad!
    private CustomerDto customer; // DTO, no la entidad!
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * @return id of LoanDto
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
    public GameDto getGame() {
        return game;
    }

    /**
     *
     * @param game new value of {@link #getGame()}
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * @return customer
     */
    public CustomerDto getCustomer() {
        return customer;
    }

    /**
     * @param customer new value of {@link #getCustomer()}
     */
    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    /**
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate new value of {@link #getStartDate()}
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate new value of {@link #getEndDate()}
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

