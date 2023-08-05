package com.dicoding.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.githubuser.data.remote.response.User
import com.dicoding.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        with(activityMainBinding) {
            svFinduser.setupWithSearchBar(sbFinduser)
            svFinduser.editText.setOnEditorActionListener { _, _, _ ->
                sbFinduser.text = svFinduser.text
                mainViewModel.getUser(svFinduser.text.toString())
                svFinduser.hide()
                showLoading(true)
                false
            }
        }

        mainViewModel.users.observe(this) { user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setUserData(users: List<User>) {
        val adapter = UserAdapter(this@MainActivity)
        adapter.submitList(users)
        activityMainBinding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}