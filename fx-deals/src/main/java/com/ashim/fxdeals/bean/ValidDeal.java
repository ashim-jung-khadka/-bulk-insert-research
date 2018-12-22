package com.ashim.fxdeals.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ashimjk on 12/1/2018
 */
@Entity
@Table(name = "valid_deal")
public class ValidDeal implements Serializable {

	private static final long serialVersionUID = 6850817883135808158L;

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "increment")
	private Long id;

	private String dealId;
	private String fromCurrency;
	private String toCurrency;
	private Date dealTime;
	private Double amount;
	private String fileName;

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

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "ValidDeal{" +
				"id=" + id +
				", dealId='" + dealId + '\'' +
				", fromCurrency='" + fromCurrency + '\'' +
				", toCurrency='" + toCurrency + '\'' +
				", dealTime=" + dealTime +
				", amount=" + amount +
				", fileName='" + fileName + '\'' +
				'}';
	}
}
