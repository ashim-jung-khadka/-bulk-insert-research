package com.ashim.fxdeals.repo.custom.entitymanager;

import com.ashim.fxdeals.bean.ValidDeal;
import com.ashim.fxdeals.repo.custom.CustomRepo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author ashimjk on 12/15/2018
 */
@Component
public class ValidEntityManagerRepo implements CustomRepo<ValidDeal> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveDeal(List<ValidDeal> deals) {
		for (ValidDeal entity : deals) {
			this.entityManager.persist(entity);
		}

		this.entityManager.flush();
		this.entityManager.clear();
	}
}
