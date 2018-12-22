package com.ashim.fxdeals.repo.custom.stateless;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.repo.custom.CustomRepo;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
@Component
public class StatelessSessionInvalidRepo implements CustomRepo<Deal> {

	@Autowired private StatelessSession statelessSession;

	@Override
	@Transactional
	public void saveDeal(List<Deal> deals) {
		statelessSession.setJdbcBatchSize(deals.size());

		for (Deal entity : deals) {
			this.statelessSession.insert(entity);
		}
	}
}
