package ca.ulaval.glo4002.trading.interfaces.configuration;

import ca.ulaval.glo4002.trading.application.services.accounts.AccountFactory;
import ca.ulaval.glo4002.trading.application.services.transactions.TransactionFactory;
import ca.ulaval.glo4002.trading.application.services.transactions.validators.TransactionDateValidator;
import ca.ulaval.glo4002.trading.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.trading.domain.stocks.StockRepository;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionRepository;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.HibernateAccountRepository;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.HibernateTransactionRepository;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.APIRequestExecutor;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.configurations.ClientAPIInitializer;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.StockClientAPI;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers.StockAssembler;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.requestBuilders.StockAPIInvocationBuilder;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.validators.StockDTOValidator;
import ca.ulaval.glo4002.trading.interfaces.rest.accounts.AccountResource;
import ca.ulaval.glo4002.trading.application.services.accounts.AccountResponseAssembler;
import ca.ulaval.glo4002.trading.interfaces.rest.accounts.validators.AccountRequestValidator;
import ca.ulaval.glo4002.trading.application.services.transactions.TransactionResponseAssembler;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.ReportResource;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.TransactionResource;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.validators.StockRequestValidator;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.validators.TransactionRequestValidator;
import ca.ulaval.glo4002.trading.application.services.accounts.AccountService;
import ca.ulaval.glo4002.trading.application.services.transactions.TransactionService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;

public class Context {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private StockRepository stockRepository;
    private LocalDate appContextDate;

    public Context(ObjectMapper _objectMapper, LocalDate _appContextDate) {
        accountRepository = new HibernateAccountRepository();
        transactionRepository = new HibernateTransactionRepository();
        stockRepository = createStockRepository(_objectMapper);
        appContextDate = _appContextDate;
    }

    public AccountResource createAccountResource() {       
        AccountRequestValidator accountRequestValidator = new AccountRequestValidator();
        AccountFactory accountFactory = new AccountFactory();
        AccountResponseAssembler accountResponseAssembler = new AccountResponseAssembler();
        AccountService accountService = new AccountService(accountRepository, accountFactory, accountResponseAssembler);

        return new AccountResource(accountService, accountRequestValidator);
    }

    public TransactionResource createTransactionResource() {
        StockRequestValidator stockRequestValidator = new StockRequestValidator();
        TransactionRequestValidator transactionRequestValidator = new TransactionRequestValidator(
                stockRequestValidator);
        TransactionFactory transactionFactory = new TransactionFactory();
        TransactionResponseAssembler transactionResponseAssembler = new TransactionResponseAssembler();
        TransactionDateValidator transactionDateValidator = new TransactionDateValidator(appContextDate);

        TransactionService transactionService = new TransactionService(
                transactionRepository,
                transactionFactory,
                transactionResponseAssembler,
                transactionDateValidator,
                stockRepository,
                accountRepository);

        return new TransactionResource(transactionService, transactionRequestValidator);
    }

    public ReportResource createReportResource() {
        TransactionFactory transactionFactory = new TransactionFactory();
        TransactionResponseAssembler transactionResponseAssembler = new TransactionResponseAssembler();
        TransactionDateValidator transactionDateValidator = new TransactionDateValidator(appContextDate);

        TransactionService transactionService = new TransactionService(
                transactionRepository,
                transactionFactory,
                transactionResponseAssembler,
                transactionDateValidator,
                stockRepository,
                accountRepository);

        return new ReportResource(transactionService);
    }

    private ObjectMapper setupObjectMapper(ObjectMapper _objectMapper) {
        _objectMapper.registerModule(new JavaTimeModule());
        _objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return _objectMapper;
    }

    private StockRepository createStockRepository(ObjectMapper _objectMapper) {
        ObjectMapper objectMapper = setupObjectMapper(_objectMapper);

        StockDTOValidator stockDTOValidator = new StockDTOValidator();
        StockAssembler stockAssembler = new StockAssembler();
        ClientAPIInitializer clientAPIInitializer = new ClientAPIInitializer();
        APIRequestExecutor apiRequestExecutor = new APIRequestExecutor(objectMapper);
        StockAPIInvocationBuilder stockAPIInvocationBuilder = new StockAPIInvocationBuilder();
        return new StockClientAPI(
                clientAPIInitializer,
                apiRequestExecutor,
                stockAPIInvocationBuilder,
                stockAssembler,
                stockDTOValidator);
    }
}
