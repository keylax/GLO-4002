package ca.ulaval.glo4002.trading.application.services.transactions;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.*;
import ca.ulaval.glo4002.trading.domain.transactions.*;
import ca.ulaval.glo4002.trading.utilities.DateMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionResponseAssembler {
    private static final String TRANSACTION_TYPE_UNSUPPORTED =
            "The transaction type is not supported by the assembler.";

    private TransactionFeesCalculator transactionFeesCalculator;

    public TransactionResponseAssembler() {
        transactionFeesCalculator = new TransactionFeesCalculator();
    }

    public TransactionResponse toResponse(Transaction _transaction) {
        StockResponse stockResponse = new StockResponse(_transaction.getMarketSymbol(), _transaction.getStockSymbol());
        TransactionNumber transactionNumber = _transaction.getTransactionNumber();
        BigDecimal calculatedFees = _transaction.calculateFees(transactionFeesCalculator);
        BigDecimal unitPrice = _transaction.getUnitPrice();
        LocalDateTime dateTransaction = _transaction.getDate();

        if (_transaction.getType().equals(Purchase.TRANSACTION_TYPE)) {
            return new TransactionBuyResponse(
                    transactionNumber.getNumber(),
                    DateMapper.toInstant(dateTransaction),
                    calculatedFees.floatValue(),
                    unitPrice.floatValue(),
                    _transaction.getQuantity(),
                    stockResponse);
        } else if (_transaction.getType().equals(Sale.TRANSACTION_TYPE)) {
            return new TransactionSellResponse(
                    transactionNumber.getNumber(),
                    DateMapper.toInstant(dateTransaction),
                    calculatedFees.floatValue(),
                    unitPrice.floatValue(),
                    _transaction.getQuantity(),
                    stockResponse);
        }

        throw new UnsupportedOperationException(TRANSACTION_TYPE_UNSUPPORTED);
    }

    public TransactionReport toResponse(Transaction[] _transactions, LocalDateTime _dateReport) {
        Instant instantDateReport = DateMapper.toInstant(_dateReport);
        TransactionResponse[] displayTransactionResponses = toResponses(_transactions);
        return new TransactionReport(instantDateReport, displayTransactionResponses);
    }

    public TransactionResponse[] toResponses(Transaction[] _transactions) {
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction : _transactions) {
            TransactionResponse transactionResponse = toResponse(transaction);
            transactionResponses.add(transactionResponse);
        }

        TransactionResponse[] responses = new TransactionResponse[transactionResponses.size()];
        responses = transactionResponses.toArray(responses);
        return responses;
    }
}
