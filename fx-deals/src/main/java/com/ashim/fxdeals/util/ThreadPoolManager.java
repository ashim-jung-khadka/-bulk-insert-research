package com.ashim.fxdeals.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ashimjk on 12/1/2018
 */
@Component
public class ThreadPoolManager {

	private static final ListeningExecutorService executorService =
			MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10,
					new ThreadFactoryBuilder().setNameFormat("api-pool-%d").build()));

	private ThreadPoolManager() {
	}

	public static ListeningExecutorService getExecutorService() {
		return (executorService);
	}

	@PreDestroy
	public void destroy() {
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException ie) {
			executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}