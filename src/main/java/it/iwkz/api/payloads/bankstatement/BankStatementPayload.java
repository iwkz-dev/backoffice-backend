package it.iwkz.api.payloads.bankstatement;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
public class BankStatementPayload {
    @CsvBindByPosition( position = 0 )
    private String incomingDate;

    @CsvBindByPosition( position = 2 )
    private String type;

    @CsvBindByPosition( position = 3 )
    private String details;

    @CsvBindByPosition( position = 6 )
    private String amount;

    @CsvBindByPosition( position = 7 )
    private String saldo;

}
