package com.nangokuman.studytestproject.tweet

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class TweetFetcherTest {

    lateinit var repository: TweetRepository
    lateinit var tweetFetcher: TweetFetcher

    @Before
    fun setUp() {
        repository = mock(name = "StubTweetRepository")
        val tweets = listOf(Tweet("hello", 1), Tweet("from", 2), Tweet("world", 3))
        doReturn(Single.just(tweets)).whenever(repository).recents()

        tweetFetcher = TweetFetcher(Schedulers.trampoline(),
            Schedulers.trampoline(),
            repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun tweetFetcherTest() {
        tweetFetcher.recents(
            onSuccess = {
                assertThat(it)
                    .extracting("tweet", String::class.java)
                    .containsExactly("hello", "from", "world")
            },
            onError = {}
        )
    }
}