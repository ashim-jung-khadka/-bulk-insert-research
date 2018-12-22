package com.ashim.fxdeals.service;

import com.ashim.fxdeals.bean.DealCount;
import com.ashim.fxdeals.repo.jpa.DealRepository;
import com.ashim.fxdeals.service.transaction.TransactionServiceHandler;
import com.ashim.fxdeals.util.FxDealConfig;
import com.ashim.fxdeals.util.TransactionDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ashimjk on 12/1/2018
 */
@Service
public class FxDealService {

	private static final Logger logger = LoggerFactory.getLogger(FxDealService.class.getName());

	private final DealRepository dealRepository;
	private final StorageService storageService;
	private final TransactionServiceHandler serviceHandler;
	private final TransactionDef transactionDef;

	@Autowired
	public FxDealService(DealRepository dealRepository, StorageService storageService, TransactionServiceHandler serviceHandler, FxDealConfig fxDealConfig) {
		this.dealRepository = dealRepository;
		this.storageService = storageService;
		this.serviceHandler = serviceHandler;
		this.transactionDef = new TransactionDef();
		this.transactionDef.setTransactionName(fxDealConfig.getTransactionName());
		this.transactionDef.addTransactionNames(serviceHandler.getTransactionNames());

		this.registerTransactionNameBean();
	}

	public Stream<Path> loadAll() {
		return this.storageService.loadAll();
	}

	public boolean saveDeal(MultipartFile file) throws IOException {
		logger.info("transaction name : {}", transactionDef.getTransactionName());
		return serviceHandler.getTransactionService(transactionDef.getTransactionName()).saveDeal(file);
	}

	public Resource loadAsResource(String filename) {
		return this.storageService.loadAsResource(filename);
	}

	public List<DealCount> getTotalInvalidDealCount() {
		List<Object[]> results = dealRepository.getTotalCountOfInvalidDeal();
		List<DealCount> dealCounts = new ArrayList<>();
		for (Object[] result : results) {

			String fromCurrencyCode = (String) result[0];
			String toCurrencyCode = (String) result[1];
			int count = ((Number) result[2]).intValue();

			DealCount dealCount = new DealCount(fromCurrencyCode, toCurrencyCode, count);
			dealCounts.add(dealCount);
		}

		return dealCounts;
	}

	private void registerTransactionNameBean() {
		try {
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			ObjectName objectName = new ObjectName("com.ashim.fxdeals.util:type=TransactionDef");

			server.registerMBean(transactionDef, objectName);
		} catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
			logger.error("Error while registering transaction bean:", e);
		}
	}
}
