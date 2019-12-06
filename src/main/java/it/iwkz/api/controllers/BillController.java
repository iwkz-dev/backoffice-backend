package it.iwkz.api.controllers;

import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.BillType;
import it.iwkz.api.payloads.EntityResponse;
import it.iwkz.api.payloads.ListResponse;
import it.iwkz.api.payloads.bill.AddBillRequest;
import it.iwkz.api.payloads.bill.TotalBillResponse;
import it.iwkz.api.repositories.BillTypeRepository;
import it.iwkz.api.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;

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

    @GetMapping("/total")
    public EntityResponse<TotalBillResponse> getTotalBillsByMonthYear(
            @RequestParam(value = "month", required = false, defaultValue = "0") int month,
            @RequestParam(value = "year", required = false, defaultValue = "0") int year
    ) {
        if (month == 0) month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        if (year == 0) year = Calendar.getInstance().get(Calendar.YEAR);

        return new EntityResponse<>(billService.getTotalBillByMonthYear(month, year));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBill(@Valid @RequestBody AddBillRequest billRequest) {
        billService.addBill(billRequest);
    }
}
