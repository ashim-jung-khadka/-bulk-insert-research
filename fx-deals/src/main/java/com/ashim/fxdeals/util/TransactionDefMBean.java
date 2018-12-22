package com.ashim.fxdeals.util;

import java.util.List;

/**
 * @author ashimjk on 12/22/2018
 */
public interface TransactionDefMBean {

	String getTransactionName();

	void setTransactionName(String transactionName);

	List<String> findAllTransactionName();
}
