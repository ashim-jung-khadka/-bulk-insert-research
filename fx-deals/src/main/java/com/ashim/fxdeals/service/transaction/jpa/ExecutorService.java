package com.ashim.fxdeals.service.transaction.jpa;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.bean.ValidDeal;
import com.ashim.fxdeals.service.transaction.TransactionService;
import com.ashim.fxdeals.util.ThreadPoolManager;
import com.ashim.fxdeals.util.Validator;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
@Service
@Qualifier("executor")
public class ExecutorService extends TransactionService {

	private List<ListenableFuture<String>> futures = new ArrayList<>();

	@Override
	protected String getName() {
		return "executor";
	}

	@Override
	protected void performDbTransaction(CSVReader reader, String fileName) {

		logger.info("uploading csv to table");
		ListeningExecutorService service = ThreadPoolManager.getExecutorService();
		List<ValidDeal> validDeals = new ArrayList<>(fxDealConfig.getBatchSize());
		List<Deal> invalidDeals = new ArrayList<>(fxDealConfig.getBatchSize());

		for (String[] nextLine : reader) {
			Deal deal = getDealFromReader(nextLine, fileName);
			ListenableFuture<String> future = service
					.submit(() -> this.persistData(deal, validDeals, invalidDeals));
			futures.add(future);
		}

		this.updateDealCount(fileName, validDeals, invalidDeals);
	}

	private String persistData(Deal deal, List<ValidDeal> validDeals, List<Deal> invalidDeals) {
		if (Validator.isDTOValid(deal)) {
			validDeals.add(deal.buildValidDealObj());
			if (fxDealConfig.getBatchSize() == validDeals.size()) {
				this.validDealRepository.saveAll(validDeals);
				validDeals.clear();
			}

		} else {
			deal.setId(null);
			invalidDeals.add(deal);
			if (fxDealConfig.getBatchSize() == invalidDeals.size()) {
				this.dealRepository.saveAll(invalidDeals);
				invalidDeals.clear();
			}
		}

		return null;
	}

	private void updateDealCount(String fileName, List<ValidDeal> validDeals, List<Deal> invalidDeals) {
		ListenableFuture<List<String>> lf = Futures.successfulAsList(futures);
		Futures.addCallback(lf, new FutureCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> rs) {
				if (validDeals.size() > 0) {
					validDealRepository.saveAll(validDeals);
					validDeals.clear();
				}

				if (invalidDeals.size() > 0) {
					dealRepository.saveAll(invalidDeals);
					invalidDeals.clear();
				}

				updateDealCount(fileName);
			}

			@Override
			public void onFailure(Throwable throwable) {
				// No implementation required
			}
		});
	}
}
