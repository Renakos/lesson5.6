package com.example.lesson54.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")

data class Post(
    @SerializedName("id") @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("userId") val userId: Int
)