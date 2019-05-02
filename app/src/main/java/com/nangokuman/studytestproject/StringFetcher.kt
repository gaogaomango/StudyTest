package com.nangokuman.studytestproject

import java.lang.RuntimeException

open class StringFetcher {
    @Throws(RuntimeException::class)
    open fun fetch(): String {
        Thread.sleep(1000L)
        return "foo"
    }
}