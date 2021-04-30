package it.iwkz.api.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;

import it.iwkz.api.payloads.bankstatement.BankStatementPayload;

public class CSVHelper {
    private static String TYPE = "text/csv";
    private static int lineOfContent = 10;

    public static boolean hasCSVFormat( MultipartFile file ) {

        if (!TYPE.equals( file.getContentType() )) {
            return false;
        }

        return true;
    }

    public static List<BankStatementPayload> convertCSVToListPayload( MultipartFile file ) {
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator( ';' ).build();
            CSVReader reader = new CSVReaderBuilder( new BufferedReader( new InputStreamReader( file.getInputStream() ) ) ).withCSVParser( csvParser )
                            .withSkipLines( lineOfContent ).build();
            return new CsvToBeanBuilder( reader ).withType( BankStatementPayload.class ).build().parse();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
