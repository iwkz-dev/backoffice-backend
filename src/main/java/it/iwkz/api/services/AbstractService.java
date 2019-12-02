package it.iwkz.api.services;

import it.iwkz.api.exceptions.BadRequestException;

public class AbstractService {
    public void isValidPage(int page) {
        if (page < 0) {
            throw new BadRequestException("invalid page number!");
        }
    }

    public void isValidDate(int month) {
        if (month < 0) {
            throw new BadRequestException("invalid month number!");
        }
    }
}
