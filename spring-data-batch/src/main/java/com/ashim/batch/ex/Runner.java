package com.ashim.batch.ex;

import com.ashim.batch.ex.persist.CustomExampleRepository;
import com.ashim.batch.ex.persist.ExampleEntity;
import com.ashim.batch.ex.persist.ExampleRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author ashimjk on 11/24/2018
 */
@Component(value = "runner")
public class Runner {

    @Inject
    private ExampleRepository exampleRepository;
    @Inject CustomExampleRepository customExampleRepository;

    public void run() {

        useJPARepo();
        //        useCustomRepo();
    }

    private void useJPARepo() {
        List<ExampleEntity> exampleEntities = new ArrayList<>();
        int batchSize = 50;
        int dataSize = 100000;

        long time = System.currentTimeMillis();

        for (int i = 0; i < dataSize; i++) {
            ExampleEntity entity1 = new ExampleEntity(UUID.randomUUID().toString());
            exampleEntities.add(entity1);

            if (i % batchSize == 0) {
                exampleRepository.save(exampleEntities);
                exampleRepository.flush();
                exampleEntities.clear();
            }
        }

        exampleRepository.save(exampleEntities);
        System.out.println("Execution Time = " + (System.currentTimeMillis() - time));
    }

    private void useCustomRepo() {
        List<ExampleEntity> exampleEntities = new ArrayList<>();
        int dataSize = 100000;
        long time = System.currentTimeMillis();

        for (int i = 0; i < dataSize; i++) {
            ExampleEntity entity1 = new ExampleEntity(UUID.randomUUID().toString());
            exampleEntities.add(entity1);
        }

        customExampleRepository.bulkSave(exampleEntities);
        System.out.println("Execution Time = " + (System.currentTimeMillis() - time));
    }

}
