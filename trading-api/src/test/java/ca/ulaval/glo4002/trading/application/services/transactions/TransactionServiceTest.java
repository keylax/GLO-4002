package ca.ulaval.glo4002.trading.application.services.transactions;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.TransactionRequest;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.StockResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionBuyResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionReport;
import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses.TransactionResponse;
import ca.ulaval.glo4002.trading.application.services.transactions.exceptions.TransactionReportInvalidDateException;
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
import ca.ulaval.glo4002.trading.domain.transactions.exceptions.InvalidDateException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.utilities.DateMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    private static final String A_VALID_SERIALIZED_REPORT_DATE = "2018-07-08";
    private static final String EXPECTED_SERIALIZED_START_OF_DAY_REPORT_DATE = "2018-07-08T00:00";
    private static final String EXPECTED_SERIALIZED_END_OF_DAY_REPORT_DATE = "2018-07-08T23:59:59.999999999";
    private static final String A_INVALID_SERIALIZED_REPORT_DATE = "anInvalidDate";
    private static final UUID A_TRANSACTION_UUID = UUID.fromString("4325a580-ff0e-4b51-8339-9ef443ccd719");
    private static final String AN_ACCOUNT_NUMBER_STRING = "AE-1";
    private static final String A_SERIALIZED_DATE = "2018-07-08T23:59:59.999Z";
    private static final String A_MARKET = "aMarket";
    private static final String A_SYMBOL = "aSymbol";
    private static final long A_QUANTITY = 2;
    private static final float A_TRANSACTION_PRICE = 2.99f;
    private static final float SOME_TRANSACTION_FEES = 2.99f;
    private static final int A_DATE_YEAR = 2018;

    private TransactionService transactionService;
    private Date aDate;
    private LocalDateTime aTransactionDate;
    private AccountNumber anAccountNumber;

    @Mock
    private TransactionFactory transactionFactory;

    @Mock
    private TransactionResponseAssembler transactionResponseAssembler;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionDateValidator transactionDateValidator;

    @Mock
    private Account anAccount;

    @Mock
    private Transaction aTransaction;

    @Mock
    private Sale aSale;

    @Mock
    private Purchase aPurchase;

    @Mock
    private Stock aStock;

    @Mock
    private Market aMarket;

    @Mock
    private StockRepository stockRepository;

    @Before
    public void setup() {
        aDate = DateMapper.toDate(A_SERIALIZED_DATE);
        anAccountNumber = new AccountNumber(AN_ACCOUNT_NUMBER_STRING);
        aTransactionDate = DateMapper.toDateTime(aDate);
        transactionService = new TransactionService(
                transactionRepository,
                transactionFactory,
                transactionResponseAssembler,
                transactionDateValidator,
                stockRepository,
                accountRepository);
        when(aMarket.isOpen(any())).thenReturn(true);
    }

    @Test(expected = InvalidDateException.class)
    public void whenSellingStockAndTransactionDateIsInInvalidYearRange_thenAnExceptionIsThrown() {
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(false);

        transactionService.performSell(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING, A_TRANSACTION_UUID);
    }

    @Test(expected = InvalidDateException.class)
    public void whenBuyingStockAndTransactionDateIsInInvalidYearRange_thenAnExceptionIsThrown() {
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(false);

        transactionService.performBuy(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING);
    }

    @Test
    public void whenSellingStock_thenAccountSellStock() {
        TransactionNumber aTransactionNumber = new TransactionNumber(UUID.randomUUID());
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(true);
        when(transactionDateValidator.isDayValid(A_SERIALIZED_DATE)).thenReturn(true);
        when(transactionFactory.createSale(aTransactionRequest, aStock, aMarket)).thenReturn(aSale);
        when(aSale.getTransaction()).thenReturn(aTransaction);
        when(aTransaction.getTransactionNumber()).thenReturn(aTransactionNumber);
        arrangeRepositoriesBehavior();

        transactionService.performSell(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING, A_TRANSACTION_UUID);

        verify(anAccount).sellStock(aSale, new TransactionNumber(A_TRANSACTION_UUID));
    }

    @Test
    public void whenSellingStock_thenTransactionIsPersisted() {
        TransactionNumber aTransactionNumber = new TransactionNumber(UUID.randomUUID());
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(true);
        when(transactionDateValidator.isDayValid(A_SERIALIZED_DATE)).thenReturn(true);
        when(transactionFactory.createSale(aTransactionRequest, aStock, aMarket)).thenReturn(aSale);
        when(aSale.getTransaction()).thenReturn(aTransaction);
        when(aTransaction.getTransactionNumber()).thenReturn(aTransactionNumber);
        arrangeRepositoriesBehavior();

        transactionService.performSell(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING, A_TRANSACTION_UUID);

        verify(transactionRepository).createTransaction(aTransaction);
    }

    @Test
    public void whenSellingStock_thenReturnTransactionNumber() {
        TransactionNumber expectedTransactionNumber = new TransactionNumber();
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(true);
        when(transactionDateValidator.isDayValid(A_SERIALIZED_DATE)).thenReturn(true);
        when(transactionFactory.createSale(aTransactionRequest, aStock, aMarket)).thenReturn(aSale);
        when(aSale.getTransaction()).thenReturn(aTransaction);
        when(aTransaction.getTransactionNumber()).thenReturn(expectedTransactionNumber);
        arrangeRepositoriesBehavior();

        UUID actualTransactionNumber = transactionService.performSell(
                aTransactionRequest,
                AN_ACCOUNT_NUMBER_STRING,
                A_TRANSACTION_UUID);

        Assert.assertEquals(expectedTransactionNumber.getNumber(), actualTransactionNumber);
    }

    @Test
    public void whenSellingStock_thenTheAccountIsUpdated() {
        TransactionNumber expectedTransactionNumber = new TransactionNumber();
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(true);
        when(transactionDateValidator.isDayValid(A_SERIALIZED_DATE)).thenReturn(true);
        when(transactionFactory.createSale(aTransactionRequest, aStock, aMarket)).thenReturn(aSale);
        when(aSale.getTransaction()).thenReturn(aTransaction);
        when(aTransaction.getTransactionNumber()).thenReturn(expectedTransactionNumber);
        arrangeRepositoriesBehavior();

        transactionService.performSell(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING, A_TRANSACTION_UUID);

        verify(accountRepository).updateAccount(anAccount);
    }

    @Test
    public void whenBuyingStock_thenAccountBuyStock() {
        TransactionNumber aTransactionNumber = new TransactionNumber(UUID.randomUUID());
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(true);
        when(transactionDateValidator.isDayValid(A_SERIALIZED_DATE)).thenReturn(true);
        when(transactionFactory.createPurchase(aTransactionRequest, aStock, aMarket)).thenReturn(aPurchase);
        when(aPurchase.getTransaction()).thenReturn(aTransaction);
        when(aTransaction.getTransactionNumber()).thenReturn(aTransactionNumber);
        arrangeRepositoriesBehavior();

        transactionService.performBuy(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING);

        verify(anAccount).buyStock(aPurchase);
    }

    @Test
    public void whenBuyingStock_thenTransactionIsPersisted() {
        TransactionNumber aTransactionNumber = new TransactionNumber(UUID.randomUUID());
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(true);
        when(transactionDateValidator.isDayValid(A_SERIALIZED_DATE)).thenReturn(true);
        when(transactionFactory.createPurchase(aTransactionRequest, aStock, aMarket)).thenReturn(aPurchase);
        when(aPurchase.getTransaction()).thenReturn(aTransaction);
        when(aTransaction.getTransactionNumber()).thenReturn(aTransactionNumber);
        arrangeRepositoriesBehavior();

        transactionService.performBuy(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING);

        verify(transactionRepository).createTransaction(aTransaction);
    }

    @Test
    public void whenBuyingStock_thenReturnTransactionNumber() {
        TransactionNumber expectedTransactionNumber = new TransactionNumber();
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(true);
        when(transactionDateValidator.isDayValid(A_SERIALIZED_DATE)).thenReturn(true);
        when(transactionFactory.createPurchase(aTransactionRequest, aStock, aMarket)).thenReturn(aPurchase);
        when(aPurchase.getTransaction()).thenReturn(aTransaction);
        when(aTransaction.getTransactionNumber()).thenReturn(expectedTransactionNumber);
        arrangeRepositoriesBehavior();

        UUID actualTransactionNumber = transactionService.performBuy(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING);

        Assert.assertEquals(expectedTransactionNumber.getNumber(), actualTransactionNumber);
    }

    @Test
    public void whenBuyingStock_thenTheAccountIsUpdated() {
        TransactionNumber expectedTransactionNumber = new TransactionNumber();
        TransactionRequest aTransactionRequest = createATransactionRequest();
        when(transactionDateValidator.isDateInValidYearRange(A_DATE_YEAR)).thenReturn(true);
        when(transactionDateValidator.isDayValid(A_SERIALIZED_DATE)).thenReturn(true);
        when(transactionFactory.createPurchase(aTransactionRequest, aStock, aMarket)).thenReturn(aPurchase);
        when(aPurchase.getTransaction()).thenReturn(aTransaction);
        when(aTransaction.getTransactionNumber()).thenReturn(expectedTransactionNumber);
        arrangeRepositoriesBehavior();

        transactionService.performBuy(aTransactionRequest, AN_ACCOUNT_NUMBER_STRING);

        verify(accountRepository).updateAccount(anAccount);
    }

    @Test
    public void whenConsultingATransaction_thenReturnExpectedTransaction() {
        TransactionResponse expectedTransactionResponse = createATransactionResponse();
        when(transactionResponseAssembler.toResponse(aTransaction)).thenReturn(expectedTransactionResponse);
        when(transactionRepository.findTransactionByIdAndAccountNumber(any(TransactionNumber.class),
                                                                      eq(anAccountNumber)))
                .thenReturn(aTransaction);

        TransactionResponse actualTransaction = transactionService.consultTransaction(
                A_TRANSACTION_UUID,
                AN_ACCOUNT_NUMBER_STRING);

        Assert.assertEquals(expectedTransactionResponse, actualTransaction);
    }

    @Test(expected = TransactionReportInvalidDateException.class)
    public void givenAInvalidSerializedDate_whenConsultingTransactionDailyReport_thenAnExceptionIsThrown() {
        transactionService.consultTransactionDailyReport(AN_ACCOUNT_NUMBER_STRING, A_INVALID_SERIALIZED_REPORT_DATE);
    }

    @Test(expected = TransactionReportInvalidDateException.class)
    public void whenConsultingTransactionDailyReportAndDateValidatorInvalidateDate_thenAnExceptionIsThrown() {
        LocalDate expectedReportDate = LocalDate.parse(A_VALID_SERIALIZED_REPORT_DATE);
        when(transactionDateValidator.isReportDateValid(expectedReportDate)).thenReturn(false);

        transactionService.consultTransactionDailyReport(AN_ACCOUNT_NUMBER_STRING, A_VALID_SERIALIZED_REPORT_DATE);
    }

    @Test(expected = AccountNotFoundException.class)
    public void whenConsultingTransactionDailyReportAndAccountIsNotExistent_thenAnExceptionIsThrown() {
        LocalDate expectedReportDate = LocalDate.parse(A_VALID_SERIALIZED_REPORT_DATE);
        when(transactionDateValidator.isReportDateValid(expectedReportDate)).thenReturn(true);
        when(accountRepository.isAccountExistent(anAccountNumber)).thenReturn(false);

        transactionService.consultTransactionDailyReport(AN_ACCOUNT_NUMBER_STRING, A_VALID_SERIALIZED_REPORT_DATE);
    }

    @Test
    public void whenConsultingTransactionDailyReport_thenReturnExpectedTransactionReport() {
        Transaction[] transactions = {};
        LocalDate expectedReportDate = LocalDate.parse(A_VALID_SERIALIZED_REPORT_DATE);
        LocalDateTime expectedDateAtStartOfDay = LocalDateTime.parse(EXPECTED_SERIALIZED_START_OF_DAY_REPORT_DATE);
        LocalDateTime expectedDateAtEndOfDay = LocalDateTime.parse(EXPECTED_SERIALIZED_END_OF_DAY_REPORT_DATE);
        TransactionReport expectedTransactionReport = createATransactionReport();
        when(transactionRepository.findAccountTransactionsInDateRange(anAccountNumber, expectedDateAtStartOfDay,
                expectedDateAtEndOfDay)).thenReturn(transactions);
        when(accountRepository.isAccountExistent(anAccountNumber)).thenReturn(true);
        when(transactionResponseAssembler.toResponse(transactions, expectedDateAtEndOfDay))
                .thenReturn(expectedTransactionReport);
        when(transactionDateValidator.isReportDateValid(expectedReportDate)).thenReturn(true);
        when(transactionDateValidator.isDayValid(EXPECTED_SERIALIZED_START_OF_DAY_REPORT_DATE)).thenReturn(true);
        when(transactionDateValidator.isDayValid(EXPECTED_SERIALIZED_END_OF_DAY_REPORT_DATE)).thenReturn(true);

        TransactionReport actualTransactionReport = transactionService.consultTransactionDailyReport(
                AN_ACCOUNT_NUMBER_STRING,
                A_VALID_SERIALIZED_REPORT_DATE);

        Assert.assertEquals(expectedTransactionReport, actualTransactionReport);
    }

    private TransactionReport createATransactionReport() {
        TransactionResponse transactionResponse = createATransactionResponse();
        TransactionResponse[] transactionResponses = {transactionResponse};
        return new TransactionReport(Instant.now(), transactionResponses);
    }

    private TransactionResponse createATransactionResponse() {
        StockResponse aStockResponse = new StockResponse(A_MARKET, A_SYMBOL);
        return new TransactionBuyResponse(
                A_TRANSACTION_UUID,
                aDate.toInstant(),
                SOME_TRANSACTION_FEES,
                A_TRANSACTION_PRICE,
                A_QUANTITY,
                aStockResponse);
    }

    private void arrangeRepositoriesBehavior() {
        when(accountRepository.findAccountByNumber(anAccountNumber)).thenReturn(anAccount);
        when(stockRepository.findStockBy(A_MARKET, A_SYMBOL, aTransactionDate)).thenReturn(aStock);
        when(stockRepository.findMarketBy(A_MARKET)).thenReturn(aMarket);
    }

    private TransactionRequest createATransactionRequest() {
        return TransactionTestHelper.createATransactionRequest(A_SERIALIZED_DATE, A_MARKET, A_SYMBOL);
    }
}
