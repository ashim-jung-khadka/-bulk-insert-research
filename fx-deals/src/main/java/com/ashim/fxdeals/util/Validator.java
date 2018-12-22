package com.ashim.fxdeals.util;

import com.ashim.fxdeals.bean.Deal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;

/**
 * @author ashimjk on 12/1/2018
 */
public class Validator {

	private static final Logger logger = LoggerFactory.getLogger("INVALID_DEAL");
	public static final String DEFAULT_CURRENCY_CODE = "Not Supplied";

	private Validator() {
	}

	public static boolean isDTOValid(Deal deal) {

		return !(isDtoNullOrEmpty(deal)
				|| isCurrencyInvalid(deal.getDealId(), deal.getFromCurrency())
				|| isCurrencyInvalid(deal.getDealId(), deal.getToCurrency())
				|| isDateInvalid(deal.getDealId(), deal.getDealTime())
				|| isAmountInvalid(deal.getDealId(), deal.getAmount())
		);
	}

	private static boolean isAmountInvalid(String id, String amount) {
		try {
			Double.parseDouble(amount);
			return false;
		} catch (NumberFormatException ex) {
			logger.warn("{} - Illegal amount : {}", id, amount);
		}
		return true;
	}

	private static boolean isDateInvalid(String id, String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(date);
			return false;
		} catch (ParseException ex) {
			logger.warn("{} - Illegal date format : {}", id, date);
		}
		return true;
	}

	private static boolean isCurrencyInvalid(String dealId, String currencyCode) {
		try {
			Currency.getInstance(currencyCode);
			return false;
		} catch (IllegalArgumentException ex) {
			logger.warn("{} - Illegal currency code : {}", dealId, currencyCode);
		}

		return true;
	}

	private static boolean isDtoNullOrEmpty(Deal deal) {
		return StringUtils.isEmpty(deal.getDealId())
				|| StringUtils.isEmpty(deal.getFromCurrency())
				|| StringUtils.isEmpty(deal.getToCurrency())
				|| StringUtils.isEmpty(deal.getDealTime())
				|| StringUtils.isEmpty(deal.getAmount());
	}
}
