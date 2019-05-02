package com.nangokuman.studytestproject.tweet

import io.reactivex.Single
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class TweetRepository {
    val executor: Executor = Executors.newSingleThreadExecutor()

    fun recents(): Single<List<Tweet>> {
        return Single.create {emitter ->
            executor.execute{
                try {
                    val tweets = listOf(
                        Tweet("hello", 1),
                        Tweet("from", 2),
                        Tweet("world", 3)
                    )
                    emitter.onSuccess(tweets)
                } catch (e: Throwable) {
                    emitter.onError(e)
                }
            }
        }
    }
}

data class Tweet(val tweet: String, val id: Long)