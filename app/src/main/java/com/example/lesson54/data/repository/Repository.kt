package com.example.lesson54.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.lesson54.App
import com.example.lesson54.data.local.PostDao
import com.example.lesson54.data.model.Post
import com.example.lesson54.data.remote.RetrofitClient
import com.example.lesson54.data.remote.apiservices.PostsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val postDao: PostDao = App.db.postDao()
    private val apiService: PostsApiService = RetrofitClient.postsApiService
    val allPosts: LiveData<MutableList<Post>> = postDao.getAllPosts()

    init {
        Log.e("room", postDao.getAllPosts().value.toString())
    }

    fun createNewPost(
        post: Post,
        onResponse: (Post) -> Unit,
        onFailure: (String, Throwable) -> Unit
    ) {
        apiService.createNewPost(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful && response.body() != null) {
                    onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                onFailure(t.message ?: "Unknown error!", t)
            }
        })
    }

    fun editPost(
        postId: Int,
        post: Post,
        onResponse: (Post) -> Unit,
        onFailure: (String, Throwable) -> Unit
    ) {
        apiService.editPost(postId, post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful && response.body() != null) {
                    onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                onFailure(t.message ?: "Unknown error!", t)
            }
        })
    }

    fun deletePost(post: Post, onResponse: () -> Unit, onFailure: (String, Throwable) -> Unit) {
        apiService.deletePost(post.id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    onResponse()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                onFailure(t.message ?: "Unknown error!", t)
            }
        })
    }

//    private fun insertPostToRoom(post: Post) {
//        postDao.insertPost(post)
//    }

    fun updatePost(
        post: Post,
        onResponse: (post: Post) -> Unit,
        onFailure: (message: String, Throwable) -> Unit
    ) {
        apiService.updatePost(post.id, post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful && response.body() != null) {
                    onResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                onFailure(t.message ?: "Unknown error!", t)
            }
        })
    }

    fun insertPostRoom(post: Post) {
        return postDao.insertPost(post)
    }

    fun deletePostRoom(post: Post) {
        return postDao.deletePost(post)
    }

    fun updatePostRoom(post: Post) {
        postDao.updatePost(post)
    }
}
