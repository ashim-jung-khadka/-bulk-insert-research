package com.ashim.fxdeals.service.transaction.custom;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.bean.ValidDeal;
import com.ashim.fxdeals.repo.custom.CustomRepo;
import com.ashim.fxdeals.repo.custom.entitymanager.InvalidEntityManagerRepo;
import com.ashim.fxdeals.repo.custom.entitymanager.ValidEntityManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author ashimjk on 12/15/2018
 */
@Service
@Qualifier("entity")
public class EntityManagerService extends CustomService {

	private final ValidEntityManagerRepo validRepo;
	private final InvalidEntityManagerRepo invalidRepo;

	@Override
	protected String getName() {
		return "entity";
	}

	@Autowired
	public EntityManagerService(ValidEntityManagerRepo validRepo, InvalidEntityManagerRepo invalidRepo) {
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