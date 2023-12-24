package com.creatingreal.wallstreetmc.objects.stock;

import java.util.List;

public class Stock {

    private String name;
    private List<String> description;
    private String ticker;
    private double price;
    private double marketCap;
    private double volatility;
    private int volume;
    private double highOfTheDay;
    private double lowOfTheDay;
    private double openPrice;
    private double closePrice;
    private double previousClose;
    private double allTimeHigh;
    private double allTimeLow;
    private int allTimeVolume;
    private boolean halted;
    private String haltReason;
    private int haltDuration;
    private String configIdentifier;

    public Stock(String name, List<String> description, String ticker, double price, double marketCap, double volatility, int volume, double highOfTheDay, double lowOfTheDay, double openPrice, double closePrice, double previousClose, double allTimeHigh, double allTimeLow, int allTimeVolume, boolean halted, String haltReason, int haltDuration, String configIdentifier) {
        this.name = name;
        this.description = description;
        this.ticker = ticker;
        this.price = price;
        this.marketCap = marketCap;
        this.volatility = volatility;
        this.volume = volume;
        this.highOfTheDay = highOfTheDay;
        this.lowOfTheDay = lowOfTheDay;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.previousClose = previousClose;
        this.allTimeHigh = allTimeHigh;
        this.allTimeLow = allTimeLow;
        this.allTimeVolume = allTimeVolume;
        this.halted = halted;
        this.haltReason = haltReason;
        this.haltDuration = haltDuration;
        this.configIdentifier = configIdentifier;
    }

    public String getConfigIdentifier() {
        return configIdentifier;
    }

    public void setConfigIdentifier(String configIdentifier) {
        this.configIdentifier = configIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;

        if(price > this.highOfTheDay){
            this.highOfTheDay = price;
        }

        if(price < this.lowOfTheDay){
            this.lowOfTheDay = price;
        }

        if(price > this.allTimeHigh){
            this.allTimeHigh = price;
        }

        if(price < this.allTimeLow){
            this.allTimeLow = price;
        }
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;

        if(volume > this.allTimeVolume){
            this.allTimeVolume = volume;
        }
    }

    public double getHighOfTheDay() {
        return highOfTheDay;
    }

    public void setHighOfTheDay(double highOfTheDay) {
        this.highOfTheDay = highOfTheDay;
    }

    public double getLowOfTheDay() {
        return lowOfTheDay;
    }

    public void setLowOfTheDay(double lowOfTheDay) {
        this.lowOfTheDay = lowOfTheDay;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public double getAllTimeHigh() {
        return allTimeHigh;
    }

    public void setAllTimeHigh(double allTimeHigh) {
        this.allTimeHigh = allTimeHigh;
    }

    public double getAllTimeLow() {
        return allTimeLow;
    }

    public void setAllTimeLow(double allTimeLow) {
        this.allTimeLow = allTimeLow;
    }

    public int getAllTimeVolume() {
        return allTimeVolume;
    }

    public void setAllTimeVolume(int allTimeVolume) {
        this.allTimeVolume = allTimeVolume;
    }

    public boolean isHalted() {
        return halted;
    }

    public void setHalted(boolean halted) {
        this.halted = halted;
    }

    public String getHaltReason() {
        return haltReason;
    }

    public void setHaltReason(String haltReason) {
        this.haltReason = haltReason;
    }

    public int getHaltDuration() {
        return haltDuration;
    }

    public void setHaltDuration(int haltDuration) {
        this.haltDuration = haltDuration;
    }




}
