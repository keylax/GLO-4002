package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountDuplicateCurrencyException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class AccountDuplicateCurrencyExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String A_CURRENCY = "USD";
    private static final String EXPECTED_ENTITY = "{\"description\":\"The currency %s cannot be declared twice\","
                                                  + "\"error\":\"DUPLICATE_CURRENCY\"}";

    @Test
    public void whenMappingAnAccountSameCurrencyException_thenReturnExpectedResponse() {
        String expectedEntity = String.format(EXPECTED_ENTITY, A_CURRENCY);
        AccountDuplicateCurrencyExceptionMapper mapper = new AccountDuplicateCurrencyExceptionMapper();
        AccountDuplicateCurrencyException exception = new AccountDuplicateCurrencyException(A_CURRENCY);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedEntity, mapperResponse.getEntity());
    }
}
