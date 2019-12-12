package it.iwkz.api.services;

import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.IncomeType;
import it.iwkz.api.models.Income;
import it.iwkz.api.payloads.ListResponse;
import it.iwkz.api.payloads.bill.TotalBillResponse;
import it.iwkz.api.payloads.income.AddIncomeRequest;
import it.iwkz.api.payloads.income.AddIncomeTypeRequest;
import it.iwkz.api.payloads.income.IncomePercentageResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class IncomeService extends AbstractService{
    @Autowired
    private IncomesRepository incomesRepository;

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private BillService billService;

    private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);

    @Transactional
    public Income addIncome(AddIncomeRequest incomeRequest) {
        IncomeType incomeType = incomeTypeRepository.findById(incomeRequest.getIncomeTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("IncomeType", "addIncome", incomeRequest.getIncomeTypeId()));

        Income income = new Income();
        income.setAmount(incomeRequest.getAmount());
        income.setIncomeType(incomeType);
        income.setInfo(Optional.of(incomeRequest).map(AddIncomeRequest::getInfo).orElse(""));
        income.setMonth(Optional.of(incomeRequest).map(AddIncomeRequest::getMonth).orElse(AppConst.CURRENT_MONTH));
        income.setYear(Optional.of(incomeRequest).map(AddIncomeRequest::getYear).orElse(AppConst.CURRENT_YEAR));

        return incomesRepository.save(income);
    }

    public ListResponse<Income> getAllIncomesByMonthYear(int month, int year, int page, int pageSize) {
        isValidPage(page);
        isValidDate(month);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdAt");
        Page<Income> incomes = incomesRepository.findByMonthYear(month, year, pageable);

        ListResponse<Income> listResponse = new ListResponse<>();
        listResponse.setData(incomes.getContent());
        listResponse.setPage(incomes.getNumber());
        listResponse.setPageSize(incomes.getSize());
        listResponse.setCount(incomes.getTotalElements());

        return listResponse;
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
        response.setTotalIncomes(roundingValue(totalIncomes));
        response.setTotalIncomeByTypes(incomeByTypes);

        return response;
    }

    @Transactional
    public Income updateIncome(long incomeId, AddIncomeRequest incomeRequest) {
        Income income = incomesRepository.findById(incomeId)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "incomeId", incomeId));

        IncomeType incomeType = incomeTypeRepository.findById(incomeRequest.getIncomeTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("IncomeType", "incomeTypeId", incomeRequest.getIncomeTypeId()));

        income.setAmount(incomeRequest.getAmount());
        income.setInfo(incomeRequest.getInfo());
        income.setIncomeType(incomeType);

        return incomesRepository.save(income);
    }

    @Transactional
    public void deleteIncome(long incomeId) {
        Income income = incomesRepository.findById(incomeId)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "incomeId", incomeId));

        incomesRepository.delete(income);
    }

    @Transactional
    public IncomeType updateIncomeType(long incomeTypeId, AddIncomeTypeRequest incomeTypeRequest) {
        IncomeType incomeType = incomeTypeRepository.findById(incomeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("IncomeType", "incomeTypeId", incomeTypeId));

        incomeType.setName(incomeTypeRequest.getName());
        incomeType.setDescription(incomeTypeRequest.getDescription());

        return incomeTypeRepository.save(incomeType);
    }

    @Transactional
    public IncomeType addIncomeType(AddIncomeTypeRequest incomeTypeRequest) {
        IncomeType incomeType = new IncomeType();
        incomeType.setName(incomeTypeRequest.getName());
        incomeType.setDescription(incomeTypeRequest.getDescription());

        return incomeTypeRepository.save(incomeType);
    }

    public IncomePercentageResponse calculateIncomePercentage(int month, int year) {
        TotalIncomeResponse incomeResponse = getTotalIncomes(month, year);
        TotalBillResponse billResponse = billService.getTotalBillByMonthYear(month, year);

        double totalIncomeAmount = incomeResponse.getTotalIncomes();
        double totalBillAmount = billResponse.getTotalBills();

        double percentage = (totalIncomeAmount * 100.0d) / totalBillAmount;

        IncomePercentageResponse response = new IncomePercentageResponse();
        response.setTotalBill(totalBillAmount);
        response.setTotalIncome(totalIncomeAmount);
        response.setIncomePercentage(percentage > 100 ? 100 : roundingValue(percentage));

        return response;
    }
}
