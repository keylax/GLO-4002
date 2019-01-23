package ca.ulaval.glo4002.trading.interfaces.rest.reports;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionReport;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.TransactionService;
import ca.ulaval.glo4002.trading.interfaces.rest.helpers.TransactionResponseHelper;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportMissingDateException;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportMissingTypeException;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportTypeUnsupportedException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportResourceTest {
    private static final String AN_ACCOUNT_NUMBER = "anAccountNumber";
    private static final String UNSUPPORTED_REPORT_TYPE = "unsupportedType";
    private static final String DAILY_REPORT_TYPE = "DAILY";
    private static final String A_VALID_DATE = "2017-01-01";
    private static final String A_VALID_REPORT_TYPE = DAILY_REPORT_TYPE;

    private ReportResource reportResource;

    @Mock
    private TransactionService transactionService;

    @Before
    public void setup() {
        reportResource = new ReportResource(transactionService);
    }

    @Test(expected = TransactionReportMissingTypeException.class)
    public void givenAnUndefinedReportType_whenConsultingTransactionHistory_thenAnExceptionIsThrown() {
        reportResource.consultTransactionHistory(AN_ACCOUNT_NUMBER, null, A_VALID_DATE);
    }

    @Test(expected = TransactionReportTypeUnsupportedException.class)
    public void givenAnUnsupportedReportType_whenConsultingTransactionHistory_thenAnExceptionIsThrown() {
        reportResource.consultTransactionHistory(AN_ACCOUNT_NUMBER, UNSUPPORTED_REPORT_TYPE, A_VALID_DATE);
    }

    @Test(expected = TransactionReportMissingDateException.class)
    public void givenAnUndefinedReportDate_whenConsultingTransactionHistory_thenAnExceptionIsThrown() {
        reportResource.consultTransactionHistory(AN_ACCOUNT_NUMBER, A_VALID_REPORT_TYPE, null);
    }

    @Test
    public void givenAnDailyReportType_whenConsultingTransactionHistory_thenReturnExpectedTransactionReport() {
        TransactionReport expectedTransactionReport = createATransactionReport();
        when(transactionService.consultTransactionDailyReport(AN_ACCOUNT_NUMBER, A_VALID_DATE))
                .thenReturn(expectedTransactionReport);

        TransactionReport actualTransactionReport = reportResource.consultTransactionHistory(
                AN_ACCOUNT_NUMBER,
                DAILY_REPORT_TYPE,
                A_VALID_DATE);

        Assert.assertEquals(expectedTransactionReport, actualTransactionReport);
    }

    private TransactionReport createATransactionReport() {
        TransactionResponse transactionResponse = TransactionResponseHelper.createATransactionResponse(
                UUID.randomUUID());
        TransactionResponse[] transactionResponses = {transactionResponse};
        return new TransactionReport(Instant.now(), transactionResponses);
    }
}
