package com.nangokuman.studytestproject

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
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
}