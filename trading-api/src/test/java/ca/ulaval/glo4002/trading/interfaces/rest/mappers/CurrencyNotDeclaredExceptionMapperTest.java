package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.CurrencyNotDeclaredException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class CurrencyNotDeclaredExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String A_CURRENCY = "USD";
    private static final String EXPECTED_ENTITY = "{\"description\":\""
                                                  + "This account does not have credits for the currency %s\","
                                                  + "\"error\":\"UNDEFINED_CURRENCY\"}";

    @Test
    public void whenMappingACurrencyNotDeclaredException_thenReturnExpectedResponse() {
        String expectedEntity = String.format(EXPECTED_ENTITY, A_CURRENCY);
        CurrencyNotDeclaredExceptionMapper mapper = new CurrencyNotDeclaredExceptionMapper();
        CurrencyNotDeclaredException exception = new CurrencyNotDeclaredException(A_CURRENCY);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedEntity, mapperResponse.getEntity());
    }
}
