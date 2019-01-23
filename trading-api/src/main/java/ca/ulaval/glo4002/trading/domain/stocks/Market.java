package ca.ulaval.glo4002.trading.domain.stocks;

import java.time.*;
import java.util.List;

import ca.ulaval.glo4002.trading.utilities.DateMapper;

public class Market {
    private String currencySymbol;
    private ZoneId timezone;
    private List<OpenHours> openHours;
    private String symbol;

    public Market(String _symbol, List<OpenHours> _openHours, ZoneId _timezone) {
        openHours = _openHours;
        timezone = _timezone;
        symbol = _symbol;
        setCurrencySymbol(_symbol);
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isOpen(LocalDateTime _date) {
        Instant dateInstant = DateMapper.toInstant(_date);

        LocalDateTime zonedDate = LocalDateTime.ofInstant(dateInstant, timezone);

        return !isWeekend(zonedDate) && isOpenHours(zonedDate);
    }

    private boolean isWeekend(LocalDateTime _date) {
        DayOfWeek dayOfWeek = _date.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }

    private boolean isOpenHours(LocalDateTime _date) {
        LocalTime dateTime = _date.toLocalTime();
        for (OpenHours hours : openHours) {
            LocalTime openHour = hours.getOpenTime();
            LocalTime closeHour = hours.getCloseTime();
            if (dateTime.compareTo(openHour) >= 0 && dateTime.compareTo(closeHour) <= 0) {
                return true;
            }
        }
        return false;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    private void setCurrencySymbol(String _symbol) {
        if (_symbol.equals("NASDAQ") || _symbol.equals("NYSE")) {
            currencySymbol = "USD";
        } else if (_symbol.equals("XTKS")) {
            currencySymbol = "JPY";
        } else if (_symbol.equals("XSWX")) {
            currencySymbol = "CHF";
        }
    }
}
