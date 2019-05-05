package com.nangokuman.studytestproject.git

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nangokuman.studytestproject.HttpUtil.gitHubRemoteDataSource
import com.nangokuman.studytestproject.R

class GitHubRepositoryActivity : AppCompatActivity(), GitHubView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_hub_repository)

        val presenter = GitHubListPresenter(this, gitHubRemoteDataSource)
        presenter.getRepositoryList("shiroyama")
    }

    override fun showRepositoryList(List: List<Repo>) {
        /* RecycleViewなどに結果を描画 */
    }

}
