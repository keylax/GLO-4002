package ca.ulaval.glo4002.trading.interfaces.rest.reports;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionReport;
import ca.ulaval.glo4002.trading.application.services.transactions.TransactionService;
import ca.ulaval.glo4002.trading.interfaces.rest.AccountsPaths;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportMissingDateException;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportMissingTypeException;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportTypeUnsupportedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path(AccountsPaths.ROOT)
@Produces(MediaType.APPLICATION_JSON)
public class ReportResource {
    private static final String TYPE_ARGUMENT = "type";
    private static final String DATE_ARGUMENT = "date";
    private static final String DAILY_REPORT_TYPE = "DAILY";

    private TransactionService transactionService;

    public ReportResource(TransactionService _transactionService) {
        transactionService = _transactionService;
    }

    @GET
    @Path(AccountsPaths.ACCOUNT_TRANSACTIONS_HISTORY)
    public TransactionReport consultTransactionHistory(
            @PathParam(AccountsPaths.ACCOUNT_NUMBER_ARGUMENT) String _accountNumber,
            @QueryParam(TYPE_ARGUMENT) String _type,
            @QueryParam(DATE_ARGUMENT) String _date) {
        validateReportDateDefined(_date);
        validateReportTypeDefined(_type);

        if (DAILY_REPORT_TYPE.equals(_type)) {
            return transactionService.consultTransactionDailyReport(_accountNumber, _date);
        }

        throw new TransactionReportTypeUnsupportedException(_type);
    }

    private void validateReportDateDefined(String _date) {
        if (_date == null) {
            throw new TransactionReportMissingDateException();
        }
    }

    private void validateReportTypeDefined(String _type) {
        if (_type == null) {
            throw new TransactionReportMissingTypeException();
        }
    }
}
