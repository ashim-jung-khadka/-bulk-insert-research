package com.ashim.file.reader.csv;

import com.opencsv.bean.CsvBindByName;

/**
 * @author ashimjk on 11/28/2018
 */
public class FxDeal {

    @CsvBindByName private String dealId;
    @CsvBindByName private String fromCurrency;
    @CsvBindByName private String toCurrency;
    @CsvBindByName private String dealTime;
    @CsvBindByName private String amount;

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
