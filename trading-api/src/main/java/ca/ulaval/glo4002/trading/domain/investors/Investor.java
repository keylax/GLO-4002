package ca.ulaval.glo4002.trading.domain.investors;

public class Investor {
    private String firstName;
    private String lastName;
    private long id;

    public Investor(long _id,
                    String _firstName,
                    String _lastName) {
        id = _id;
        firstName = _firstName;
        lastName = _lastName;
    }

    public long getInvestorId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInvestorInitials() {
        return firstName.substring(0, 1) + lastName.substring(0, 1);
    }
}
