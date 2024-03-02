package com.example.lesson54.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lesson54.data.model.Post
import com.example.lesson54.data.repository.Repository

class MainViewModel : ViewModel() {


    private val repository = Repository()
    val allPosts: LiveData<MutableList<Post>> = repository.allPosts

    init {
        Log.e("room", repository.allPosts.value.toString(), )
    }
    fun createNewPost(post: Post, onFailure: (String, Throwable) -> Unit) {
        repository.createNewPost(
            post = post, onResponse = { newPost ->
                repository.insertPostRoom(
                    Post(
                        title = newPost.title, body = newPost.body, userId = newPost.userId
                    )
                )
            }, onFailure = onFailure
        )
    }

    fun editPost(
        editedPost: Post,
        onFailure: (String, Throwable) -> Unit
    ) {
        repository.editPost(
            postId = editedPost.id, post = editedPost,
            onResponse = { post: Post ->
                Log.e("EditedPost", post.toString())
                repository.updatePostRoom(editedPost)
            },
            onFailure
        )
    }


    //    fun deletePost(post: Post, postId: Int, onFailure: (String, Throwable) -> Unit) {
//        repository.deletePost(postId, { repository.deletePostRoom(post) }, onFailure)
//        allPosts.value.let {
//            allPosts.value?.remove(post) ?: Log.e("Exception", "exception", NullPointerException())
//        }
//    }
    fun deletePost(
        post: Post,
        onFailure: (message: String) -> Unit,
    ) {
        if (allPosts !== null) {
            allPosts.value.let {
                allPosts.value?.remove(post)

                Log.e("Exception", "exception")
            }
        }
        repository.deletePost(onResponse = {
            repository.deletePostRoom(post)
        }, onFailure = { message: String, t ->
            onFailure(message)
        }, post = post
        )
    }

    //    fun updatePost(postId: Int, post: Post, onFailure: (String, Throwable) -> Unit) {
//        repository.updatePost(postId, post, { }, onFailure)
//        allPosts.value.let {
//            allPosts.value?.set(postId, post) ?: Log.e(
//                "Exception",
//                "exception",
//                NullPointerException()
//            )
//        }
//    }
    fun updatePost(
        post: Post, onFailure: (message: String) -> Unit
    ) {
        repository.updatePost(post = post, onResponse = {
            repository.updatePostRoom(post)
        },

            onFailure = { message, t ->
                onFailure(message)
            })
        allPosts.value.let {
            allPosts.value?.set(post.id, post)
        }
    }

}
