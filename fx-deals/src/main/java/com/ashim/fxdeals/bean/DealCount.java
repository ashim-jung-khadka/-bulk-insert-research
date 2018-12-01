package com.ashim.fxdeals.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ashimjk on 12/1/2018
 */
@Entity
@Table(name = "deal_count")
public class DealCount implements Serializable {

    private static final long serialVersionUID = -1124719551435543217L;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Long id;
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private int countOfDeals;

    public DealCount() {
    }

    public DealCount(String fromCurrencyCode, String toCurrencyCode, int countOfDeals) {
        this.fromCurrencyCode = fromCurrencyCode;
        this.toCurrencyCode = toCurrencyCode;
        this.countOfDeals = countOfDeals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public int getCountOfDeals() {
        return countOfDeals;
    }

    public void setCountOfDeals(int countOfDeals) {
        this.countOfDeals = countOfDeals;
    }

    public void incrementCount(int count) {
        setCountOfDeals(countOfDeals + count);
    }

    @Override public String toString() {
        return "DealCount{" +
                "id=" + id +
                ", fromCurrencyCode='" + fromCurrencyCode + '\'' +
                ", toCurrencyCode='" + toCurrencyCode + '\'' +
                ", countOfDeals=" + countOfDeals +
                '}';
    }
}
