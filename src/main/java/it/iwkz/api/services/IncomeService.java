package it.iwkz.api.services;

import it.iwkz.api.exceptions.BadRequestException;
import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.IncomeType;
import it.iwkz.api.models.Income;
import it.iwkz.api.payloads.PagedResponse;
import it.iwkz.api.payloads.income.AddIncomeRequest;
import it.iwkz.api.payloads.income.TotalIncomeResponse;
import it.iwkz.api.repositories.IncomeTypeRepository;
import it.iwkz.api.repositories.IncomesRepository;
import it.iwkz.api.utils.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IncomeService {
    @Autowired
    private IncomesRepository incomesRepository;

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);

    public Income addIncome(AddIncomeRequest incomeRequest) {
        IncomeType incomeType = incomeTypeRepository.findById(incomeRequest.getIncomeTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("IncomeType", "addIncome", incomeRequest.getIncomeTypeId()));

        Income income = new Income();
        income.setAmount(incomeRequest.getAmount());
        income.setIncomeType(incomeType);
        income.setMonth(incomeRequest.getMonth());
        income.setYear(incomeRequest.getYear());
        income.setInfo(Optional.of(incomeRequest).map(AddIncomeRequest::getInfo).orElse(""));

        return incomesRepository.save(income);
    }

    public PagedResponse<Income> getAllIncomesByMonthYear(int month, int year, int page, int pageSize) {
        isValidPage(page);
        isValidDate(month);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdAt");
        Page<Income> incomes = incomesRepository.findByMonthYear(month, year, pageable);

        PagedResponse<Income> pagedResponse = new PagedResponse<>();
        pagedResponse.setData(incomes.getContent());
        pagedResponse.setPage(incomes.getNumber());
        pagedResponse.setPageSize(incomes.getSize());
        pagedResponse.setTotalData(incomes.getTotalElements());

        return pagedResponse;
    }

    public TotalIncomeResponse getTotalIncomes(int month, int year) {
        isValidDate(month);

        Pageable pageable = PageRequest.of(0, Integer.parseInt(AppConst.MAX_PAGE_SIZE), Sort.Direction.DESC,"createdAt");
        Page<Income> incomes = incomesRepository.findByMonthYear(month, year, pageable);

        HashMap<String, Double> incomeByTypes = new HashMap<>();
        double totalIncomes = 0d;

        for(Income income : incomes) {
            String type = income.getIncomeType().getName();
            double amount = income.getAmount();

            if (incomeByTypes.containsKey(type)) {
                amount += incomeByTypes.get(type);
            }

            incomeByTypes.put(type, amount);
            totalIncomes += amount;
        }

        TotalIncomeResponse response = new TotalIncomeResponse();
        response.setMonth(month);
        response.setYear(year);
        response.setTotalIncomes(totalIncomes);
        response.setTotalIncomeByTypes(incomeByTypes);

        return response;
    }

    private void isValidPage(int page) {
        if (page < 0) {
            throw new BadRequestException("invalid page number!");
        }
    }

    private void isValidDate(int month) {
        if (month < 0) {
            throw new BadRequestException("invalid month number!");
        }
    }
}
