package com.ashim.fxdeals.service.transaction;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.bean.DealCount;
import com.ashim.fxdeals.repo.jpa.DealRepository;
import com.ashim.fxdeals.repo.jpa.ValidDealRepository;
import com.ashim.fxdeals.service.DealCountService;
import com.ashim.fxdeals.service.StorageService;
import com.ashim.fxdeals.util.FxDealConfig;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author ashimjk on 12/15/2018
 */
public abstract class TransactionService {

	protected static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	protected ValidDealRepository validDealRepository;
	@Autowired
	protected DealRepository dealRepository;
	@Autowired
	private StorageService storageService;
	@Autowired
	private DealCountService dealCountService;
	@Autowired
	protected FxDealConfig fxDealConfig;

	protected abstract String getName();

	protected abstract void performDbTransaction(CSVReader reader, String fileName);

	public boolean saveDeal(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();

		// check if file already uploaded
		Long count = this.validDealRepository.countByFileName(fileName);
		if (count != null && count > 0) {
			return false;
		}

		count = this.dealRepository.countByFileName(fileName);
		if (count != null && count > 0) {
			return false;
		}

		this.storageService.storeFile(file);
		File uploadedFile = this.storageService.loadAsResource(fileName).getFile();

		logger.info("persisting in database");
		CSVReader reader = new CSVReader(new FileReader(uploadedFile));
		reader.skip(1);

		this.performDbTransaction(reader, fileName);
		reader.close();

		return true;
	}

	protected Deal getDealFromReader(String[] nextLine, String fileName) {
		return new Deal(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], fileName);
	}

	protected void updateDealCount(String fileName) {
		logger.info("updating deal count");

		List<Object[]> results = validDealRepository.getAggCountByFileName(fileName);
		for (Object[] result : results) {

			String fromCurrencyCode = (String) result[0];
			String toCurrencyCode = (String) result[1];
			int count = ((Number) result[2]).intValue();

			DealCount dealCount = dealCountService.getDealCount(fromCurrencyCode, toCurrencyCode);
			if (dealCount == null) {
				dealCount = new DealCount(fromCurrencyCode, toCurrencyCode, count);
			} else {
				dealCount.incrementCount(count);
			}
			dealCountService.save(dealCount);
		}
	}

}
