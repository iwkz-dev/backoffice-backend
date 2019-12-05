package it.iwkz.api.controllers;

import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.Income;
import it.iwkz.api.models.IncomeType;
import it.iwkz.api.payloads.EntityResponse;
import it.iwkz.api.payloads.ListResponse;
import it.iwkz.api.payloads.income.AddIncomeRequest;
import it.iwkz.api.payloads.income.TotalIncomeResponse;
import it.iwkz.api.repositories.IncomeTypeRepository;
import it.iwkz.api.services.IncomeService;
import it.iwkz.api.utils.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private IncomeService incomeService;

    private static final Logger logger = LoggerFactory.getLogger(IncomeController.class);

    @GetMapping("/types")
    public ListResponse<IncomeType> getIncomeTypes() {
        return new ListResponse<>(incomeTypeRepository.findAll());
    }

    @GetMapping("/types/{id}")
    public EntityResponse<IncomeType> getIncomeType(@PathVariable long id) {
        IncomeType incomeType = incomeTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IncomeType", "getIncomeType", id));

        return new EntityResponse<>(incomeType);
    }

    @GetMapping("/{month}/{year}")
    public ListResponse<Income> getIncomesByMonthYear(@PathVariable int month, @PathVariable int year,
                                                      @RequestParam(value ="page", defaultValue = AppConst.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "pageSize", defaultValue = AppConst.DEFAULT_PAGE_SIZE) int pageSize ) {

        return incomeService.getAllIncomesByMonthYear(month, year, page, pageSize);
    }

    @GetMapping("/total/{month}/{year}")
    public EntityResponse<TotalIncomeResponse> getTotalIncomeByMonthYear(@PathVariable int month, @PathVariable int year) {
        return new EntityResponse<>(incomeService.getTotalIncomes(month, year));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addIncome(@Valid @RequestBody AddIncomeRequest incomeRequest) {
        incomeService.addIncome(incomeRequest);
    }
}
