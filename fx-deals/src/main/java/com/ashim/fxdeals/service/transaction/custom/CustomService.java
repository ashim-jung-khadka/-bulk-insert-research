package com.ashim.fxdeals.service.transaction.custom;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.bean.ValidDeal;
import com.ashim.fxdeals.repo.custom.CustomRepo;
import com.ashim.fxdeals.service.transaction.TransactionService;
import com.ashim.fxdeals.util.Validator;
import com.opencsv.CSVReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ashimjk on 12/17/2018
 */
public abstract class CustomService extends TransactionService {

	abstract CustomRepo<ValidDeal> getValidRepo();

	abstract CustomRepo<Deal> getInvalidRepo();

	@Override
	protected void performDbTransaction(CSVReader reader, String fileName) {

		List<ValidDeal> validDeals = new ArrayList<>(fxDealConfig.getBatchSize());
		List<Deal> invalidDeals = new ArrayList<>(fxDealConfig.getBatchSize());

		for (String[] nextLine : reader) {
			Deal deal = getDealFromReader(nextLine, fileName);
			this.persistDataUsingCustomRepo(deal, validDeals, invalidDeals);
		}

		this.persistRemainingDataUsingCustomRepo(validDeals, invalidDeals);
		this.updateDealCount(fileName);
	}

	private void persistRemainingDataUsingCustomRepo(List<ValidDeal> validDeals, List<Deal> invalidDeals) {
		if (validDeals.size() > 0) {
			this.getValidRepo().saveDeal(validDeals);
		}

		if (invalidDeals.size() > 0) {
			this.getInvalidRepo().saveDeal(invalidDeals);
		}
	}

	private void persistDataUsingCustomRepo(Deal deal, List<ValidDeal> validDeals, List<Deal> invalidDeals) {
		if (Validator.isDTOValid(deal)) {
			validDeals.add(deal.buildValidDealObj());
			if (fxDealConfig.getBatchSize() == validDeals.size()) {
				this.getValidRepo().saveDeal(validDeals);
				validDeals.clear();
			}

		} else {
			invalidDeals.add(deal);
			if (fxDealConfig.getBatchSize() == invalidDeals.size()) {
				this.getInvalidRepo().saveDeal(invalidDeals);
				invalidDeals.clear();
			}
		}
	}

}
