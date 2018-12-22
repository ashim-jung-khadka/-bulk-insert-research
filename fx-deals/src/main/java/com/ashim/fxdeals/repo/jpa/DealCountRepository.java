package com.ashim.fxdeals.repo.jpa;

import com.ashim.fxdeals.bean.DealCount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ashimjk on 12/1/2018
 */
@Repository
public interface DealCountRepository extends CrudRepository<DealCount, String> {

	DealCount findByFromCurrencyCodeAndToCurrencyCode(String fromCurrencyCode, String toCurrencyCode);

}
