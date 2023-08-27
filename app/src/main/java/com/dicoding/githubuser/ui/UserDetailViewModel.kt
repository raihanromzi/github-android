package com.dicoding.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.remote.response.UserDetail
import com.dicoding.githubuser.data.remote.response.UserFollowerFollowing
import com.dicoding.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val _user = MutableLiveData<UserDetail?>()
    val user = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userFollower = MutableLiveData<List<UserFollowerFollowing>>()
    val userFollower: LiveData<List<UserFollowerFollowing>> = _userFollower

    private val _userFollowing = MutableLiveData<List<UserFollowerFollowing>?>()
    val userFollowing: MutableLiveData<List<UserFollowerFollowing>?> = _userFollowing

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                        Log.d(TAG, responseBody.toString())
                    }
                } else {
                    _isLoading.value = true
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "$t.message")
            }

        })
    }

    fun getFollowerUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<UserFollowerFollowing>> {
            override fun onResponse(
                call: Call<List<UserFollowerFollowing>>,
                response: Response<List<UserFollowerFollowing>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userFollower.value = responseBody
                        Log.d("FOLLOWER", responseBody.toString())
                    }
                } else {
                    _isLoading.value = true
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<List<UserFollowerFollowing>>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "$t.message")
            }

        })
    }

    fun getFollowingUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<UserFollowerFollowing>> {
            override fun onResponse(
                call: Call<List<UserFollowerFollowing>>,
                response: Response<List<UserFollowerFollowing>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userFollowing.value = responseBody
                        Log.d("FOLLOWING", responseBody.toString())
                    }
                } else {
                    _isLoading.value = true
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<List<UserFollowerFollowing>>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "$t.message")
            }

        })
    }

    companion object {
        private const val TAG = "UserDetail ViewModel"
    }
}