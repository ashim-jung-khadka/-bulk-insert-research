package com.ashim.batch.processing.runner.config;

import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.TransactionCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author ashimjk on 11/24/2018
 * <p>
 * Clears the complete database.
 */
@Service
public final class CleanDb {

    private final Logger log = LoggerFactory.getLogger(CleanDb.class);

    private final IDBI dbi;

    public CleanDb(IDBI dbi) {
        this.dbi = dbi;
    }

    public void cleanUp() {
        log.info("Clearing the database...");

        dbi.inTransaction((TransactionCallback<Void>) (handle, status) -> {
            handle.execute("delete from customer");
            return null;
        });
    }
}
