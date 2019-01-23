package ca.ulaval.glo4002.trading.domain.transactions.exceptions;

import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;

public class MarketIsClosedException extends RuntimeException {
    private Market market;
    private TransactionNumber transactionNumber;

    public MarketIsClosedException(Market _market, TransactionNumber _transactionNumber) {
        market = _market;
        transactionNumber = _transactionNumber;
    }

    public String getMarket() {
        return market.getSymbol();
    }

    public String getTransactionNumber() {
        return transactionNumber.toString();
    }
}
