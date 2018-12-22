package com.ashim.fxdeals.service.transaction.custom;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.bean.ValidDeal;
import com.ashim.fxdeals.repo.custom.CustomRepo;
import com.ashim.fxdeals.repo.custom.stateless.StatelessSessionInvalidRepo;
import com.ashim.fxdeals.repo.custom.stateless.StatelessSessionValidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ashimjk on 12/15/2018
 */
@Service
@Qualifier("stateless")
public class StatelessSessionService extends CustomService {

	private final StatelessSessionValidRepo validRepo;
	private final StatelessSessionInvalidRepo invalidRepo;

	@Override
	protected String getName() {
		return "stateless";
	}

	@Autowired
	public StatelessSessionService(StatelessSessionValidRepo validRepo,
	                               StatelessSessionInvalidRepo invalidRepo) {
		this.validRepo = validRepo;
		this.invalidRepo = invalidRepo;
	}

	@Override
	CustomRepo<ValidDeal> getValidRepo() {
		return validRepo;
	}

	@Override
	CustomRepo<Deal> getInvalidRepo() {
		return invalidRepo;
	}
}