package ca.ulaval.glo4002.trading.application.services.transactions.validators;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class TransactionDateValidatorTest {
    private static final String A_SERIALIZED_DATE_WITH_INVALID_DAY = "2018-04-31T15:50:30.333Z";
    private static final int A_YEAR_SUPERIOR_TO_AUTHORIZE_RANGE = 2019;
    private static final int A_YEAR_INFERIOR_TO_AUTHORIZE_RANGE = 2014;
    private static final int A_VALID_YEAR = 2018;
    private static final int A_VALID_MONTH = 1;
    private static final int A_VALID_DAY = 1;

    @Test
    public void givenAYearInferiorToAuthorizeRange_whenValidatingIfDateInValidRange_thenReturnFalse() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isDateInValidYearRange = validator.isDateInValidYearRange(A_YEAR_INFERIOR_TO_AUTHORIZE_RANGE);

        Assert.assertFalse(isDateInValidYearRange);
    }

    @Test
    public void givenAYearSuperiorToAuthorizeRange_whenValidatingIfDateInValidRange_thenReturnFalse() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isDateInValidYearRange = validator.isDateInValidYearRange(A_YEAR_SUPERIOR_TO_AUTHORIZE_RANGE);

        Assert.assertFalse(isDateInValidYearRange);
    }

    @Test
    public void whenValidatingIfDateInValidRange_thenReturnTrue() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isDateInValidYearRange = validator.isDateInValidYearRange(A_VALID_YEAR);

        Assert.assertTrue(isDateInValidYearRange);
    }

    @Test
    public void givenAReportDateWithYearSuperiorToAuthorizeRange_whenValidatingReportDate_thenReturnFalse() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        LocalDate dateReport = LocalDate.of(A_YEAR_SUPERIOR_TO_AUTHORIZE_RANGE, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isReportDateValid = validator.isReportDateValid(dateReport);

        Assert.assertFalse(isReportDateValid);
    }

    @Test
    public void givenAReportDateWithYearInferiorToAuthorizeRange_whenValidatingReportDate_thenReturnFalse() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        LocalDate dateReport = LocalDate.of(A_YEAR_INFERIOR_TO_AUTHORIZE_RANGE, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isReportDateValid = validator.isReportDateValid(dateReport);

        Assert.assertFalse(isReportDateValid);
    }

    @Test
    public void givenAReportDateAndAExecutionDateWithTheSameValue_whenValidatingReportDate_thenReturnFalse() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        LocalDate dateReport = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isReportDateValid = validator.isReportDateValid(dateReport);

        Assert.assertFalse(isReportDateValid);
    }

    @Test
    public void givenAReportDateGreaterThenExecutionDate_whenValidatingReportDate_thenReturnFalse() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        LocalDate dateReport = LocalDate.of(A_VALID_YEAR + 1, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isReportDateValid = validator.isReportDateValid(dateReport);

        Assert.assertFalse(isReportDateValid);
    }

    @Test
    public void givenAReportDateLesserThenExecutionDate_whenValidatingReportDate_thenReturnTrue() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        LocalDate dateReport = LocalDate.of(A_VALID_YEAR - 1, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isReportDateValid = validator.isReportDateValid(dateReport);

        Assert.assertTrue(isReportDateValid);
    }

    @Test
    public void givenADateWithAnInvalidDay_whenValidatingDay_thenReturnFalse() {
        LocalDate aExecutionDate = LocalDate.of(A_VALID_YEAR, A_VALID_MONTH, A_VALID_DAY);
        TransactionDateValidator validator = new TransactionDateValidator(aExecutionDate);

        boolean isDayValid = validator.isDayValid(A_SERIALIZED_DATE_WITH_INVALID_DAY);

        Assert.assertFalse(isDayValid);
    }
}
