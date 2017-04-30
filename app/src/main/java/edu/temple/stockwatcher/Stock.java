package edu.temple.stockwatcher;

import java.io.Serializable;

/**
 * Created by Frank on 4/29/2017.
 */

public class Stock implements Serializable {

    private String companyName;
    private String symbol;
    private double price;

    public Stock(String companyName, String symbol, double price) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.price = price;
    }

    public Stock(String companyName, String symbol) {
        this.companyName = companyName;
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
