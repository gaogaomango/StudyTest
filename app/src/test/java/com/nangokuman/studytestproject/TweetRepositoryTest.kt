package com.nangokuman.studytestproject

import com.nangokuman.studytestproject.tweet.TweetRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class TweetRepositoryTest {


    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun recents_contains_test_OK() {
        val repository = TweetRepository()
        val list = repository.recents()
            .test()
            .await()
            .values()[0]

        assertThat(list).extracting("tweet", String::class.java)
            .containsExactly("hello", "from", "world")
    }
}