package com.nangokuman.studytestproject.git

import io.reactivex.Single

class GitHubRemoteDataSource(private val gitHubService: GitHubService) {
    fun listRepos(user: String): Single<List<Repo>> {
        return gitHubService.listRepos(user)
    }
}