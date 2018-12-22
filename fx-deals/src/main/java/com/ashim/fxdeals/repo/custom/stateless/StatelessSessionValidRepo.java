package com.ashim.fxdeals.repo.custom.stateless;

import com.ashim.fxdeals.bean.ValidDeal;
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
public class StatelessSessionValidRepo implements CustomRepo<ValidDeal> {

	@Autowired private StatelessSession statelessSession;

	@Override
	@Transactional
	public void saveDeal(List<ValidDeal> deals) {
		statelessSession.setJdbcBatchSize(deals.size());

		for (ValidDeal entity : deals) {
			this.statelessSession.insert(entity);
		}
	}
}
