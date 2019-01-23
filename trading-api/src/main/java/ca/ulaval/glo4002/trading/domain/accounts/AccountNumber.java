package ca.ulaval.glo4002.trading.domain.accounts;

public class AccountNumber {
    private static final int INITIALS_POSITION = 0;
    private static final int ID_POSITION = 1;
    private static final String ACCOUNT_NUMBER_SEPARATOR = "-";

    private int id;
    private String initials;

    public AccountNumber(String _accountNumber) {
        String[] splitAccountNumber = _accountNumber.split(ACCOUNT_NUMBER_SEPARATOR);
        initials = splitAccountNumber[INITIALS_POSITION];
        id = Integer.parseInt(splitAccountNumber[ID_POSITION]);
    }

    public AccountNumber(int _id, String _initials) {
        id = _id;
        initials = _initials;
    }

    public int getId() {
        return id;
    }

    public int hashCode() {
        return Integer.hashCode(id);
    }

    public boolean equals(Object _other) {
        if (_other == null) {
            return false;
        }

        if (getClass() != _other.getClass()) {
            return false;
        }

        return ((AccountNumber) _other).id == id && ((AccountNumber) _other).initials.equals(initials);
    }

    public String toString() {
        return initials + ACCOUNT_NUMBER_SEPARATOR + String.valueOf(id);
    }
}
