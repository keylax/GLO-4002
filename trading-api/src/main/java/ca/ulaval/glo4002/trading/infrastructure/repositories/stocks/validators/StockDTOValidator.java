package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.validators;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.MarketDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.PriceDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.StockDTO;

public class StockDTOValidator {
    public boolean isStockDTOValid(StockDTO _stockDTO) {
        boolean isValid = _stockDTO.symbol != null && _stockDTO.market != null &&  _stockDTO.prices != null;

        if (isValid) {
            for (PriceDTO priceDTO: _stockDTO.prices) {
                if (!isPriceDTOValid(priceDTO)) {
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    public boolean isPriceDTOValid(PriceDTO _priceDTO) {
        return _priceDTO.date != null && _priceDTO.price != null;
    }

    public boolean isMarketDTOValid(MarketDTO _marketDTO) {
        return _marketDTO.openHours != null && _marketDTO.symbol != null && _marketDTO.timezone != null;
    }
}
