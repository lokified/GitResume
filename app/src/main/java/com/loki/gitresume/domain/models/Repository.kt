package com.loki.gitresume.domain.models

data class Repository(
    val id: String,
    val name: String,
    val url: String,
    val description: String,
    val topics: List<String>
)
