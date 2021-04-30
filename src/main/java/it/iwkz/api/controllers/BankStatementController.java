package it.iwkz.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.iwkz.api.exceptions.BadRequestException;
import it.iwkz.api.helper.CSVHelper;
import it.iwkz.api.payloads.bankstatement.BankStatementPayload;

@RestController
@RequestMapping( "/api/bankstatement" )
public class BankStatementController extends AbstractController {

    @PostMapping( "/upload" )
    public void upload( @RequestParam( "file" ) MultipartFile file ) {
        if (!CSVHelper.hasCSVFormat( file ) || file.isEmpty()) {
            throw new BadRequestException( "it's not csv or file is empty" );
        }

        List<BankStatementPayload> listBankStatement = CSVHelper.convertCSVToListPayload( file );
        listBankStatement.forEach( System.out::println );
    }

}
