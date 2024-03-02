package com.example.lesson54.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lesson54.data.model.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): LiveData<MutableList<Post>>

    @Delete(Post::class)
    fun deletePost(post: Post)

    @Update(Post::class, onConflict = OnConflictStrategy.REPLACE)
    fun updatePost(post: Post)
}
