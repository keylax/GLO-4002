package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.accounts.exceptions.AccountRequestInvalidException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class AccountRequestInvalidExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"all parameters must be declared\",\"error\":\"UNDEFINED_PARAMETER\"}";

    @Test
    public void whenMappingAAccountRequestInvalidException_thenReturnExpectedResponse() {
        AccountRequestInvalidExceptionMapper mapper = new AccountRequestInvalidExceptionMapper();
        AccountRequestInvalidException exception = new AccountRequestInvalidException();

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(EXPECTED_ENTITY, mapperResponse.getEntity());
    }
}
