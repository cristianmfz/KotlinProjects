package tfg.cristian.project

actual interface UserRepository {
    actual fun isLoggedIn(): Boolean
    actual fun getUsername(): String?
    actual fun saveLoggedInState(username: String?)
    actual fun clearLoggedInState()
    actual fun getSelectedCountry(): String?
    actual fun saveSelectedCountry(country: String)
}

class IOSUserRepository() : UserRepository {
    override fun isLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String? {
        TODO("Not yet implemented")
    }

    override fun saveLoggedInState(username: String?) {
        TODO("Not yet implemented")
    }

    override fun clearLoggedInState() {
        TODO("Not yet implemented")
    }

    override fun getSelectedCountry(): String? {
        TODO("Not yet implemented")
    }

    override fun saveSelectedCountry(country: String) {
        TODO("Not yet implemented")
    }
}