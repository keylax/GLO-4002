package ca.ulaval.glo4002.trading.infrastructure.repositories.investors;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InvestorEntity {
    @Id
    public long id;
    public String firstName;
    public String lastName;

    public InvestorEntity() {
        //for hibernate
    }

    public InvestorEntity(long _id, String _firstName, String _lastName) {
        id = _id;
        firstName = _firstName;
        lastName = _lastName;
    }

    public String getInitials() {
        return "" + firstName.charAt(0) + lastName.charAt(0);
    }
}
