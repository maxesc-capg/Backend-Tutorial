package com.ccsw.tutorial.loan.model;

import java.time.LocalDate;

/**
 * @author Max Escrivá
 */

/* IMPORTANTE!!!! ESTO ES PARA CREAR Y EDITAR PRÉSTAMOS QUE VENGAN DEL FRONT!!!! */
public class LoanSaveDto {
    private Long gameId;
    private Long customerId;
    private LocalDate startDate;
    private LocalDate endDate;

    // getters y setters

    /**
     *
     * @return gameId
     */
    public Long getGameId() {
        return gameId;
    }

    /**
     *
     * @param gameId new value of {@link #getGameId()}
     */
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     *
     * @return customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId new value of {@link #getCustomerId()}
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate new value of {@link #getStartDate()}
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate new value of {@link #getEndDate()}
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}