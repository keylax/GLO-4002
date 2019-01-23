package ca.ulaval.glo4002.trading.interfaces.rest.accounts;

import ca.ulaval.glo4002.trading.interfaces.rest.AccountsPaths;
import ca.ulaval.glo4002.trading.interfaces.rest.accounts.exceptions.AccountRequestInvalidException;
import ca.ulaval.glo4002.trading.interfaces.rest.accounts.validators.AccountRequestValidator;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.requests.OpenAccountRequest;
import ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses.AccountResponse;
import ca.ulaval.glo4002.trading.application.services.accounts.AccountService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(AccountsPaths.ROOT)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    private static final String LOCATION_HEADER = "Location";
    private static final String SEPARATOR = "/";

    private AccountService accountService;
    private AccountRequestValidator accountRequestValidator;

    public AccountResource(AccountService _accountService,
                           AccountRequestValidator _accountRequestValidator) {
        accountService = _accountService;
        accountRequestValidator = _accountRequestValidator;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response openAccount(OpenAccountRequest _openAccountRequest) {
        if (!accountRequestValidator.isAccountRequestValid(_openAccountRequest)) {
            throw new AccountRequestInvalidException();
        }

        String accountNumber = accountService.openAccount(_openAccountRequest);
        String locationResponse = buildAccountLocation(accountNumber);
        return Response.status(Response.Status.CREATED).header(LOCATION_HEADER, locationResponse).build();
    }

    @GET
    @Path(AccountsPaths.ACCOUNT_NUMBER)
    public AccountResponse consultAccount(@PathParam(AccountsPaths.ACCOUNT_NUMBER_ARGUMENT) String _accountNumber) {
        return accountService.consultAccount(_accountNumber);
    }

    private String buildAccountLocation(String _accountNumber) {
        return AccountsPaths.ROOT + SEPARATOR + _accountNumber;
    }
}
