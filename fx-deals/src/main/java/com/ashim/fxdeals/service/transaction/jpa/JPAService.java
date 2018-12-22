package com.ashim.fxdeals.service.transaction.jpa;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.service.transaction.TransactionService;
import com.ashim.fxdeals.util.Validator;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ashimjk on 12/1/2018
 */
@Service
@Qualifier("jpa")
public class JPAService extends TransactionService {

	@Override
	protected String getName() {
		return "jpa";
	}

	@Override
	protected void performDbTransaction(CSVReader reader, String fileName) {

		for (String[] nextLine : reader) {
			Deal deal = getDealFromReader(nextLine, fileName);
			if (Validator.isDTOValid(deal)) {
				validDealRepository.save(deal.buildValidDealObj());
			} else {
				dealRepository.save(deal);
			}
		}

		this.updateDealCount(fileName);
	}
}
