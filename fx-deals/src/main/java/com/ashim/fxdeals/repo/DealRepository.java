package com.ashim.fxdeals.repo;

import com.ashim.fxdeals.bean.Deal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
@Repository
public interface DealRepository extends CrudRepository<Deal, Long> {

    Long countByFileName(String fileName);

    @Query(value = "SELECT from_currency as fromCurrency, to_currency as toCurrency, count(from_currency) as count "
            + "FROM invalid_deal group by from_currency, to_currency", nativeQuery = true)
    List<Object[]> getTotalCountOfInvalidDeal();

}
