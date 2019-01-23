package ca.ulaval.glo4002.trading.interfaces.rest;

public class AccountsPaths {
    public static final String ROOT = "/accounts";
    public static final String ACCOUNT_NUMBER_ARGUMENT = "accountNumber";
    public static final String TRANSACTION_NUMBER_ARGUMENT = "transactionNumber";
    public static final String ACCOUNT_NUMBER = "{" + ACCOUNT_NUMBER_ARGUMENT + "}";
    public static final String TRANSACTIONS = "transactions";
    public static final String REPORTS = "reports";
    public static final String ACCOUNT_TRANSACTIONS = ACCOUNT_NUMBER + "/" + TRANSACTIONS;
    public static final String ACCOUNT_TRANSACTION = ACCOUNT_TRANSACTIONS + "/{" + TRANSACTION_NUMBER_ARGUMENT + "}";
    public static final String ACCOUNT_TRANSACTIONS_HISTORY = ACCOUNT_NUMBER + "/" + REPORTS;
}
