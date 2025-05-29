package tfg.cristian.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

sealed class AuthResult {
    data object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

expect class AuthService() {
    fun signInWithEmail(email: String, password: String, scope: CoroutineScope): Flow<AuthResult>
    fun createAccountWithEmail(email: String, password: String, scope: CoroutineScope): Flow<AuthResult>
    fun signOut(): Flow<AuthResult>
    fun isUserAuthenticated(): Boolean
}