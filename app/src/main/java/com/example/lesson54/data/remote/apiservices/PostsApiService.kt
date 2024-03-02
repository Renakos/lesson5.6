package com.example.lesson54.data.remote.apiservices

import com.example.lesson54.data.model.Post
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val ENDPOINT = "posts"

interface PostsApiService {
    @PATCH("$ENDPOINT/{postId}")
    fun editPost(
        @Path("postId") postId: Int,
        @Body post: Post
    ): Call<Post>

    @DELETE("$ENDPOINT/{postId}")
    fun deletePost(
        @Path("postId") postId: Int
    ): Call<Post>

    @POST(ENDPOINT)
    fun createNewPost(
        @Body post: Post
    ): Call<Post>

    @PUT("$ENDPOINT/{postId}")
    fun updatePost(
        @Path("postId") postId: Int,
        @Body post: Post
    ): Call<Post>

}
