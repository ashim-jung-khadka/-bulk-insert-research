package com.ashim.fxdeals.repo;

import com.ashim.fxdeals.bean.ValidDeal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
@Repository
public interface ValidDealRepository extends CrudRepository<ValidDeal, Long> {

    Long countByFileName(String fileName);

    @Query(value = "SELECT from_currency as fromCurrency, to_currency as toCurrency, count(from_currency) as count "
            + "FROM valid_deal where file_name = :fileName group by from_currency, to_currency", nativeQuery = true)
    List<Object[]> getAggCountByFileName(@Param("fileName") String fileName);

}
