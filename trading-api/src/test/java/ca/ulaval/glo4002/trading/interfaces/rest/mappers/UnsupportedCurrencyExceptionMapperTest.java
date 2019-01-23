package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.UnsupportedCurrencyException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class UnsupportedCurrencyExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String A_CURRENCY = "CAD";
    private static final String EXPECTED_ENTITY = "{\"description\":\""
                                                  + "The currency %s is unsupported, must be USD, CHF or JPY\","
                                                  + "\"error\":\"UNSUPPORTED_CURRENCY\"}";

    @Test
    public void whenMappingAnUnsupportedCurrencyException_thenReturnExpectedResponse() {
        String expectedEntity = String.format(EXPECTED_ENTITY, A_CURRENCY);
        UnsupportedCurrencyExceptionMapper mapper = new UnsupportedCurrencyExceptionMapper();
        UnsupportedCurrencyException exception = new UnsupportedCurrencyException(A_CURRENCY);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedEntity, mapperResponse.getEntity());
    }
}
