package ca.ulaval.glo4002.trading.domain.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.transactions.exceptions.InvalidQuantityException;
import ca.ulaval.glo4002.trading.domain.transactions.exceptions.MarketIsClosedException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private static final String ASSOCIATE_AN_ALREADY_ASSOCIATED_TRANSACTION_MESSAGE =
            "An associated transaction cannot be associated to another account.";

    private TransactionNumber transactionNumber;
    private Stock stock;
    private LocalDateTime date;
    private long quantity;
    private AccountNumber accountNumber;
    private TransactionType type;
    private String currency;

    public Transaction(LocalDateTime _date, Stock _stock, long _quantity, Market _market, TransactionType _type) {
        this(new TransactionNumber(), _date, _stock, _quantity, _market, _type, null);
    }

    public Transaction(TransactionNumber _transactionNumber,
                       LocalDateTime _date,
                       Stock _stock,
                       long _quantity,
                       TransactionType _type,
                       AccountNumber _accountNumber) {
        this(_transactionNumber, _date, _stock, _quantity, null, _type, _accountNumber);
    }

    private Transaction(TransactionNumber _transactionNumber,
                        LocalDateTime _date,
                        Stock _stock,
                        long _quantity,
                        Market _market,
                        TransactionType _type,
                        AccountNumber _accountNumber) {
        transactionNumber = new TransactionNumber();

        validateQuantityGreaterThanZero(_quantity);
        if (_market != null) {
            currency = _market.getCurrencySymbol();
            validateOpenHours(_date, _market);
        }

        transactionNumber = _transactionNumber;
        date = _date;
        quantity = _quantity;
        stock = _stock;
        type = _type;
        accountNumber = _accountNumber;
    }

    public BigDecimal calculateRequestedAmount() {
        BigDecimal stockPrice = stock.getPrice();
        return stockPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getUnitPrice() {
        return stock.getPrice();
    }

    public BigDecimal calculateFees(TransactionFeesCalculator _feesCalculator) {
        BigDecimal transactionPrice = calculateRequestedAmount();
        return _feesCalculator.calculateTransactionFees(quantity, transactionPrice);
    }

    public void associateAccount(AccountNumber  _accountNumber) {
        if (isAssociatedToAccount()) {
            throw new IllegalStateException(ASSOCIATE_AN_ALREADY_ASSOCIATED_TRANSACTION_MESSAGE);
        }

        accountNumber = _accountNumber;
    }

    public TransactionType getType() {
        return type;
    }

    public String getStockSymbol() {
        return stock.getSymbol();
    }

    public String getMarketSymbol() {
        return stock.getMarketSymbol();
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getStockPrice() {
        return stock.getPrice();
    }

    public long getQuantity() {
        return quantity;
    }

    public TransactionNumber getTransactionNumber() {
        return transactionNumber;
    }

    public AccountNumber getAssociatedAccountNumber() {
        return accountNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    private boolean isAssociatedToAccount() {
        return accountNumber != null;
    }

    private void validateQuantityGreaterThanZero(long _quantity) {
        if (_quantity <= 0) {
            throw new InvalidQuantityException();
        }
    }

    private void validateOpenHours(LocalDateTime _date, Market _market) {
        if (!_market.isOpen(_date)) {
            throw new MarketIsClosedException(_market, transactionNumber);
        }
    }
}
