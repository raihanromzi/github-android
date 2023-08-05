package com.dicoding.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dicoding.githubuser.data.remote.response.UserFollowerFollowing
import com.dicoding.githubuser.databinding.FragmentFollowersfollowingBinding

class FollowersFollowingFragment : Fragment() {

    private val userDetailViewModel by activityViewModels<UserDetailViewModel>()

    companion object {
        const val ARG_POSITION: String = "arg_position"
        const val ARG_USERNAME: String = "arg_username"
    }

    private lateinit var followersFollowingBinding: FragmentFollowersfollowingBinding
    private var position: Int? = null
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        followersFollowingBinding = FragmentFollowersfollowingBinding.inflate(layoutInflater)
        return followersFollowingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        userDetailViewModel.getFollowerUser(username.toString())
        userDetailViewModel.getFollowingUser(username.toString())

        if (position == 1) {
            userDetailViewModel.userFollower.observe(viewLifecycleOwner) { followers ->
                setFollowersData(followers)
            }
        } else {
            userDetailViewModel.userFollowing.observe(viewLifecycleOwner) { following ->
                setFollowingData(following)
            }
        }
    }

    private fun setFollowingData(following: List<UserFollowerFollowing>) {
        val adapter = UserFollowerFollowingAdapter()
        adapter.submitList(following)
        followersFollowingBinding.rvUsersFollowersFollowing.adapter = adapter
        followersFollowingBinding.rvUsersFollowersFollowing.visibility = View.VISIBLE
    }

    private fun setFollowersData(followers: List<UserFollowerFollowing>) {
        val adapter = UserFollowerFollowingAdapter()
        adapter.submitList(followers)
        followersFollowingBinding.rvUsersFollowersFollowing.adapter = adapter
        followersFollowingBinding.rvUsersFollowersFollowing.visibility = View.VISIBLE
    }


}