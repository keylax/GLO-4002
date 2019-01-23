package ca.ulaval.glo4002.trading.application.services.accounts.DTOs.responses;

import java.util.List;

public class InvestorProfileResponse {
    public final String type;
    public final List<String> focusAreas;

    public InvestorProfileResponse(String _type, List<String> _focusAreas) {
        type = _type;
        focusAreas = _focusAreas;
    }
}
