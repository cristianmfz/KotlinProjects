package tfg.cristian.project

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

actual class AuthService actual constructor() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    actual fun signInWithEmail(email: String, password: String, scope: CoroutineScope): Flow<AuthResult> = flow {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            emit(AuthResult.Success)
        } catch (e: Exception) {
            emit(AuthResult.Error(e.message ?: "Unknown error"))
        }
    }

    actual fun createAccountWithEmail(email: String, password: String, scope: CoroutineScope): Flow<AuthResult> = flow {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            emit(AuthResult.Success)
        } catch (e: Exception) {
            emit(AuthResult.Error(e.message ?: "Unknown error"))
        }
    }

    actual fun signOut(): Flow<AuthResult> = flow {
        try {
            auth.signOut()
            emit(AuthResult.Success)
        } catch (e: Exception) {
            emit(AuthResult.Error(e.message ?: "Unknown error"))
        }
    }

    actual fun isUserAuthenticated(): Boolean {
        return (auth.currentUser!=null)
    }
}