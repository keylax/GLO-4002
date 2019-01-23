package ca.ulaval.glo4002.trading.application.services.transactions.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class TransactionDateValidator {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final int MINIMUM_YEAR = 2015;
    private static final int MAXIMUM_YEAR = 2018;

    private LocalDate appContextDate;

    public TransactionDateValidator(LocalDate _appContextDate) {
        appContextDate = _appContextDate;
    }

    public boolean isDateInValidYearRange(int _dateYear) {
        return _dateYear >= MINIMUM_YEAR && _dateYear <= MAXIMUM_YEAR;
    }

    public boolean isReportDateValid(LocalDate _date) {
        int yearOfDate = _date.getYear();
        return isDateInValidYearRange(yearOfDate) && isDateBeforeActualDate(_date);
    }

    public boolean isDayValid(String _date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(_date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isDateBeforeActualDate(LocalDate _date) {
        return _date.isBefore(appContextDate);
    }
}
