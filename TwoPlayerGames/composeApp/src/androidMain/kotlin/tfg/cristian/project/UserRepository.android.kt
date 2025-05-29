package tfg.cristian.project

import android.content.Context

actual interface UserRepository {
    actual fun isLoggedIn(): Boolean
    actual fun getUsername(): String?
    actual fun saveLoggedInState(username: String?)
    actual fun clearLoggedInState()
    actual fun getSelectedCountry(): String?
    actual fun saveSelectedCountry(country: String)
}

class AndroidUserRepository(private val context: Context) : UserRepository {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    override fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    override fun saveLoggedInState(username: String?) {
        sharedPreferences.edit().apply {
            putBoolean("is_logged_in", true)
            putString("username", username)
            apply()
        }
    }

    override fun clearLoggedInState() {
        sharedPreferences.edit().clear().apply()
    }

    override fun getSelectedCountry(): String? {
        return sharedPreferences.getString("selected_country", null)
    }

    override fun saveSelectedCountry(country: String) {
        sharedPreferences.edit().apply {
            putString("selected_country", country)
            apply()
        }
    }
}