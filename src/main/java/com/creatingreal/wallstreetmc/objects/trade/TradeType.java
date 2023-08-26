package com.creatingreal.wallstreetmc.objects.trade;

public enum TradeType {

    BUY("Buy"),
    SELL("Sell");

    String name;

    TradeType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
