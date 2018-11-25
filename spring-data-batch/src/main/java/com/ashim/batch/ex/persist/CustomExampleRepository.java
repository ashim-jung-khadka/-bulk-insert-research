package com.ashim.batch.ex.persist;

import java.util.Collection;

/**
 * @author ashimjk on 11/24/2018
 */
public interface CustomExampleRepository {

    public void bulkSave(Collection<ExampleEntity> entities);

}
