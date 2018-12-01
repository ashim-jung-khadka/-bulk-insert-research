package com.ashim.fxdeals.bean;

import com.ashim.fxdeals.util.Validator;
import com.opencsv.bean.CsvBindByName;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author ashimjk on 12/1/2018
 */
@Entity
@Table(name = "invalid_deal")
public class Deal {

    private static final Logger logger = LoggerFactory.getLogger(Deal.class);

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Long id;

    @CsvBindByName private String dealId;
    @CsvBindByName private String fromCurrency;
    @CsvBindByName private String toCurrency;
    @CsvBindByName private String dealTime;
    @CsvBindByName private String amount;
    @CsvBindByName private String fileName;

    public Deal() {
    }

    public Deal(String dealId, String fromCurrency, String toCurrency, String dealTime, String amount,
            String fileName) {
        this.dealId = dealId;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.dealTime = dealTime;
        this.amount = amount;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getFromCurrency() {
        if (StringUtils.isEmpty(fromCurrency)) {
            fromCurrency = Validator.DEFAULT_CURRENCY_CODE;
        }
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        if (StringUtils.isEmpty(toCurrency)) {
            toCurrency = Validator.DEFAULT_CURRENCY_CODE;
        }
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ValidDeal buildValidDealObj() {
        ValidDeal validDeal = new ValidDeal();
        validDeal.setId(null);
        validDeal.setDealId(getDealId());
        validDeal.setFromCurrency(getFromCurrency());
        validDeal.setToCurrency(getToCurrency());

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            validDeal.setDealTime(sdf.parse(getDealTime()));
        } catch (ParseException ex) {
            logger.warn("Illegal date format : {} -- supported format : (yyyy-MM-dd)", getDealTime());
        }

        validDeal.setAmount(Double.parseDouble(getAmount()));
        validDeal.setFileName(getFileName());

        return validDeal;
    }

    @Override public String toString() {
        return "Deal{" +
                "id=" + id +
                ", dealId='" + dealId + '\'' +
                ", fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", dealTime='" + dealTime + '\'' +
                ", amount='" + amount + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
