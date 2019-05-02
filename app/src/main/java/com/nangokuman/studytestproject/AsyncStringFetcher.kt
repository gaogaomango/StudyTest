package com.nangokuman.studytestproject

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class AsyncStringFetcher(
    private val fetcher: StringFetcher,
    private val executor: ExecutorService = Executors.newCachedThreadPool()) {

    fun fetchAsync(onSuccess: (value: String) -> Unit,
                   onFailure: (error: Throwable) -> Unit): Future<*> {
        return executor.submit {
            try {
                val value = fetcher.fetch()
                onSuccess(value)
            } catch (error: Throwable) {
                onFailure(error)
            }
        }
    }
}