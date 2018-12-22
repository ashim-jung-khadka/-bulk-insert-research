package com.ashim.fxdeals.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ashimjk on 12/1/2018
 */
@ConfigurationProperties("fxdeal.config")
public class FxDealConfig {

	private String storageLocation;
	private int batchSize;
	private String transactionName;

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
}
