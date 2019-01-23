package ca.ulaval.glo4002.trading.interfaces.rest.transactions.validators;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;

public class TransactionRequestValidator {
    private StockRequestValidator stockRequestValidator;

    public TransactionRequestValidator(StockRequestValidator _stockRequestValidator) {
        stockRequestValidator = _stockRequestValidator;
    }

    public boolean isTransactionRequestValid(TransactionRequest _transactionRequest) {
        boolean isStockValid = stockRequestValidator.isStockRequestValid(_transactionRequest.stock);
        return isStockValid && _transactionRequest.date != null && _transactionRequest.type != null;
    }
}
