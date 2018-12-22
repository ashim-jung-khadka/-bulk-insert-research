package com.ashim.fxdeals.util;

import java.util.List;

/**
 * @author ashimjk on 12/22/2018
 */
public class TransactionDef implements TransactionDefMBean {

	private String transactionName;
	private List<String> transactionNames;

	@Override
	public String getTransactionName() {
		return transactionName;
	}

	@Override
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	@Override
	public List<String> findAllTransactionName() {
		return transactionNames;
	}

	public void addTransactionNames(List<String> transactionNames) {
		this.transactionNames = transactionNames;
	}
}
