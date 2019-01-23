package ca.ulaval.glo4002.trading.interfaces.rest.mappers.response;

import org.json.JSONObject;

public class JsonResponse {
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String DESCRIPTION_ATTRIBUTE = "description";
    private static final String TRANSACTION_NUMBER_ATTRIBUTE = "transactionNumber";

    public String buildExceptionError(String _error, String _description) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ERROR_ATTRIBUTE, _error);
        jsonObject.put(DESCRIPTION_ATTRIBUTE, _description);
        return jsonObject.toString();
    }

    public String buildTransactionExceptionError(String _error, String _description, String _transactionNumber) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ERROR_ATTRIBUTE, _error);
        jsonObject.put(DESCRIPTION_ATTRIBUTE, _description);
        jsonObject.put(TRANSACTION_NUMBER_ATTRIBUTE, _transactionNumber);
        return jsonObject.toString();
    }
}
