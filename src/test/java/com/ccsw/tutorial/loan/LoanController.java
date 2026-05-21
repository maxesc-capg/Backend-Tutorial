package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RestController
@RequestMapping(path = "/loan")
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping
    public Page<LoanDto> findAll(@RequestParam(required = false) Long gameId, @RequestParam(required = false) Long customerId, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Pageable pageable) {
        return loanService.findAll(gameId, customerId, date, pageable);
    }

    /* ResponseEntity es una clase de Spring que representa la respuesta HTTP completa */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LoanSaveDto loan) { /* Comodín porque no sabemos qué nos devolverá */
        loan.setId(id);
        try {
            loanService.save(loan);
            return ResponseEntity.ok().build(); /* Si todo va bien, 200 OK sin body */
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); /* Si no va bien, mensaje de error */
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LoanSaveDto loan) { /* Comodín porque no sabemos qué nos devolverá */
        try {
            loanService.save(loan);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        loanService.delete(id);
    }
}
