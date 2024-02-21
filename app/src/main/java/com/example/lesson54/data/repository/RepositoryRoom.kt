package com.example.lesson54.data.repository

import androidx.lifecycle.LiveData
import com.example.lesson54.App
import com.example.lesson54.data.model.Post

class RepositoryRoom {
    private val appDataBase = App.db
    private val postDao = appDataBase.postDao()
    val allPosts: LiveData<MutableList<Post>> = postDao.getAllPosts()

    fun insertPost(post: Post) {
        App.db.postDao().insertPost(post)
    }

    fun deletePost(post: Post) {
        return postDao.deletePost(post)
    }

    fun updatePost(post: Post) {
        return postDao.updatePost(post)
    }
}
