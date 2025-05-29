package tfg.cristian.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class AuthService actual constructor() {
    actual fun signInWithEmail(email: String, password: String, scope: CoroutineScope): Flow<AuthResult> = flow {
        TODO("Not yet implemented")
    }

    actual fun createAccountWithEmail(email: String, password: String, scope: CoroutineScope): Flow<AuthResult> = flow {
        TODO("Not yet implemented")
    }

    actual fun signOut(): Flow<AuthResult> = flow {
        TODO("Not yet implemented")
    }

    actual fun isUserAuthenticated(): Boolean {
        TODO("Not yet implemented")
    }
}