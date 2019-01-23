package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions.AccountAlreadyOpenException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class AccountAlreadyOpenExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final int AN_INVESTOR_ID = 34;
    private static final String EXPECTED_ENTITY = "{\"description\":\"account already open for investor %d\","
            + "\"error\":\"ACCOUNT_ALREADY_OPEN\"}";

    @Test
    public void whenMappingAnAccountAlreadyOpenException_thenReturnExpectedResponse() {
        String expectedEntity = String.format(EXPECTED_ENTITY, AN_INVESTOR_ID);
        AccountAlreadyOpenExceptionMapper mapper = new AccountAlreadyOpenExceptionMapper();
        AccountAlreadyOpenException exception = new AccountAlreadyOpenException(AN_INVESTOR_ID);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedEntity, mapperResponse.getEntity());
    }
}
