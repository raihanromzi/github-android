package com.dicoding.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserFollowerFollowingResponse(

    @field:SerializedName("UserFollowerFollowingResponse")
    val userFollowerFollowingResponse: List<UserFollowerFollowing>
)
