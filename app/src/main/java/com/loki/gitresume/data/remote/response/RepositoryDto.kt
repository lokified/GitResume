package com.loki.gitresume.data.remote.response

import com.google.gson.annotations.SerializedName

data class RepositoryDto(
    val id: String,
    val name: String,
    @SerializedName("html_url")
    val url: String,
    val description: String,
    val topics: List<String>
)