package com.nangokuman.studytestproject

import okhttp3.mockwebserver.MockResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

fun MockResponse.setBodyFromFileName(name: String): MockResponse {
    val inputStream = javaClass.classLoader.getResourceAsStream(name)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val stringBuilder = StringBuilder()
    bufferedReader.forEachLine { buffer -> stringBuilder.append(buffer) }

    val body = stringBuilder.toString()
    this.setBody(body)
    return this
}