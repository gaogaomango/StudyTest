package com.nangokuman.studytestproject

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class RxJavaTest {
    val observable = Observable.just("Rx").delay(1, TimeUnit.SECONDS)

    @Test
    fun observableTestNoGood() {
        val subscriber = observable.test()
        subscriber.await()
            .assertComplete()
            .assertValue("Rx")
    }

    @Test
    fun observable_values_test() {
        val listObservable: Observable<String> = listOf("Giants", "Dodgers", "Athletics")
            .toObservable()
            .delay(1, TimeUnit.SECONDS)

        val teams: List<String> = listObservable.test()
            .await()
            .assertComplete()
            .values()

        assertThat(teams).containsExactly("Giants", "Dodgers", "Athletics")
    }

    @Test
    fun observable_error() {
        val errorObservable: Observable<RuntimeException>
                = Observable.error(RuntimeException("Oops!"))

        errorObservable.test()
            .await()
            .assertNotComplete()
            .assertError(RuntimeException::class.java)
            .assertErrorMessage("Oops!")
    }
}