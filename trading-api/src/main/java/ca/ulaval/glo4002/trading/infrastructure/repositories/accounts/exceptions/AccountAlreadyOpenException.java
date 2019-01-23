package ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions;

public class AccountAlreadyOpenException extends RuntimeException {
    private long investorId;

    public AccountAlreadyOpenException(long _investorId) {
        investorId = _investorId;
    }

    public long getInvestorId() {
        return investorId;
    }
}
