package com.vg.model;


import java.util.Objects;

public class IBOption {

    private String symbol;
    private double strike;
    private String side;
    private String date;
    private double cost;
    private double stockPrice;

    public String getSymbol() {return symbol;}

    public void setSymbol(String symbol) {this.symbol = symbol;}

    public double getStrike() {return strike;}

    public void setStrike(double strike) {this.strike = strike;}

    public String getSide() {return side;}

    public void setSide(String side) {this.side = side;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public double getCost() {return cost;}

    public void setCost(double cost) {this.cost = cost;}

    public double getStockPrice() {return stockPrice;}

    public void setStockPrice(double stockPrice) {this.stockPrice = stockPrice;}

    public IBOption symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public IBOption strike(double strike) {
        this.strike = strike;
        return this;
    }

    public IBOption side(String side) {
        this.side = side;
        return this;
    }

    public IBOption date(String date) {
        this.date = date;
        return this;
    }

    public IBOption cost(double cost) {
        this.cost = cost;
        return this;
    }

    public IBOption stockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
        return this;
    }

    @Override
    public String toString() {
        return "IBOption{" +
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
        IBOption that = (IBOption) o;
        return strike == that.strike && Double.compare(that.cost,
                cost) == 0 && Double.compare(that.stockPrice, stockPrice) == 0 && Objects.equals(symbol,
                that.symbol) && Objects.equals(side, that.side) && Objects.equals(date, that.date);
    }

}
