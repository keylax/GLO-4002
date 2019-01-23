package ca.ulaval.glo4002.trading.domain.accounts;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountInvalidAmountException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.InvalidTransactionNumberException;
import ca.ulaval.glo4002.trading.domain.accounts.exceptions.NotEnoughCreditException;
import ca.ulaval.glo4002.trading.domain.investors.Investor;
import ca.ulaval.glo4002.trading.domain.investors.InvestorProfile;
import ca.ulaval.glo4002.trading.domain.transactions.Transaction;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.transactions.Purchase;
import ca.ulaval.glo4002.trading.domain.transactions.Sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private AccountNumber accountNumber;
    private Wallet wallet;
    private List<BoughtStock> boughtStocks;
    private Investor investor;
    private InvestorProfile investorProfile;

    public Account(Investor _investor, Wallet _wallet) {
        this(_investor, _wallet, null, new ArrayList<>());
    }

    public Account(Investor _investor, Wallet _wallet, AccountNumber _accountNumber, List<BoughtStock> _boughtStocks) {
        investor = _investor;
        investorProfile = new InvestorProfile();
        wallet = _wallet;
        accountNumber = _accountNumber;
        boughtStocks = _boughtStocks;
    }
    
    public void buyStock(Purchase _purchase) {
        _purchase.associateAccount(accountNumber);

        tryPurchaseStock(_purchase);

        BoughtStock boughtStock = new BoughtStock(_purchase);
        boughtStocks.add(boughtStock);
    }

    private void tryPurchaseStock(Purchase _purchase) {
        BigDecimal totalPrice = _purchase.calculateTotalPrice();
        Transaction purchaseTransaction = _purchase.getTransaction();
        try {
            wallet.removeCredits(totalPrice, purchaseTransaction.getCurrency());
        } catch (AccountInvalidAmountException exception) {
            TransactionNumber buyTransactionNumber = purchaseTransaction.getTransactionNumber();
            throw new NotEnoughCreditException(buyTransactionNumber);
        }
    }

    public void sellStock(Sale _sale, TransactionNumber _buyTransactionId) {
        _sale.associateAccount(accountNumber);
        Transaction saleTransaction = _sale.getTransaction();

        BoughtStock searchedStock = findBoughStock(_sale, _buyTransactionId);
        searchedStock.sellStock(_sale);

        BigDecimal calculatedTotalPrice = _sale.calculateTotalPrice();
        wallet.addCredits(calculatedTotalPrice, saleTransaction.getCurrency());
    }

    private BoughtStock findBoughStock(Sale _sale, TransactionNumber _buyTransactionId) {
        Transaction saleTransaction = _sale.getTransaction();
        for (BoughtStock boughtStock: boughtStocks) {
            TransactionNumber buyTransactionNumber = boughtStock.getTransactionNumber();
            if (buyTransactionNumber.equals(_buyTransactionId)) {
                return boughtStock;
            }
        }

        TransactionNumber saleTransactionNumber = saleTransaction.getTransactionNumber();
        throw new InvalidTransactionNumberException(saleTransactionNumber);
    }

    public String getInvestorInitials() {
        return investor.getInvestorInitials();
    }

    public InvestorProfile getInvestorProfile() {
        return investorProfile;
    }

    public List<WalletCurrency> getCurrencies() {
        return wallet.getWalletCurrencies();
    }

    public BigDecimal getTotalCredits() {
        return wallet.getTotalCredits();
    }

    public long getInvestorId() {
        return investor.getInvestorId();
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public List<BoughtStock> getBoughtStocks() {
        return boughtStocks;
    }

    public Investor getInvestor() {
        return investor;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
