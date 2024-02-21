package com.example.lesson54.data.repository

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
        postParams: HashMap<String, Any>,
        onResponse: (Post) -> Unit,
        onFailure: (String, Throwable) -> Unit
    ) {
        apiService.editPost(postParams).enqueue(object : Callback<Post> {
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

    fun deletePost(postId: Int, onResponse: () -> Unit, onFailure: (String, Throwable) -> Unit) {
        apiService.deletePost(postId).enqueue(object : Callback<Post> {
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
        postId: Int,
        post: Post,
        onResponse: () -> Unit,
        onFailure: (String, Throwable) -> Unit
    ) {
        apiService.updatePost(postId, post).enqueue(object : Callback<Post> {
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
}
