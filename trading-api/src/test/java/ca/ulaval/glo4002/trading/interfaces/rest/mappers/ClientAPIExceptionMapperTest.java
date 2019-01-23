package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.exceptions.ClientAPIException;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class ClientAPIExceptionMapperTest {
    private static final String AN_ERROR_MESSAGE = "The API is not able to find the requested data";
    private static final String EXPECTED_ENTITY = "{\"description\":\"%s\",\"error\":\"INTERNAL_ERROR\"}";
    private static final int INTERNAL_SERVER_ERROR_CODE = 500;

    @Test
    public void whenMappingAClientAPIException_thenReturnExpectedResponse() {
        String expectedEntity = String.format(EXPECTED_ENTITY, AN_ERROR_MESSAGE);
        ClientAPIExceptionMapper mapper = new ClientAPIExceptionMapper();
        ClientAPIException exception = new ClientAPIException(AN_ERROR_MESSAGE);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(INTERNAL_SERVER_ERROR_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedEntity, mapperResponse.getEntity());
    }
}
