package tfg.cristian.project

expect interface UserRepository {
    fun isLoggedIn(): Boolean
    fun getUsername(): String?
    fun saveLoggedInState(username: String?)
    fun clearLoggedInState()
    fun getSelectedCountry(): String?
    fun saveSelectedCountry(country: String)
}