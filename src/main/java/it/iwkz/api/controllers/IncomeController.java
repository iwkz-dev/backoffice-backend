package it.iwkz.api.controllers;

import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.Income;
import it.iwkz.api.models.IncomeType;
import it.iwkz.api.payloads.EntityResponse;
import it.iwkz.api.payloads.ListResponse;
import it.iwkz.api.payloads.income.AddIncomeRequest;
import it.iwkz.api.payloads.income.AddIncomeTypeRequest;
import it.iwkz.api.payloads.income.TotalIncomeResponse;
import it.iwkz.api.repositories.IncomeTypeRepository;
import it.iwkz.api.repositories.IncomesRepository;
import it.iwkz.api.services.IncomeService;
import it.iwkz.api.utils.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private IncomesRepository incomesRepository;

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

    @PostMapping("/type")
    @ResponseStatus(HttpStatus.CREATED)
    public void addIncomeType(@Validated @RequestBody AddIncomeTypeRequest incomeTypeRequest) {
        incomeService.addIncomeType(incomeTypeRequest);
    }

    @PutMapping("/type/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateIncomeType(
            @PathVariable long id,
            @Validated @RequestBody AddIncomeTypeRequest incomeTypeRequest
    ) {
        incomeService.updateIncomeType(id, incomeTypeRequest);
    }

    @GetMapping
    public ListResponse<Income> getIncomesByMonthYear(
            @RequestParam(value = "month", required = false, defaultValue = "0") int month,
            @RequestParam(value = "year", required = false, defaultValue = "0") int year,
            @RequestParam(value = "page", defaultValue = AppConst.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "pageSize", defaultValue = AppConst.DEFAULT_PAGE_SIZE) int pageSize
    ) {
        if (month == 0) month = AppConst.CURRENT_MONTH;
        if (year == 0) year = AppConst.CURRENT_YEAR;

        return incomeService.getAllIncomesByMonthYear(month, year, page, pageSize);
    }

    @GetMapping("/total")
    public EntityResponse<TotalIncomeResponse> getTotalIncomeByMonthYear(
            @RequestParam(value = "month", required = false, defaultValue = "0") int month,
            @RequestParam(value = "year", required = false, defaultValue = "0") int year
    ) {
        if (month == 0) month = AppConst.CURRENT_MONTH;
        if (year == 0) year = AppConst.CURRENT_YEAR;

        return new EntityResponse<>(incomeService.getTotalIncomes(month, year));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addIncome(@Valid @RequestBody AddIncomeRequest incomeRequest) {
        incomeService.addIncome(incomeRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateIncome(
            @PathVariable long id,
            @Valid @RequestBody AddIncomeRequest incomeRequest
    ) {
        incomeService.updateIncome(id, incomeRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteIncome(@PathVariable long id) {
        incomeService.deleteIncome(id);
    }
}
