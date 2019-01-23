package ca.ulaval.glo4002.trading.interfaces.rest.transactions;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionBuyResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionSellResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.TransactionService;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionResponse;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions.TransactionDtoInvalidException;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions.UnsupportedTransactionException;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.validators.TransactionRequestValidator;
import ca.ulaval.glo4002.trading.interfaces.rest.AccountsPaths;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.UUID;

@Path(AccountsPaths.ROOT)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    private static final String LOCATION_HEADER = "Location";
    private static final String SEPARATOR = "/";

    private TransactionService transactionService;
    private TransactionRequestValidator transactionRequestValidator;

    public TransactionResource(
            TransactionService _transactionService,
            TransactionRequestValidator _transactionRequestValidator) {
        transactionService = _transactionService;
        transactionRequestValidator = _transactionRequestValidator;
    }

    @POST
    @Path(AccountsPaths.ACCOUNT_TRANSACTIONS)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response performTransaction(@PathParam(AccountsPaths.ACCOUNT_NUMBER_ARGUMENT) String _accountNumber,
                                       TransactionRequest _transactionRequest) {
        if (!transactionRequestValidator.isTransactionRequestValid(_transactionRequest)) {
            throw new TransactionDtoInvalidException();
        }

        UUID transactionNumber;
        if (TransactionBuyResponse.TYPE.equals(_transactionRequest.type)) {
            transactionNumber = transactionService.performBuy(_transactionRequest, _accountNumber);
        } else if (TransactionSellResponse.TYPE.equals(_transactionRequest.type)) {
            transactionNumber = transactionService.performSell(
                    _transactionRequest,
                    _accountNumber,
                    _transactionRequest.transactionNumber);
        } else {
            throw new UnsupportedTransactionException(_transactionRequest.type);
        }

        String locationResponse = buildTransactionLocation(_accountNumber, transactionNumber);
        return Response.status(Response.Status.CREATED).header(LOCATION_HEADER, locationResponse).build();
    }

    @GET
    @Path(AccountsPaths.ACCOUNT_TRANSACTION)
    public TransactionResponse consultTransaction(
            @PathParam(AccountsPaths.ACCOUNT_NUMBER_ARGUMENT) String _accountNumber,
            @PathParam(AccountsPaths.TRANSACTION_NUMBER_ARGUMENT) UUID _transactionNumber) {
        return transactionService.consultTransaction(_transactionNumber, _accountNumber);
    }

    private String buildTransactionLocation(String _accountNumber, UUID _transactionId) {
        String accountLocation = AccountsPaths.ROOT + SEPARATOR + _accountNumber;
        return accountLocation + SEPARATOR + AccountsPaths.TRANSACTIONS + SEPARATOR + _transactionId.toString();
    }
}
