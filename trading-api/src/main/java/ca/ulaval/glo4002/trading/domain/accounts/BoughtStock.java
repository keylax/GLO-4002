package ca.ulaval.glo4002.trading.domain.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.NotEnoughStockException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.StockParametersDontMatchException;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.transactions.Purchase;
import ca.ulaval.glo4002.trading.domain.transactions.Sale;

public class BoughtStock {
    private String market;
    private String symbol;
    private long quantity;
    private TransactionNumber transactionNumber;

    public BoughtStock(Purchase _purchase) {
        Transaction purchaseTransaction = _purchase.getTransaction();
        market = purchaseTransaction.getMarketSymbol();
        symbol = purchaseTransaction.getStockSymbol();
        quantity = purchaseTransaction.getQuantity();
        transactionNumber = purchaseTransaction.getTransactionNumber();
    }

    public BoughtStock(String _market, String _symbol, long _quantity, TransactionNumber _transactionNumber) {
        market = _market;
        symbol = _symbol;
        quantity = _quantity;
        transactionNumber = _transactionNumber;
    }

    public void sellStock(Sale _sale) {
        Transaction saleTransaction = _sale.getTransaction();
        validateStockParameters(_sale);
        validateQuantity(_sale);
        quantity -= saleTransaction.getQuantity();
    }

    public String getMarket() {
        return market;
    }

    public String getSymbol() {
        return symbol;
    }

    public long getQuantity() {
        return quantity;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    private void validateQuantity(Sale _sale) {
        Transaction saleTransaction = _sale.getTransaction();
        if (saleTransaction.getQuantity() > quantity) {
            throw new NotEnoughStockException(saleTransaction.getMarketSymbol(), saleTransaction.getStockSymbol());
        }
    }

    private void validateStockParameters(Sale _sale) {
        Transaction saleTransaction = _sale.getTransaction();
        String transactionMarket = saleTransaction.getMarketSymbol();
        String transactionSymbol = saleTransaction.getStockSymbol();
        if (!isSameStock(transactionMarket, transactionSymbol)) {
            throw new StockParametersDontMatchException(saleTransaction.getTransactionNumber());
        }
    }

    private boolean isSameStock(String _market, String _symbol) {
        return market.equals(_market) && symbol.equals(_symbol);
    }
}
