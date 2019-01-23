package ca.ulaval.glo4002.trading.domain.investors;

import java.util.List;
import java.util.ArrayList;

public class InvestorProfile {
    private static final String DEFAULT_INVESTOR_TYPE = "CONSERVATIVE";

    private String investorType;
    private List<String> focusArea;

    public InvestorProfile() {
        investorType = DEFAULT_INVESTOR_TYPE;
        focusArea = new ArrayList<>();
    }

    public String getInvestorType() {
        return investorType;
    }

    public List<String> getFocusArea() {
        return focusArea;
    }
}

