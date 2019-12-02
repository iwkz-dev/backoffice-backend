package it.iwkz.api.controllers;

import it.iwkz.api.exceptions.BadRequestException;
import it.iwkz.api.models.Income;
import it.iwkz.api.models.IncomeType;
import it.iwkz.api.payloads.ApiResponse;
import it.iwkz.api.payloads.PagedResponse;
import it.iwkz.api.payloads.income.AddIncomeRequest;
import it.iwkz.api.payloads.income.TotalIncomeResponse;
import it.iwkz.api.repositories.IncomeTypeRepository;
import it.iwkz.api.services.IncomeService;
import it.iwkz.api.utils.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private IncomeService incomeService;

    private static final Logger logger = LoggerFactory.getLogger(IncomeController.class);

    @GetMapping("/types")
    public List<IncomeType> getIncomeTypes() {
        return incomeTypeRepository.findAll();
    }

    @GetMapping("/types/{id}")
    public IncomeType getIncomeType(@PathVariable long id) {
        return incomeTypeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("no incomeType found with id " + id));
    }

    @GetMapping("/{month}/{year}")
    public PagedResponse getFinanceCurrentMonth(@PathVariable int month, @PathVariable int year,
                                                @RequestParam(value ="page", defaultValue = AppConst.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "pageSize", defaultValue = AppConst.DEFAULT_PAGE_SIZE) int pageSize ) {

        return incomeService.getAllIncomesByMonthYear(month, year, page, pageSize);
    }

    @GetMapping("/total/{month}/{year}")
    public TotalIncomeResponse getTotalIncomeByMonthYear(@PathVariable int month, @PathVariable int year) {
        return incomeService.getTotalIncomes(month, year);
    }

    @PostMapping
    public ResponseEntity addIncome(@Valid @RequestBody AddIncomeRequest incomeRequest) {
        Income income = incomeService.addIncome(incomeRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{incomeId}")
                .buildAndExpand(income.getId()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "income added"));
    }
}
