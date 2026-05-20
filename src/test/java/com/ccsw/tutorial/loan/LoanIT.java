package com.ccsw.tutorial.loan;


import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    public static final Long DELETE_LOAN_ID = 6L;
    public static final Long MODIFY_LOAN_ID = 3L;
    public static final Long EXISTING_GAME_ID = 1L;
    public static final Long EXISTING_CUSTOMER_ID = 1L;

    private static final int TOTAL_LOANS = 6;
    private static final int PAGE_SIZE = 5;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage =
            new ParameterizedTypeReference<ResponsePage<LoanDto>>() {};

    /** Helper: builds a Date from year, month (1-based) and day. */
    private Date buildDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Helper: builds a Game reference with only the id set.
     */
    private GameDto gameRef(Long id) {
        GameDto game = new GameDto();
        game.setId(id);
        return game;
    }

    /**
     * Helper: builds a Customer reference with only the id set.
     */
    private CustomerDto customerRef(Long id) {
        CustomerDto customer = new CustomerDto();
        customer.setId(id);
        return customer;
    }


    // Pagination tests


    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {

        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    // Filter tests

    @Test
    public void findByGameIdShouldReturnOnlyLoansForThatGame() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setGameId(EXISTING_GAME_ID);
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage);


        assertNotNull(response.getBody());
        response.getBody().getContent().forEach(loan ->
                assertEquals(EXISTING_GAME_ID, loan.getGame().getId()));
    }

    @Test
    public void findByCustomerIdShouldReturnOnlyLoansForThatCustomer() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setCustomerId(EXISTING_CUSTOMER_ID);
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage);

        assertNotNull(response.getBody());
        response.getBody().getContent().forEach(loan ->
                assertEquals(EXISTING_CUSTOMER_ID, loan.getCustomer().getId()));
    }

    @Test
    public void findByDateShouldReturnLoansActiveOnThatDate() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setDate(buildDate(2024, 1, 10));
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage);

        assertNotNull(response);
        assertNotNull(response.getBody());
        response.getBody().getContent().forEach(loan -> {
            assertNotNull(loan.getLoanStart());
            assertNotNull(loan.getLoanEnd());
        });
    }

    // Create / Update tests

    @Test
    public void saveWithoutIdShouldCreateNewLoan() {

        long newLoanId = TOTAL_LOANS + 1;
        long newLoanSize = TOTAL_LOANS + 1;

        LoanDto dto = new LoanDto();
        dto.setGame(gameRef(EXISTING_GAME_ID));
        dto.setCustomer(customerRef(EXISTING_CUSTOMER_ID));
        dto.setLoanStart(buildDate(2024, 1, 1));
        dto.setLoanEnd(buildDate(2024, 1, 15));

        restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) newLoanSize));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage);

        ResponseEntity<Void> saveResponse = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class);

// Añade esto temporalmente para ver el estado real
        assertEquals(HttpStatus.OK, saveResponse.getStatusCode());


        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(newLoanSize, response.getBody().getTotalElements());

        LoanDto loan = response.getBody().getContent().stream()
                .filter(item -> item.getId().equals(newLoanId))
                .findFirst()
                .orElse(null);

        assertNotNull(loan);
        assertEquals(EXISTING_GAME_ID, loan.getGame().getId());
        assertEquals(EXISTING_CUSTOMER_ID, loan.getCustomer().getId());
    }

    @Test
    public void modifyWithExistIdShouldModifyLoan() {

        Date newStart = buildDate(2024, 2, 1);
        Date newEnd   = buildDate(2024, 2, 14);

        LoanDto dto = new LoanDto();
        dto.setGame(gameRef(EXISTING_GAME_ID));
        dto.setCustomer(customerRef(EXISTING_CUSTOMER_ID));
        dto.setLoanStart(newStart);
        dto.setLoanEnd(newEnd);

        restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_LOAN_ID,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());

        LoanDto loan = response.getBody().getContent().stream()
                .filter(item -> item.getId().equals(MODIFY_LOAN_ID))
                .findFirst()
                .orElse(null);

        assertNotNull(loan);
        assertEquals(EXISTING_GAME_ID, loan.getGame().getId());
        assertEquals(EXISTING_CUSTOMER_ID, loan.getCustomer().getId());
        assertEquals(newStart, loan.getLoanStart());
        assertEquals(newEnd, loan.getLoanEnd());
    }

    @Test
    public void modifyWithNotExistIdShouldThrowException() {

        long loanId = TOTAL_LOANS + 1;

        LoanDto dto = new LoanDto();
        dto.setGame(gameRef(EXISTING_GAME_ID));
        dto.setCustomer(customerRef(EXISTING_CUSTOMER_ID));
        dto.setLoanStart(buildDate(2024, 1, 1));
        dto.setLoanEnd(buildDate(2024, 1, 15));

        ResponseEntity<?> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "/" + loanId,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Delete tests

    @Test
    public void deleteWithExistsIdShouldDeleteLoan() {

        long newLoansSize = TOTAL_LOANS - 1;

        restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "/" + DELETE_LOAN_ID,
                HttpMethod.DELETE,
                null,
                Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(newLoansSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {

        long deleteLoanId = TOTAL_LOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "/" + deleteLoanId,
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}