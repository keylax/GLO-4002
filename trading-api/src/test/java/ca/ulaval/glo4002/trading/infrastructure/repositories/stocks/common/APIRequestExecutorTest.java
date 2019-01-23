package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.exceptions.ClientAPIException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class APIRequestExecutorTest {
    private static final int HTML_OK_STATUS = 200;
    private static final int HTML_UNAUTHORIZED_STATUS = 401;
    private static final int HTML_FORBIDDEN_STATUS = 403;
    private static final int HTML_NOT_FOUND_STATUS = 404;

    private static final String SOME_SERIALIZED_JSON = "serializedJSON";
    private static final String EXPECTED_RESPONSE_VALUE = "response";

    @Mock
    private ObjectMapper mapper;

    @Mock
    private Builder requestInvocation;

    @Mock
    private Response response;

    private APIRequestExecutor requestExecutor;

    @Before
    public void setup() {
        requestExecutor = new APIRequestExecutor(mapper);
        when(requestInvocation.get()).thenReturn(response);
    }

    @Test(expected = ClientAPIException.class)
    public void givenAResponseWithUnauthorizedStatus_whenExecutingGetRequest_thenThrowClientAPIException() {
        when(response.getStatus()).thenReturn(HTML_UNAUTHORIZED_STATUS);

        requestExecutor.executeGetRequest(requestInvocation, String.class);
    }

    @Test(expected = ClientAPIException.class)
    public void givenAResponseWithForbiddenStatus_whenExecutingGetRequest_thenThrowClientAPIException() {
        when(response.getStatus()).thenReturn(HTML_FORBIDDEN_STATUS);

        requestExecutor.executeGetRequest(requestInvocation, String.class);
    }

    @Test(expected = ClientAPIException.class)
    public void givenAResponseWithNotFoundStatus_whenExecutingGetRequest_thenThrowClientAPIException() {
        when(response.getStatus()).thenReturn(HTML_NOT_FOUND_STATUS);

        requestExecutor.executeGetRequest(requestInvocation, String.class);
    }

    @Test
    public void whenExecutingGetRequest_thenCloseConnectionWhenRequestIsProcessed() {
        when(response.getStatus()).thenReturn(HTML_OK_STATUS);
        when(response.readEntity(String.class)).thenReturn(SOME_SERIALIZED_JSON);

        requestExecutor.executeGetRequest(requestInvocation, String.class);

        verify(response).close();
    }

    @Test
    public void whenExecutingGetRequest_thenReturnExpectedResponseValue() throws IOException {
        when(response.getStatus()).thenReturn(HTML_OK_STATUS);
        when(response.readEntity(String.class)).thenReturn(SOME_SERIALIZED_JSON);
        when(mapper.readValue(SOME_SERIALIZED_JSON, String.class)).thenReturn(EXPECTED_RESPONSE_VALUE);

        String actualResponse = requestExecutor.executeGetRequest(requestInvocation, String.class);

        Assert.assertEquals(EXPECTED_RESPONSE_VALUE, actualResponse);
    }

    @Test(expected = ClientAPIException.class)
    public void whenExecutingGetRequestAndMapperThrowIOException_thenAnExceptionIsThrown() throws IOException {
        when(response.getStatus()).thenReturn(HTML_OK_STATUS);
        when(response.readEntity(String.class)).thenReturn(SOME_SERIALIZED_JSON);
        when(mapper.readValue(SOME_SERIALIZED_JSON, String.class)).thenThrow(new IOException());

        requestExecutor.executeGetRequest(requestInvocation, String.class);
    }
}
