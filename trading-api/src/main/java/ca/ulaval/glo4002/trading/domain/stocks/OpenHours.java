package ca.ulaval.glo4002.trading.domain.stocks;

import java.time.LocalTime;

public class OpenHours {
    private LocalTime openTime;
    private LocalTime closeTime;

    public OpenHours(LocalTime _openTime, LocalTime _closeTime) {
        openTime = _openTime;
        closeTime = _closeTime;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }
}
