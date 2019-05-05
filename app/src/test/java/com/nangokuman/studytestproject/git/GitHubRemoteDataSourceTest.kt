package com.nangokuman.studytestproject.git

import com.nangokuman.studytestproject.setBodyFromFileName
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.mockito.stubbing.Answer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

@RunWith(Enclosed::class)
class GitHubRemoteDataSourceTest {

    class GitHubRemoteDataSourceMockWebServerTest {
        val mockWebServer = MockWebServer()
        lateinit var gitHubRemoteDataSource: GitHubRemoteDataSource

        @Before
        fun setUp() {
            val dispatcher: Dispatcher = object: Dispatcher() {
                override fun dispatch(request: RecordedRequest?): MockResponse {
                    return when {
                        request == null -> MockResponse().setResponseCode(400)
                        request.path == null -> MockResponse().setResponseCode(400)
                        request.path.matches(Regex("/users/[a-zA-Z0-9]+/repos")) -> {
                            MockResponse().setResponseCode(200).setBodyFromFileName("users_repos.json")
                        }
                        else -> MockResponse().setResponseCode(400)
                    }
                }
            }
            mockWebServer.setDispatcher(dispatcher)
            mockWebServer.start()

            val retrofit = Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .client(OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val gitHubService = retrofit.create(GitHubService::class.java)
            gitHubRemoteDataSource = GitHubRemoteDataSource(gitHubService)
        }

        @After
        fun tearDown() {
            mockWebServer.shutdown()
        }

        @Test
        fun listRepos_OK() {
            gitHubRemoteDataSource.listRepos("").test().await().assertNotComplete()

            val list = gitHubRemoteDataSource.listRepos("shiroyama")
                .test()
                .await()
                .assertNoErrors()
                .assertComplete()
                .values()[0]
            assertThat(list).isNotEmpty
        }
    }

    class GitHubRemoteDataSourceModelTest {
        private val gitHubService = mock<GitHubService> (
            defaultAnswer = Answer { throw RuntimeException() }
        )
        lateinit var gitHubRemoteDataSource: GitHubRemoteDataSource

        @Before
        fun setUp() {
            doReturn(Single.just(listOf(Repo(1, "repo1"), Repo(2, "repo2"), Repo(3, "repo3")))).whenever(gitHubService).listRepos(any())
            gitHubRemoteDataSource = GitHubRemoteDataSource(gitHubService)
        }

        @Test
        fun listRepos_model_test() {
            gitHubRemoteDataSource.listRepos("shiroyama")
                .test()
                .assertComplete()
                .assertNoErrors()
            verify(gitHubService, times(1)).listRepos(eq("shiroyama"))
            verifyNoMoreInteractions(gitHubService)
        }

    }

}