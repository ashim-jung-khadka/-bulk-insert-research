package com.ashim.batch.ex.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author ashimjk on 11/24/2018
 */
@Component
public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> {

}
