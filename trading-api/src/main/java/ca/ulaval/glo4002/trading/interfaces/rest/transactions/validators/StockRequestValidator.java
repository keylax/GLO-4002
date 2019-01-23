package ca.ulaval.glo4002.trading.interfaces.rest.transactions.validators;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.StockRequest;

public class StockRequestValidator {
    public boolean isStockRequestValid(StockRequest _stockRequest) {
        return _stockRequest != null && _stockRequest.market != null && _stockRequest.symbol != null;
    }
}
