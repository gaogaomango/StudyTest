package com.nangokuman.studytestproject

import com.nangokuman.studytestproject.git.GitHubRemoteDataSource
import com.nangokuman.studytestproject.git.GitHubService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object HttpUtil {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https:api.github.com")
        .client(OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val gitHubRemoteDataSource = GitHubRemoteDataSource(retrofit.create(GitHubService::class.java))
}