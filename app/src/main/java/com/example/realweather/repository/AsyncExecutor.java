package com.example.realweather.repository;

import android.os.Handler;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

public interface AsyncExecutor {

	Handler HANDLER = new Handler();
	Void NOTHING = null;

	default void executeAsync(Collection<Callable<Void>> callables, ExecutorService executor) {
		try {
			executor.invokeAll(callables);
		} catch (InterruptedException exception) {
			Timber.e(exception);
			Thread.currentThread().interrupt();
		}
	}

	default void executeHandlerPost(Runnable runnable) {
		HANDLER.post(runnable);
	}

	default void executeSingleThreadAsync(Collection<Callable<Void>> callables) {
		executeAsync(callables, Executors.newSingleThreadExecutor());
	}

	default void executeThreadPoolAsync(Collection<Callable<Void>> callables) {
		executeAsync(callables, Executors.newFixedThreadPool(5));
	}

}
