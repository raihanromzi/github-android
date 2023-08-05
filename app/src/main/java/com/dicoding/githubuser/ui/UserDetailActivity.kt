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

    companion object {
        const val USER_NAME = ""
        private val TAB_TITLES = intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }

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
            setUserDetailData(user)
        }
    }

    private fun setUserDetailData(user: UserDetail) {
        userDetailBinding.tvRealnameDetail.visibility = View.VISIBLE
        userDetailBinding.tvRealnameDetail.text = user.name

        userDetailBinding.tvUserNameDetail.visibility = View.VISIBLE
        userDetailBinding.tvUserNameDetail.text = getString(R.string.username, user.login)

        userDetailBinding.tvUserFollowersDetail.visibility = View.VISIBLE
        userDetailBinding.tvUserFollowersDetail.text = getString(R.string.followers, user.followers)

        userDetailBinding.tvUserFollowingDetail.visibility = View.VISIBLE
        userDetailBinding.tvUserFollowingDetail.text = getString(R.string.following, user.following)

        userDetailBinding.ivUserPictureDetail.visibility = View.VISIBLE
        Glide.with(userDetailBinding.ivUserPictureDetail.context).load(user.avatarUrl)
            .into(userDetailBinding.ivUserPictureDetail)

        userDetailBinding.viewPager.visibility = View.VISIBLE
        userDetailBinding.tabs.visibility = View.VISIBLE
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
}