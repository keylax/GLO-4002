package ca.ulaval.glo4002.trading.application.services.transactions;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.StockRequest;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionReport;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.validators.TransactionDateValidator;
import ca.ulaval.glo4002.trading.domain.accounts.Account;
import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;
import ca.ulaval.glo4002.trading.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.stocks.StockRepository;
import ca.ulaval.glo4002.trading.domain.transactions.Purchase;
import ca.ulaval.glo4002.trading.domain.transactions.Sale;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionRepository;
import ca.ulaval.glo4002.trading.application.services.transactions.exceptions.TransactionReportInvalidDateException;
import ca.ulaval.glo4002.trading.domain.transactions.exceptions.InvalidDateException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.utilities.DateMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class TransactionService {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private TransactionRepository transactionRepository;
    private TransactionFactory transactionFactory;
    private TransactionResponseAssembler transactionResponseAssembler;
    private TransactionDateValidator transactionDateValidator;
    private StockRepository stockRepository;
    private AccountRepository accountRepository;

    public TransactionService(
            TransactionRepository _transactionRepository,
            TransactionFactory _transactionFactory,
            TransactionResponseAssembler _transactionResponseAssembler,
            TransactionDateValidator _transactionDateValidator,
            StockRepository _stockRepository,
            AccountRepository _accountRepository) {
        transactionRepository = _transactionRepository;
        transactionFactory = _transactionFactory;
        transactionResponseAssembler = _transactionResponseAssembler;
        transactionDateValidator = _transactionDateValidator;
        stockRepository = _stockRepository;
        accountRepository = _accountRepository;
    }

    public UUID performSell(
            TransactionRequest _transactionRequest,
            String _accountNumber,
            UUID _buyTransactionId) {
        AccountNumber accountNumber = new AccountNumber(_accountNumber);
        TransactionNumber buyTransactionNumber = new TransactionNumber(_buyTransactionId);
        validateTransactionDay(_transactionRequest.date);
        LocalDateTime transactionDate = DateMapper.toDateTime(_transactionRequest.date);
        validateTransactionDate(transactionDate);
      
        StockRequest stockRequest = _transactionRequest.stock;
        Stock stock = stockRepository.findStockBy(stockRequest.market, stockRequest.symbol, transactionDate);
        Market market = stockRepository.findMarketBy(stockRequest.market);
        Account account = accountRepository.findAccountByNumber(accountNumber);
        Sale sale = transactionFactory.createSale(_transactionRequest, stock, market);
        account.sellStock(sale, buyTransactionNumber);
        accountRepository.updateAccount(account);

        Transaction transaction = sale.getTransaction();
        transactionRepository.createTransaction(transaction);

        TransactionNumber transactionNumber = transaction.getTransactionNumber();
        return transactionNumber.getNumber();
    }

    public UUID performBuy(TransactionRequest _transactionRequest, String _accountNumber) {
        AccountNumber accountNumber = new AccountNumber(_accountNumber);
        validateTransactionDay(_transactionRequest.date);
        LocalDateTime transactionDate = DateMapper.toDateTime(_transactionRequest.date);
        validateTransactionDate(transactionDate);

        StockRequest stockRequest = _transactionRequest.stock;
        Stock stock = stockRepository.findStockBy(stockRequest.market, stockRequest.symbol, transactionDate);
        Market market = stockRepository.findMarketBy(stockRequest.market);
        Account account = accountRepository.findAccountByNumber(accountNumber);
        Purchase purchase = transactionFactory.createPurchase(_transactionRequest, stock, market);
        account.buyStock(purchase);
        accountRepository.updateAccount(account);

        Transaction transaction = purchase.getTransaction();
        transactionRepository.createTransaction(transaction);

        TransactionNumber transactionNumber = transaction.getTransactionNumber();
        return transactionNumber.getNumber();
    }

    public TransactionResponse consultTransaction(UUID _transactionNumber, String _accountNumber) {
        TransactionNumber transactionNumber = new TransactionNumber(_transactionNumber);
        AccountNumber accountNumber = new AccountNumber(_accountNumber);
        Transaction selectedTransaction = transactionRepository.findTransactionByIdAndAccountNumber(
                transactionNumber,
                accountNumber);

        return transactionResponseAssembler.toResponse(selectedTransaction);
    }

    public TransactionReport consultTransactionDailyReport(String _accountNumber, String _serializedReportDate) {
        AccountNumber accountNumber = new AccountNumber(_accountNumber);
        LocalDate reportDate = tryParseDate(_serializedReportDate);
        validateReportDate(reportDate);
        validateAccountExist(accountNumber);

        LocalDateTime reportDateAtStartOfDay = reportDate.atStartOfDay();
        LocalDateTime reportDateAtEndOfDay = reportDate.atTime(LocalTime.MAX);

        Transaction[] reportTransactions = transactionRepository.findAccountTransactionsInDateRange(
                accountNumber,
                reportDateAtStartOfDay,
                reportDateAtEndOfDay);

        return transactionResponseAssembler.toResponse(reportTransactions, reportDateAtEndOfDay);
    }

    private void validateAccountExist(AccountNumber _accountNumber) {
        if (!accountRepository.isAccountExistent(_accountNumber)) {
            throw new AccountNotFoundException(_accountNumber);
        }
    }

    private void validateReportDate(LocalDate _reportDate) {
        if (!transactionDateValidator.isReportDateValid(_reportDate)) {
            throw new TransactionReportInvalidDateException(_reportDate.toString());
        }
    }

    private void validateTransactionDate(LocalDateTime _transactionDate) {
        int transactionDateYear = _transactionDate.getYear();
        if (!transactionDateValidator.isDateInValidYearRange(transactionDateYear)) {
            throw new InvalidDateException();
        }
    }

    private void validateTransactionDay(String _transactionDate) {
        if (!transactionDateValidator.isDayValid(_transactionDate)) {
            throw new InvalidDateException();
        }
    }

    private LocalDate tryParseDate(String _date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(_date, dateTimeFormatter);
        } catch (DateTimeParseException exception) {
            throw new TransactionReportInvalidDateException(_date);
        }
        return localDate;
    }
}
