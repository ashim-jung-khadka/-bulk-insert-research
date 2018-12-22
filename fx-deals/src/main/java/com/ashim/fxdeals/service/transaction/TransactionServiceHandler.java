package com.ashim.fxdeals.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ashimjk on 12/17/2018
 */
@Service
public class TransactionServiceHandler {

	private final Map<String, TransactionService> servicesByName = new HashMap<>();

	@Autowired
	public TransactionServiceHandler(List<TransactionService> services) {
		for (TransactionService service : services) {
			register(service.getName(), service);
		}
	}

	private void register(String name, TransactionService service) {
		this.servicesByName.put(name, service);
	}

	public TransactionService getTransactionService(String name) {
		return this.servicesByName.get(name);
	}

	public List<String> getTransactionNames() {
		return new ArrayList<>(servicesByName.keySet());
	}
}
