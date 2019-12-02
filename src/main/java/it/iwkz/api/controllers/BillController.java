package it.iwkz.api.controllers;

import it.iwkz.api.exceptions.ResourceNotFoundException;
import it.iwkz.api.models.Bill;
import it.iwkz.api.models.BillType;
import it.iwkz.api.payloads.ApiResponse;
import it.iwkz.api.payloads.bill.AddBillRequest;
import it.iwkz.api.payloads.bill.TotalBillResponse;
import it.iwkz.api.repositories.BillTypeRepository;
import it.iwkz.api.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillTypeRepository billTypeRepository;

    @Autowired
    private BillService billService;

    @GetMapping("/types")
    public List<BillType> getBillTypes() {
        return billTypeRepository.findAll();
    }

    @GetMapping("/types/{id}")
    public BillType getBillType(@PathVariable long id) {
        return billTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BillType", "getBillType", id));
    }

    @GetMapping("/total/{month}/{year}")
    public TotalBillResponse getTotalBillsByMonthYear(@PathVariable int month, @PathVariable int year) {
        return billService.getTotalBillByMonthYear(month, year);
    }

    @PostMapping
    public ResponseEntity addBill(@Valid @RequestBody AddBillRequest billRequest) {
        Bill bill = billService.addBill(billRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{incomeId}")
                .buildAndExpand(bill.getId()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "bill added" + location));
    }
}
