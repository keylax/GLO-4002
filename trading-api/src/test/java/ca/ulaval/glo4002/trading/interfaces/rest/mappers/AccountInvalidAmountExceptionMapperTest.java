package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountInvalidAmountException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class AccountInvalidAmountExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"credit amount cannot be lower than or equal to zero\",\"error\":\"INVALID_AMOUNT\"}";

    @Test
    public void whenMappingAnAccountInvalidAmountException_thenReturnExpectedResponse() {
        AccountInvalidAmountExceptionMapper mapper = new AccountInvalidAmountExceptionMapper();
        AccountInvalidAmountException exception = new AccountInvalidAmountException();

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(EXPECTED_ENTITY, mapperResponse.getEntity());
    }

}
