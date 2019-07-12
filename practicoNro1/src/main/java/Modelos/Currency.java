package Modelos;

import com.google.gson.annotations.SerializedName;

public class Currency {
    @SerializedName("id")
    String currency_id;
    String symbol;
    String description;
    String decimal_places;

    public Currency(String id, String symbol, String description, String decimal_places) {
        this.currency_id = id;
        this.symbol = symbol;
        this.description = description;
        this.decimal_places = decimal_places;
    }

    public String currency_id() {
        return currency_id;
    }

    public void currency_id(String id) {
        this.currency_id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDecimal_places() {
        return decimal_places;
    }

    public void setDecimal_places(String decimal_places) {
        this.decimal_places = decimal_places;
    }
}
