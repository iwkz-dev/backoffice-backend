package it.iwkz.api.services;

import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.Bill;
import it.iwkz.api.models.BillType;
import it.iwkz.api.payloads.ListResponse;
import it.iwkz.api.payloads.bill.AddBillRequest;
import it.iwkz.api.payloads.bill.AddBillTypeRequest;
import it.iwkz.api.payloads.bill.TotalBillResponse;
import it.iwkz.api.repositories.BillTypeRepository;
import it.iwkz.api.repositories.BillsRepository;
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

import java.util.HashMap;

@Service
public class BillService extends AbstractService{
    @Autowired
    private BillsRepository billsRepository;

    @Autowired
    private BillTypeRepository billTypeRepository;

    private static final Logger logger = LoggerFactory.getLogger(BillsRepository.class);

    @Transactional
    public Bill addBill(AddBillRequest billRequest) {
        BillType billType = billTypeRepository.findById(billRequest.getBillTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("BillType", "addBill", billRequest.getBillTypeId()));

        Bill bill = new Bill();
        bill.setMonth(billRequest.getMonth());
        bill.setYear(billRequest.getYear());
        bill.setAmount(billRequest.getAmount());
        bill.setBillType(billType);

        return billsRepository.save(bill);
    }

    public ListResponse<Bill> getAllIncomesByMonthYear(int month, int year, int page, int pageSize) {
        isValidPage(page);
        isValidDate(month);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "createdAt");
        Page<Bill> incomes = billsRepository.findByMonthYear(month, year, pageable);

        ListResponse<Bill> listResponse = new ListResponse<>();
        listResponse.setData(incomes.getContent());
        listResponse.setPage(incomes.getNumber());
        listResponse.setPageSize(incomes.getSize());
        listResponse.setCount(incomes.getTotalElements());

        return listResponse;
    }

    public TotalBillResponse getTotalBillByMonthYear(int month, int year) {
        isValidDate(month);

        Pageable pageable = PageRequest.of(0, Integer.parseInt(AppConst.MAX_PAGE_SIZE), Sort.Direction.DESC,"createdAt");
        Page<Bill> bills = billsRepository.findByMonthYear(month, year, pageable);

        HashMap<String, Double> billByTypes = new HashMap<>();
        double totalBills = 0d;

        for(Bill bill : bills) {
            String type = bill.getBillType().getName();
            double amount = bill.getAmount();
            totalBills += amount;

            if (billByTypes.containsKey(type)) {
                amount += billByTypes.get(type);
            }

            billByTypes.put(type, amount);
        }

        TotalBillResponse response = new TotalBillResponse();
        response.setMonth(month);
        response.setYear(year);
        response.setTotalBills(roundingValue(totalBills));
        response.setTotalBillByTypes(billByTypes);

        return response;
    }

    @Transactional
    public Bill updateBill(long billId, AddBillRequest billRequest) {
        Bill bill = billsRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "billId", billId));

        BillType billType = billTypeRepository.findById(billRequest.getBillTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("BillType", "billTypeId", billRequest.getBillTypeId()));

        bill.setAmount(billRequest.getAmount());
        bill.setInfo(billRequest.getInfo());
        bill.setBillType(billType);

        return billsRepository.save(bill);
    }

    @Transactional
    public void deleteBill(long billId) {
        Bill bill = billsRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "billId", billId));

        billsRepository.delete(bill);
    }

    @Transactional
    public BillType updateBillType(long billTypeId, AddBillTypeRequest billTypeRequest) {
        BillType billType = billTypeRepository.findById(billTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("BillType", "billTypeId", billTypeId));

        billType.setName(billTypeRequest.getName());
        billType.setDescription(billTypeRequest.getDescription());

        return billTypeRepository.save(billType);
    }

    @Transactional
    public BillType addBillType(AddBillTypeRequest billTypeRequest) {
        BillType billType = new BillType();
        billType.setName(billTypeRequest.getName());
        billType.setDescription(billTypeRequest.getDescription());

        return billTypeRepository.save(billType);
    }
}
