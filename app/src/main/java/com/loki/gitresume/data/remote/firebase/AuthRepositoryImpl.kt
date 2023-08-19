package com.loki.gitresume.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.loki.gitresume.domain.models.User
import com.loki.gitresume.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let {

                    User(
                        id = it.uid,
                        email = it.email!!
                    )
                } ?: User())
            }

            auth.addAuthStateListener(listener)
            awaitClose {
                auth.removeAuthStateListener(listener)
            }
        }

    override suspend fun authenticate(email: String, password: String): User {
        val user = auth.signInWithEmailAndPassword(email, password).await()?.user
        return User(id = user!!.uid, email = user.email!!)
    }

    override suspend fun createAccount(email: String, password: String): String? {
        return auth.createUserWithEmailAndPassword(email, password).await().user?.uid
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}