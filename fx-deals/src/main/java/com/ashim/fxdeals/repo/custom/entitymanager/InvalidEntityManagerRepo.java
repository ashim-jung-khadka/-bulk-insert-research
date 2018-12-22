package com.ashim.fxdeals.repo.custom.entitymanager;

import com.ashim.fxdeals.bean.Deal;
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
public class InvalidEntityManagerRepo implements CustomRepo<Deal> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveDeal(List<Deal> deals) {
		for (Deal entity : deals) {
			this.entityManager.persist(entity);
		}

		this.entityManager.flush();
		this.entityManager.clear();
	}
}
