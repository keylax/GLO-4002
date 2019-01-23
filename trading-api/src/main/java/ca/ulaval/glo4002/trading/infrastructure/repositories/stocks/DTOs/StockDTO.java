package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs;

import java.util.List;

public class StockDTO {
    public Integer id;
    public String symbol;
    public String type;
    public String market;
    public List<PriceDTO> prices;
}
