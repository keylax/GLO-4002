package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions.AccountNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class AccountNotFoundExceptionMapperTest {
    private static final int NOT_FOUND_CODE = 404;
    private static final String AN_ACCOUNT_NUMBER = "VF-86";
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"account with number %s not found\",\"error\":\"ACCOUNT_NOT_FOUND\"}";

    @Test
    public void whenMappingAnAccountNotFoundException_thenReturnExpectedResponse() {
        AccountNumber accountNumber = new AccountNumber(AN_ACCOUNT_NUMBER);
        String expectedEntity = String.format(EXPECTED_ENTITY, AN_ACCOUNT_NUMBER);
        AccountNotFoundExceptionMapper mapper = new AccountNotFoundExceptionMapper();
        AccountNotFoundException exception = new AccountNotFoundException(accountNumber);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(NOT_FOUND_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedEntity, mapperResponse.getEntity());
    }
}
