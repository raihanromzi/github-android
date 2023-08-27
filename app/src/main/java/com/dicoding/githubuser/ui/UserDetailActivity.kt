package com.dicoding.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.remote.response.UserDetail
import com.dicoding.githubuser.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var userDetailBinding: ActivityUserDetailBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>()
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDetailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(userDetailBinding.root)

        name = intent.getStringExtra(USER_NAME)
        userDetailViewModel.getDetailUser(name.toString())

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userDetailViewModel.user.observe(this) { user ->
            if (user != null) {
                setUserDetailData(user)
            }
        }
    }

    private fun setUserDetailData(user: UserDetail) {
        userDetailBinding.apply {
            tvRealnameDetail.visibility = View.VISIBLE
            tvRealnameDetail.text = user.name

            tvUserNameDetail.visibility = View.VISIBLE
            tvUserNameDetail.text = getString(R.string.username, user.login)

            tvUserFollowersDetail.visibility = View.VISIBLE
            tvUserFollowersDetail.text = getString(R.string.followers, user.followers)

            tvUserFollowingDetail.visibility = View.VISIBLE
            tvUserFollowingDetail.text = getString(R.string.following, user.following)

            ivUserPictureDetail.visibility = View.VISIBLE
            Glide.with(ivUserPictureDetail.context).load(user.avatarUrl)
                .into(ivUserPictureDetail)

            viewPager.visibility = View.VISIBLE
            tabs.visibility = View.VISIBLE
        }
        val followersFollowingPagerAdapter = FollowersFollowingPagerAdapter(this)
        followersFollowingPagerAdapter.username = name.toString()
        val viewPager: ViewPager2 = userDetailBinding.viewPager
        viewPager.adapter = followersFollowingPagerAdapter
        val tabs: TabLayout = userDetailBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        userDetailBinding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USER_NAME = ""
        private val TAB_TITLES = intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }
}