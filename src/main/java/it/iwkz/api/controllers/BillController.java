package it.iwkz.api.controllers;

import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.Bill;
import it.iwkz.api.models.BillType;
import it.iwkz.api.payloads.EntityResponse;
import it.iwkz.api.payloads.ListResponse;
import it.iwkz.api.payloads.bill.AddBillRequest;
import it.iwkz.api.payloads.bill.AddBillTypeRequest;
import it.iwkz.api.payloads.bill.TotalBillResponse;
import it.iwkz.api.repositories.BillTypeRepository;
import it.iwkz.api.services.BillService;
import it.iwkz.api.utils.AppConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillTypeRepository billTypeRepository;

    @Autowired
    private BillService billService;

    @GetMapping("/types")
    public ListResponse<BillType> getBillTypes() {
        return new ListResponse<>(billTypeRepository.findAll());
    }

    @GetMapping("/types/{id}")
    public EntityResponse<BillType> getBillType(@PathVariable long id) {
        BillType billType = billTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BillType", "getBillType", id));
        return new EntityResponse<>(billType);
    }

    @PostMapping("/type")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBillType(@Validated @RequestBody AddBillTypeRequest billTypeRequest) {
        billService.addBillType(billTypeRequest);
    }

    @PutMapping("/type/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBillType(
            @PathVariable long id,
            @Validated @RequestBody AddBillTypeRequest billTypeRequest
    ) {
        billService.updateBillType(id, billTypeRequest);
    }

    @GetMapping
    public ListResponse<Bill> getBillByMonthYear(
            @RequestParam(value = "month", required = false, defaultValue = "0") int month,
            @RequestParam(value = "year", required = false, defaultValue = "0") int year,
            @RequestParam(value = "page", defaultValue = AppConst.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "pageSize", defaultValue = AppConst.DEFAULT_PAGE_SIZE) int pageSize
    ) {
        if (month == 0) month = AppConst.CURRENT_MONTH;
        if (year == 0) year = AppConst.CURRENT_YEAR;

        return billService.getAllIncomesByMonthYear(month, year, page, pageSize);
    }

    @GetMapping("/total")
    public EntityResponse<TotalBillResponse> getTotalBillsByMonthYear(
            @RequestParam(value = "month", required = false, defaultValue = "0") int month,
            @RequestParam(value = "year", required = false, defaultValue = "0") int year
    ) {
        if (month == 0) month = AppConst.CURRENT_MONTH;
        if (year == 0) year = AppConst.CURRENT_YEAR;

        return new EntityResponse<>(billService.getTotalBillByMonthYear(month, year));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBill(@Valid @RequestBody AddBillRequest billRequest) {
        billService.addBill(billRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBill(
            @PathVariable long id,
            @Valid @RequestBody AddBillRequest billRequest
    ) {
        billService.updateBill(id, billRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBill(@PathVariable long id) {
        billService.deleteBill(id);
    }
}
