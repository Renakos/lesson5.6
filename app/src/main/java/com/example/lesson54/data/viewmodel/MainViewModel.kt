package com.example.lesson54.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lesson54.data.model.Post
import com.example.lesson54.data.repository.Repository
import com.example.lesson54.data.repository.RepositoryRoom

class MainViewModel : ViewModel() {

    private val repositoryRoom = RepositoryRoom()
    private val repository = Repository()

    val allPosts: LiveData<MutableList<Post>> = repository.allPosts
    val allDatabasePosts: LiveData<MutableList<Post>> = repositoryRoom.allPosts

    fun createNewPost(post: Post, onFailure: (String, Throwable) -> Unit) {
        repository.createNewPost(
            post = post,
            onResponse = { newPost ->
                repositoryRoom.insertPost(
                    Post(
                        title = newPost.title,
                        body = newPost.body,
                        userId = newPost.userId
                    )
                )
            },
            onFailure = { s, t ->

            }
        )
    }

    fun editPost(
        post: Post,
        postParams: HashMap<String, Any>,
        onFailure: (String, Throwable) -> Unit
    ) {
        repository.editPost(postParams, { repositoryRoom.updatePost(post) }, onFailure)
    }

    fun deletePost(post: Post, postId: Int, onFailure: (String, Throwable) -> Unit) {
        repository.deletePost(postId, { repositoryRoom.deletePost(post) }, onFailure)
        allPosts.value.let {
            allPosts.value?.remove(post) ?: Log.e("Exception", "exception", NullPointerException())
        }
    }

    fun updatePost(postId: Int, post: Post, onFailure: (String, Throwable) -> Unit) {
        repository.updatePost(postId, post, { }, onFailure)
        allPosts.value.let {
            allPosts.value?.set(postId, post) ?: Log.e(
                "Exception",
                "exception",
                NullPointerException()
            )
        }
    }

}
