package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers;

import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.stocks.OpenHours;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.MarketDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.PriceDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.StockDTO;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.utilities.DateMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class StockAssembler {
    private static final String OPENED_TIME_SEPARATOR = "-";
    private static final String HOUR_MINUTE_SEPARATOR = ":";

    private StockPriceFinder stockPriceFinder;

    public StockAssembler() {
        stockPriceFinder = new StockPriceFinder();
    }

    public Stock toDomainObject(StockDTO _stockDTO, String _market, LocalDateTime _date) {
        ArrayList<StockPrice> stockPrices = new ArrayList<>();
        for (PriceDTO priceDTO : _stockDTO.prices) {
            StockPrice stockPrice = toStockPrice(priceDTO);
            stockPrices.add(stockPrice);
        }

        BigDecimal stockPrice = stockPriceFinder.findPriceAtDate(stockPrices, _date);
        return new Stock(_stockDTO.symbol, _market, stockPrice);
    }

    public StockPrice toStockPrice(PriceDTO _priceDTO) {
        LocalDateTime dateTime = DateMapper.toDateTime(_priceDTO.date);
        return new StockPrice(dateTime, _priceDTO.price);
    }

    public Market toDomainObject(MarketDTO _marketDTO) {
        ZoneId timezone = ZoneId.of(_marketDTO.timezone);

        ArrayList<OpenHours> openHours = new ArrayList<>();
        for (String openedTimePair : _marketDTO.openHours) {
            String[] openedTime = openedTimePair.split(OPENED_TIME_SEPARATOR);

            LocalTime openTime = getLocalTime(openedTime[0]);
            LocalTime closeTime = getLocalTime(openedTime[1]);
            OpenHours openHour = new OpenHours(openTime, closeTime);

            openHours.add(openHour);
        }

        return new Market(_marketDTO.symbol, openHours, timezone);
    }

    private LocalTime getLocalTime(String _time) {
        String[] openHourMinute = _time.split(HOUR_MINUTE_SEPARATOR);
        int openHour = Integer.parseInt(openHourMinute[0]);
        int openMinute = Integer.parseInt(openHourMinute[1]);
        return LocalTime.of(openHour, openMinute);
    }
}
