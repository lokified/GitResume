package com.loki.gitresume.domain.repository

import com.loki.gitresume.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String): User
    suspend fun createAccount(email: String, password: String): String?
    suspend fun sendRecoveryEmail(email: String)
    suspend fun signOut()
}