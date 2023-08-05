package com.dicoding.githubuser.data.remote.retrofit

import com.dicoding.githubuser.data.remote.response.GithubResponse
import com.dicoding.githubuser.data.remote.response.UserDetail
import com.dicoding.githubuser.data.remote.response.UserFollowerFollowing
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") query: String
    ): Call<UserDetail>


    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<UserFollowerFollowing>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<UserFollowerFollowing>>

}