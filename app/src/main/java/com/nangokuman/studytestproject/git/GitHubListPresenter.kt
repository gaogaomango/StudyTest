package com.nangokuman.studytestproject.git

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class GitHubListPresenter(private val view: GitHubView, private val repository: GitHubRemoteDataSource): GitHubPresenter {

    override fun getRepositoryList(name: String) {
        repository.listRepos(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { view.showRepositoryList(it) }

    }

}