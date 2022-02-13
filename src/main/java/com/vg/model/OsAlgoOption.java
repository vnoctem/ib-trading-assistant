package com.vg.model;


import java.util.Objects;

public class OsAlgoOption {

    private String symbol;
    private int strike;
    private String side;
    private String date;
    private double cost;
    private double stockPrice;

    public String getSymbol() {return symbol;}

    public void setSymbol(String symbol) {this.symbol = symbol;}

    public int getStrike() {return strike;}

    public void setStrike(int strike) {this.strike = strike;}

    public String getSide() {return side;}

    public void setSide(String side) {this.side = side;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public double getCost() {return cost;}

    public void setCost(double cost) {this.cost = cost;}

    public double getStockPrice() {return stockPrice;}

    public void setStockPrice(double stockPrice) {this.stockPrice = stockPrice;}

    public OsAlgoOption symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public OsAlgoOption strike(int strike) {
        this.strike = strike;
        return this;
    }

    public OsAlgoOption side(String side) {
        this.side = side;
        return this;
    }

    public OsAlgoOption date(String date) {
        this.date = date;
        return this;
    }

    public OsAlgoOption cost(double cost) {
        this.cost = cost;
        return this;
    }

    public OsAlgoOption stockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
        return this;
    }

    @Override
    public String toString() {
        return "OsAlgoOption{" +
                "symbol='" + symbol + '\'' +
                ", strike=" + strike +
                ", side='" + side + '\'' +
                ", date='" + date + '\'' +
                ", cost=" + cost +
                ", stockPrice=" + stockPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OsAlgoOption that = (OsAlgoOption) o;
        return strike == that.strike && Double.compare(that.cost,
                cost) == 0 && Double.compare(that.stockPrice, stockPrice) == 0 && Objects.equals(symbol,
                that.symbol) && Objects.equals(side, that.side) && Objects.equals(date, that.date);
    }

}
