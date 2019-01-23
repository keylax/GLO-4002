package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.exceptions.ClientAPIException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

public class APIRequestExecutor {
    private static final String NOT_FOUND_MESSAGE_EXCEPTION = "The API is not able to find the requested data";
    private static final String UNAUTHORIZED_MESSAGE_EXCEPTION =
            "The client API requires authentication to access data from server-side API";
    private static final String FORBIDDEN_MESSAGE_EXCEPTION =
            "The client API doesn't possess the required authorization to access data from server-side API";

    private static final int HTML_UNAUTHORIZED_STATUS = 401;
    private static final int HTML_FORBIDDEN_STATUS = 403;
    private static final int HTML_NOT_FOUND_STATUS = 404;

    private ObjectMapper mapper;

    public APIRequestExecutor(ObjectMapper _mapper) {
        mapper = _mapper;
    }

    public <ClassType> ClassType executeGetRequest(Builder _apiInvocation, Class<ClassType> _classPrototype) {
        Response apiResponse = _apiInvocation.get();
        handleAPIErrorsReception(apiResponse.getStatus());
        return generateClassObject(apiResponse, _classPrototype);
    }

    private <ClassType> ClassType generateClassObject(Response _apiResponse, Class<ClassType> _classPrototype) {
        String serializedJSON = _apiResponse.readEntity(String.class);
        _apiResponse.close();
        return generateClassObject(serializedJSON, _classPrototype);
    }

    private void handleAPIErrorsReception(int _htmlStatus) {
        if (_htmlStatus == HTML_UNAUTHORIZED_STATUS) {
            throw new ClientAPIException(UNAUTHORIZED_MESSAGE_EXCEPTION);
        }

        if (_htmlStatus == HTML_FORBIDDEN_STATUS) {
            throw new ClientAPIException(FORBIDDEN_MESSAGE_EXCEPTION);
        }

        if (_htmlStatus == HTML_NOT_FOUND_STATUS) {
            throw new ClientAPIException(NOT_FOUND_MESSAGE_EXCEPTION);
        }
    }

    private <ClassType> ClassType generateClassObject(String _serializedJSON, Class<ClassType> _classPrototype) {
        ClassType genericObject;
        try {
            genericObject = mapper.readValue(_serializedJSON, _classPrototype);
        } catch (IOException exception) {
            throw new ClientAPIException(NOT_FOUND_MESSAGE_EXCEPTION);
        }
        return genericObject;
    }
}
