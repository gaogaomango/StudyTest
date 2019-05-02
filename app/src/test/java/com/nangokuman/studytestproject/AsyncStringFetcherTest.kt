package com.nangokuman.studytestproject

import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException
import java.util.concurrent.CountDownLatch

class AsyncStringFetcherTest {
    lateinit var fetcher: StringFetcher
    lateinit var asyncFetcher: AsyncStringFetcher
    lateinit var latch: CountDownLatch

    @Before
    fun setUp() {
        fetcher = spy(StringFetcher())
        asyncFetcher = AsyncStringFetcher(fetcher)
        latch = CountDownLatch(1)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun fetchAsync_expectFailButPass_01() {
        asyncFetcher.fetchAsync(
            { _ -> fail("onSuccess")},
            { _ -> fail("onFailure")}
        )
    }

    @Test
    fun fetchAsync_expectFailButPass_02_wait() {
        asyncFetcher.fetchAsync(
            { _ -> fail("onSuccess")},
            { _ -> fail("onFailure")}
        )
        Thread.sleep(200L)
    }

    @Test(expected = RuntimeException::class)
    fun fetchAsync_expectPassButFail() {
        asyncFetcher.fetchAsync(
            { _ -> fail("onSuccess")},
            { _ -> fail("onFailure")}
        )
        Thread.sleep(200L)
    }

    @Test
    fun fetchAsync_callbackedProperly() {
        asyncFetcher.fetchAsync(
            { value ->
                assertThat(value).isEqualTo("foo")
                verify(fetcher, times(1)).fetch()
                System.out.println("success")
                latch.countDown()
            },
            { _ -> fail("onFailure")}
        )
        System.out.println("fetchAsync invoked.")
        latch.await()
    }

    @Test
    fun fetchAsync_callbackedFailureProperly() {
        doThrow(RuntimeException("ERROR")).whenever(fetcher).fetch()

        asyncFetcher.fetchAsync(
            { value ->
                assertThat(value).isEqualTo("foo")
                verify(fetcher, atLeast(1)).fetch()
                System.out.println("success")
                latch.countDown()
            },
            { error ->
                assertThat(error)
                    .isInstanceOf(RuntimeException::class.java)
                    .hasMessageContaining("ERROR")
                verify(fetcher, times(1)).fetch()
                System.out.println("failure")
                latch.countDown()
            }
        )
        System.out.println("fetchAsync invoked.")
        latch.await()
    }

    @Test
    fun fetchAsync_future_OK() {
        var actualValue: String? = null
        var actualError: Throwable? = null

        asyncFetcher.fetchAsync(
            {value -> actualValue = value},
            {error -> actualError = error}
            ).get()
        verify(fetcher, times(1)).fetch()
        assertThat(actualValue).isEqualTo("foo")
        assertThat(actualError).isNull()
    }

    @Test
    fun fetchAsync_future_NG() {
        doThrow(RuntimeException("ERROR")).whenever(fetcher).fetch()
        var actualValue: String? = null
        var actualError: Throwable? = null

        asyncFetcher.fetchAsync(
            {value -> actualValue = value},
            {error -> actualError = error}
        ).get()
        verify(fetcher, times(1)).fetch()
        assertThat(actualValue).isNull()
        assertThat(actualError)
            .isExactlyInstanceOf(RuntimeException::class.java)
            .hasMessage("ERROR")
    }

}